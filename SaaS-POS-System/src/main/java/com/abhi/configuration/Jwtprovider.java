package com.abhi.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class Jwtprovider {

    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

    public String generateToken(Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities =
                authentication.getAuthorities();

        String roles = populateAuthorities(authorities);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 864000000))
                .claim("email", authentication.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
    }

    public String getEmailFromToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return String.valueOf(claims.get("email"));



    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {

        Set<String> auths = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);

    }
}
