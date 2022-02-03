package edu.school21.restful.security;

import edu.school21.restful.models.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.expiration}")
    private Long JWT_TOKEN_EXPIRATION;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + JWT_TOKEN_EXPIRATION);
        Map<String, Object> claimsMap = new HashMap<>();
        Long id = user.getId();
        claimsMap.put("id", id);
        claimsMap.put("role", user.getRoles().toArray()[0]);
        claimsMap.put("username", user.getUsername());
        return Jwts.builder()
                .setSubject(id.toString())
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException |
                MalformedJwtException |
                ExpiredJwtException |
                UnsupportedJwtException |
                IllegalArgumentException ex) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("username");
    }
}
