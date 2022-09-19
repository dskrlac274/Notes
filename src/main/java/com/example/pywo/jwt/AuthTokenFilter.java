package com.example.pywo.jwt;

import com.example.pywo.exception.JwtExpired;
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
import java.util.Date;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private MyUserDetailsService userDetailsService;

    private String jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
             jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt) ) {

                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,PUT, DELETE");
                response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
                response.setHeader("Access-Control-Max-Age", "");
                response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With,");

                String s1 = jwtUtils.getUserNameFromJwtToken(jwt);
                String username = s1.substring(s1.indexOf(",") + 1);
                username.trim();
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }
    public String getJwt(){
        return jwt;
    }
    public void setJwt(String jwt){
        this.jwt = jwt;
    }
    public void isExpired(){
        if(jwtUtils.getExpirationTimeFromJwtToken(jwt).compareTo(new Date()) < 0) {
            jwt = "";
        }
    }
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}