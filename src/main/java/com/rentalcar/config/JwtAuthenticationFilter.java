package com.rentalcar.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalcar.backend.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    private final String secret= "una_chiave_sicura_di_almeno_32_caratteri_1234567890";
    @Autowired
    private JwtUtils jwtUtils;

    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try{
            String token = jwtUtils.resolveToken(request);

            if(StringUtils.hasText(token) && jwtUtils.validateToken(token)){
                String username = jwtUtils.extractUsername(token);

                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e){
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("message", "Authentication Error");
            errorDetails.put("details",e.getMessage());

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            mapper.writeValue(response.getWriter(), errorDetails);

        }

    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
