package com.bravo.league.services;

import com.bravo.league.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByEmail(username);
    }


    public String getToken(UsernamePasswordAuthenticationToken login) {
        User user = userService.findByEmail(login.getPrincipal().toString());

        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("Totem TI")
                .setSubject(user.getId().toString())
                .setIssuedAt(today)
                .claim("name", user.getName())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    public boolean isTokenValid(String token) {
        if(token != null) {
            try {
                Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }




}
