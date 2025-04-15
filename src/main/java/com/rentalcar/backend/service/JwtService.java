package com.rentalcar.backend.service;

import com.rentalcar.backend.model.InvalidToken;
import com.rentalcar.backend.model.User;
import com.rentalcar.backend.repository.InvalidTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtService {
    private static final long EXPIRATION_TIME = 3600000;
    public final InvalidTokenRepository invalidTokenRepository;
    private final Key key;

    public JwtService(@Value("${jwt.secret}") String secret, InvalidTokenRepository invalidTokenRepository) {
        this.invalidTokenRepository = invalidTokenRepository;
        String secret1 = secret != null && !secret.isEmpty() ? secret : generateSecretKey();
        this.key = Keys.hmacShaKeyFor(secret1.getBytes(StandardCharsets.UTF_8));
    }
    public String generateToken(UserDetails userDetails) {
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        User user = (User) userDetails;

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", role)
                .claim("id", user.getId())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }


    public String resolveToken(HttpServletRequest request) {

        String TOKEN_HEADER = "Authorization";
        String bearerToken = request.getHeader(TOKEN_HEADER);
        String TOKEN_PREFIX = "Bearer ";
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public static String generateSecretKey() {
        int length = 32;

        SecureRandom secureRandom = new SecureRandom();

        byte[] keyBytes = new byte[length];

        secureRandom.nextBytes(keyBytes);

        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public void invalidateToken(String token) {
        Date expiration = extractExpiration(token);
        InvalidToken invalidToken = new InvalidToken(token, expiration);
        invalidTokenRepository.save(invalidToken);
    }

}
