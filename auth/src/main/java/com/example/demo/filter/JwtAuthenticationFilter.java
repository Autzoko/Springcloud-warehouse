package com.example.demo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String secretKey = "yourSecretKey";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }

        if (token != null && isValidToken(token)) {
            String userId = getUserIdFromToken(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userId, null, Collections.emptyList());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isValidToken(String token) {
        try {
            String uid = getUserIdFromToken(token);
            String cachedToken = (String) redisTemplate.opsForValue().get("accessToken:" + uid);
            if (token.equals(cachedToken)) {
                Algorithm algorithm = Algorithm.HMAC256(secretKey);
                JWTVerifier verifier = JWT.require(algorithm).build();
                verifier.verify(token);
                return true;
            } else {
                return false;
            }
        } catch (JWTVerificationException e) {
            return false;
        }
    }


    private String getUserIdFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        return jwt.getSubject();
    }
}
