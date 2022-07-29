package com.nnk.springboot.config;

import com.nnk.springboot.constant.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
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
            response.sendRedirect("/app/admin/home");

        } else {
            response.sendRedirect("/app/user");

        }
    }
}
