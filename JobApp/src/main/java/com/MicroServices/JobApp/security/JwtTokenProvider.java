package com.MicroServices.JobApp.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for generating and validating JWT tokens.
 */
@Component
public class JwtTokenProvider {

    // Secret key for signing the JWT token, injected from application properties
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    // Token expiration duration in milliseconds, injected from application properties
    @Value("${app.jwt.expiration}")
    private Long jwtExpirationInMs;

    /**
     * Generates a JWT token for an authenticated user.
     *
     * @param authentication Spring Security authentication object
     * @return a signed JWT token string
     */
    public String generateToken(Authentication authentication) {
        // Extract user details from the authentication object
        org.springframework.security.core.userdetails.User userPrincipal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        // Current timestamp
        Date now = new Date();

        // Token expiration time
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        // Build and sign the JWT token with username and expiration details
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())      // Set the username as subject
                .setIssuedAt(now)                            // Set the current timestamp
                .setExpiration(expiryDate)                   // Set the expiration time
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // Sign with the HS512 algorithm using the secret key
                .compact();                                  // Build and return the token string
    }

    /**
     * Validates a JWT token's signature and structure.
     *
     * @param authToken the JWT token string to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String authToken) {
        try {
            // Parse the token and validate signature using the secret key
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken); // Will throw exception if invalid
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT Token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT Token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT Token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token the JWT token string
     * @return the username embedded in the token
     */
    public String getUsernameFromToken(String token) {
        // Parse the token and extract claims
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token) // Will throw exception if token is invalid
                .getBody();

        // Return the subject (username)
        return claims.getSubject();
    }
}
