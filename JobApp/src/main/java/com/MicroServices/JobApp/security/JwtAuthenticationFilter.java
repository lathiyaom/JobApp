package com.MicroServices.JobApp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Custom JWT authentication filter that is executed once per request.
 * <p>
 * It checks for the presence of a valid JWT in the Authorization header,
 * validates it, and sets the authentication in the security context.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * This method intercepts each HTTP request to perform JWT validation and authentication setup.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        // Check if the header exists and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // Extract the token from the header by removing "Bearer " prefix
            String token = authHeader.substring(7);

            // Debug logs for token processing (consider replacing with proper logging in production)
            System.out.println("Authorization header: " + authHeader);
            System.out.println("Extracted token: " + token);

            // Extract username from the token
            String username = jwtTokenProvider.getUsernameFromToken(token);

            // Check if username is present and authentication is not already set in the security context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load user details from the database using username
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validate the token using the token provider
                if (jwtTokenProvider.validateToken(token)) {

                    // Create an authentication token with user details and authorities
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // Attach request-specific details (IP, session ID, etc.)
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication object into the security context
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        // Continue the filter chain regardless of authentication outcome
        filterChain.doFilter(request, response);
    }
}
