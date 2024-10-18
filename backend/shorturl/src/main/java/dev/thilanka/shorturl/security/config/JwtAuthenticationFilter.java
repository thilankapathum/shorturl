package dev.thilanka.shorturl.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor    //-- Constructor using final fields.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //-- JWT Service has methods related to processing JWTs.
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    //-- JWTAuthFilter will filter(check) each HTTP request from client, whether it contains a valid JWT (token)
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");   //-- Get 'Authorization' header from Http request. It contains Auth. token (Eg: JWT)
        final String jwt;       //-- Will assign JWT Token
        final String username;  //-- Will assign User's username

        //-- Check whether Authorization Header contains a 'Bearer token'
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            //-- Perform filtering because there's no valid Bearer token.
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);  //-- Extract JWT (token) from Authorization header. Eg: "Bearer eyJhbGciOiJ..."
        username = jwtService.extractUsername(jwt);

        //-- Checking whether JWT's username is not-null AND whether this user is already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            //-- If JWT is valid, update SecurityContextHolder
            if (jwtService.isJwtValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                //-- Provide additional details for the authenticationToken
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //-- Update SecurityContextHolder with authenticationToken
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        //-- Perform filtering because JWT is invalid AND user is not already authenticated
        filterChain.doFilter(request, response);


    }
}
