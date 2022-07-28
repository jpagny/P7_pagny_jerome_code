package com.nnk.springboot.handler;

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

        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);

        boolean admin = false;

        LOG.debug("AT onAuthenticationSuccess(...) function!");

        for (GrantedAuthority auth : authentication.getAuthorities()) {

            LOG.debug("ICI : " + auth.getAuthority());
            if ("ROLE_ADMIN".equals(auth.getAuthority())) {
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
