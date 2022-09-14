package com.example.pywo.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

@Component("myLogoutSuccessHandler")
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        final HttpSession session = request.getSession();
        if (session != null) {
            Enumeration<String> attributes = request.getSession().getAttributeNames();
            while (attributes.hasMoreElements()) {
                String attribute = (String) attributes.nextElement();
                System.out.println(attribute+" : "+request.getSession().getAttribute(attribute));
                session.removeAttribute(attribute);
            }
        }
    }
}