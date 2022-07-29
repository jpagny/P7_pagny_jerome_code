package com.nnk.springboot.config;

import com.nnk.springboot.constant.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);

        boolean admin = false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {

            if ((Role.ADMIN.getAuthority()).equals(auth.getAuthority())) {
                admin = true;
            }
        }

        if (admin) {
            response.sendRedirect("/app/admin");

        } else {
            response.sendRedirect("/app/user");

        }
    }
}
