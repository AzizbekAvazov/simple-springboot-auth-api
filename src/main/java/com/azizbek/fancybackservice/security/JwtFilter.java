package com.azizbek.fancybackservice.security;

import com.azizbek.fancybackservice.util.Utilities;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Creator: Azizbek Avazov
 * Date: 01.07.2022
 * Time: 10:48
 */

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService userDetailsService;
    private final TokenManager tokenManager;

    public JwtFilter(JwtUserDetailsService userDetailsService, TokenManager tokenManager) {
        this.userDetailsService = userDetailsService;
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String username = null;
        String token = request.getHeader("Authorization");

        try {
            if (token != null && !token.equals("")) {
                // Remove any leading/trailing whitespace
                token = token.trim();
                username = tokenManager.getUsernameFromToken(token);
            }
        } catch (MalformedJwtException e) {
            // Log the exception for debugging
            logger.error("Malformed JWT Token: " + e.getMessage());

            // Create a custom error response
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("timestamp", Utilities.getCurrentDateAndTime());
            errorResponse.put("status", HttpServletResponse.SC_BAD_REQUEST);
            errorResponse.put("error", "Bad Request");
            errorResponse.put("message", "Malformed JWT Token");

            // Set the HTTP status code and response content type
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");

            // Write the error response to the response body
            response.getWriter().write(errorResponse.toString());
            return;  // Return the error response and exit the filter
        } catch (IllegalArgumentException e) {
            // Log the exception for debugging
            logger.error("Unable to get JWT Token: " + e.getMessage(), e);
        } catch (ExpiredJwtException e) {
            // Log the exception for debugging
            // Log the exception for debugging
            logger.error("JWT Token has expired: " + e.getMessage());

            // Create a custom error response
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("timestamp", Utilities.getCurrentDateAndTime());
            errorResponse.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            errorResponse.put("error", "Unauthorized");
            errorResponse.put("message", "JWT Token has expired");

            // Set the HTTP status code and response content type
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            // Write the error response to the response body
            response.getWriter().write(errorResponse.toString());
            return;  // Return the error response and exit the
        }

        // Check if the token is missing and the request is addressing protected URL
        if (token == null && !request.getRequestURI().startsWith("/PUBLIC/") && !request.getRequestURI().startsWith("/AUTH/")) {
            // No token provided for non-"auth/" requests, return a response indicating unauthorized access
            JSONObject errorResponse = Utilities.generateBaseResponse(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Authorization token is missing",
                    request.getRequestURI());

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(errorResponse.toString());
            return;  // Return the error response and exit the filter
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (tokenManager.validateJwtToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
