package com.example.pywo.jwt;

import com.example.pywo.security.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("MAMAMAMAMAMAMAMAM");
        try {
            String jwt = parseJwt(request);
            System.out.println("JWT JE: " + " " + jwt);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String s1 = jwtUtils.getUserNameFromJwtToken(jwt);
                String username = s1.substring(s1.indexOf(",") + 1);
                username.trim();
                System.out.println("Username je:" + " " + username);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("User details je:" + " " + userDetails);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, new ArrayList<>());
                System.out.println("Token je:" + " " + authentication);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println("Details:" + " " + authentication.getDetails());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}