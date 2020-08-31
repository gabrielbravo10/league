package com.bravo.league.configurations;

import com.bravo.league.models.User;
import com.bravo.league.services.AuthenticationService;
import com.bravo.league.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationService authenticationService;
    private UserService userService;

    public AuthenticationFilter(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);
        boolean isTokenValid = authenticationService.isTokenValid(token);

        if (isTokenValid) {
            authenticateUser(token);
        }

        filterChain.doFilter(request, response);
    }



    private String recoverToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (isAuthorizationValid(token)) {
            return token.substring(7);
        }
        return null;
    }

    private boolean isAuthorizationValid(String token) {
        return token != null
                && token.startsWith("Bearer ")
                && token.length() > 7;
    }

    private void authenticateUser(String token) {
        Long userId = authenticationService.getUserId(token);
        User user = userService.findById(userId);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getProfiles());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
