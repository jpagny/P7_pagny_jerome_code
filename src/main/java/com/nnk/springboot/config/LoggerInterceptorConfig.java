package com.nnk.springboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class LoggerInterceptorConfig implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggerInterceptorConfig.class);

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        log.debug("[preHandle][" + request.getProtocol() + "]" + "[" + request.getMethod()
                + "]" + request.getRequestURI() + getParameters(request));


        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) {

        log.debug("[postHandle][" + request + "]");

    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler, Exception ex) {

        if (ex != null) {
            ex.printStackTrace();
        }
        log.debug("[afterCompletion][" + request + "][exception: " + ex + "]");
    }

    private String getParameters(HttpServletRequest request) {
        StringBuilder posted = new StringBuilder();
        Enumeration<?> e = request.getParameterNames();
        if (e != null) {
            posted.append("?");
        }
        while (true) {
            assert e != null;
            if (!e.hasMoreElements()) break;
            if (posted.length() > 1) {
                posted.append("&");
            }
            String curr = (String) e.nextElement();
            posted.append(curr).append("=");
            if (curr.contains("password")
                    || curr.contains("pass")
                    || curr.contains("pwd")) {
                posted.append("*****");
            } else {
                posted.append(request.getParameter(curr));
            }
        }
        String ip = request.getHeader("X-FORWARDED-FOR");
        String ipAddress = (ip == null) ? getRemoteAddress(request) : ip;
        if (ipAddress != null && !ipAddress.equals("")) {
            posted.append("&_psip=").append(ipAddress);
        }
        return posted.toString();
    }

    private String getRemoteAddress(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            log.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }


}
