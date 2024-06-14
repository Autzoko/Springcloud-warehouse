package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demo.po.*;
import com.example.demo.service.NormalUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class LoginController {
    @Autowired
    private NormalUserService userService;

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

            // Generate a new access token
            String newAccessToken = generateToken(nUid, accessTokenExpiration);

            // Return the new access token and position in the response
            AuthResponse authResponse = new AuthResponse(newAccessToken, refreshToken);
            return ResponseEntity.ok(Result.success(authResponse));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.error(HttpStatus.UNAUTHORIZED.value(), "Invalid refresh token"));
        }
    }

    @GetMapping("/validateToken")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        try {
            String uid = extractUserIdFromToken(token);
            if (uid != null) {
                return ResponseEntity.ok(uid);
            }
        } catch (JWTDecodeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
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

    private String extractUserIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
