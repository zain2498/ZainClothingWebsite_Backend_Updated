package com.zain_discoveries.com.zain_discoveries.clothing_website.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTProvider {
    private SecretKey secretKey;

    public JWTProvider() {
        try {
            secretKey = Keys.hmacShaKeyFor(JWTConstants.JWT_SECRET_KEY.getBytes());
            System.out.println("key is successfully created... " + secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateToken(Authentication authentication) {
        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 846000000))
                .claim("email", authentication.getName())
                .signWith(secretKey)
                .compact();

        return jwt;
    }

    public String getEmailFromToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claims.get("email"));
        return email;
    }
}
