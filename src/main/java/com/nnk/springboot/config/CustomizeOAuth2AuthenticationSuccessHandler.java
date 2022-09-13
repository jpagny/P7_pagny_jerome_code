package com.nnk.springboot.config;

import com.nnk.springboot.constant.Role;
import com.nnk.springboot.dto.CustomOAuth2User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.exception.ResourceAlreadyExistException;
import com.nnk.springboot.repository.impl.UserRepository;
import com.nnk.springboot.service.implement.UserService;
import lombok.SneakyThrows;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.passay.IllegalCharacterRule.ERROR_CODE;

@Component
public class CustomizeOAuth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    private final UserRepository userRepository;

    public CustomizeOAuth2AuthenticationSuccessHandler(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        response.setStatus(HttpServletResponse.SC_OK);

        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        processOAuthPostLogin(oauthUser);
        response.sendRedirect("/home");
    }

    public void processOAuthPostLogin(CustomOAuth2User oAuth2User) throws ResourceAlreadyExistException {
        Optional<UserEntity> existUser = userRepository.findUserByUsername(oAuth2User.getAttribute("login"));

        if (!existUser.isPresent()) {

            UserDTO newUser = new UserDTO();
            newUser.setUsername(oAuth2User.getAttribute("login"));
            newUser.setPassword(generatePassword());
            newUser.setFullname(oAuth2User.getAttribute("name"));
            newUser.setRole(Role.USER.name());

            userService.create(newUser);

        } else {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
            updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + existUser.get().getRole()));

            Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);

            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }

    }

    private String generatePassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        return gen.generatePassword(20, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
    }

}
