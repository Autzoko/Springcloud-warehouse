package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demo.po.AuthResponse;
import com.example.demo.po.NormalUser;
import com.example.demo.po.Result;
import com.example.demo.service.NormalUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class LoginController {
    @Autowired
    private NormalUserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String secretKey = "yourSecretKey";
    private final long accessTokenExpiration = 3600000; // 1 hour

    @PostMapping("/user/login")
    @ResponseBody
    public ResponseEntity<Result> userLogin(@RequestBody NormalUser normalUser) {
        String email = normalUser.getEmail();
        String password = normalUser.getPassword();

        if (userService.authenticateUser(email, password)) {
            String nUid = userService.getUserByEmail(email).getUid();
            String accessToken = generateToken(nUid, accessTokenExpiration);
            // 7 days
            long refreshTokenExpiration = 604800000;
            String refreshToken = generateToken(nUid, refreshTokenExpiration);

            // 缓存 Token
            cacheToken(nUid, accessToken, refreshToken, accessTokenExpiration, refreshTokenExpiration);

            AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);
            return ResponseEntity.ok(Result.success(authResponse));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.error(HttpStatus.UNAUTHORIZED.value(), "Authentication failed"));
        }
    }

    @PostMapping("/user/refreshToken")
    @ResponseBody
    public ResponseEntity<Result> userRefreshToken(@RequestBody AuthResponse refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        // Validate the refresh token
        if (isValidToken(refreshToken)) {
            String nUid = getUidFromToken(refreshToken);
            String cachedRefreshToken = (String) redisTemplate.opsForValue().get("refreshToken:" + nUid);

            if (cachedRefreshToken != null && cachedRefreshToken.equals(refreshToken)) {
                // Generate a new access token
                String newAccessToken = generateToken(nUid, accessTokenExpiration);

                // 缓存新的 access token
                redisTemplate.opsForValue().set("accessToken:" + nUid, newAccessToken, accessTokenExpiration, TimeUnit.MILLISECONDS);

                // Return the new access token and position in the response
                AuthResponse authResponse = new AuthResponse(newAccessToken, refreshToken);
                return ResponseEntity.ok(Result.success(authResponse));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.error(HttpStatus.UNAUTHORIZED.value(), "Invalid refresh token"));
    }

    public String getUidFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String generateToken(String uid, long expiration) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(uid)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    private boolean isValidToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private void cacheToken(String uid, String accessToken, String refreshToken, long accessTokenExpiration, long refreshTokenExpiration) {
        redisTemplate.opsForValue().set("accessToken:" + uid, accessToken, accessTokenExpiration, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set("refreshToken:" + uid, refreshToken, refreshTokenExpiration, TimeUnit.MILLISECONDS);
    }
}
