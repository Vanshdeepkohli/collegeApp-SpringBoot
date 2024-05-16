package com.vansheepkohli.collegeApp.filter;

import com.vansheepkohli.collegeApp.services.CustomUserService;
import com.vansheepkohli.collegeApp.services.JWTService;
import com.vansheepkohli.collegeApp.services.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    //    private final StudentDetailsServiceImpl userDetailsService;
    private final CustomUserService customUserDetailsService;

    private final RefreshTokenService refreshTokenService;

    public JwtAuthenticationFilter(JWTService jwtService, CustomUserService customUserDetailsService, RefreshTokenService refreshTokenService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String refreshToken = request.getHeader("X-Refresh-Token");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println(refreshToken);

        String token = authHeader.substring(7);
        System.out.println(token);
        System.out.println("after string token on line 58");
        try {
            String username = jwtService.extractUserName(token);
            System.out.println("after username line 60");


            System.out.println("vansh");

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                System.out.println("inside username!=null");

                if (jwtService.isValid(token, userDetails)) {
                    System.out.println("inside isValid()");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }
            }
        } catch (Exception e) {

            System.out.println("inside exception block");
            System.out.println(e);
            if (refreshToken != null) {
                List<String> newJwtToken = refreshTokenService.generateJwtTokenFromRefreshToken(refreshToken);
                if (newJwtToken != null) {
                    // Send the new JWT token to the user
                    if (newJwtToken.size() == 1) {
                        response.addHeader("Authorization", "Bearer " + newJwtToken.get(0));
                    } else {
                        response.addHeader("Authorization", "Bearer " + newJwtToken.get(0));
                        response.addHeader("X-Refresh-Token", newJwtToken.get(1));
                    }

                } else {
                    System.out.println(newJwtToken);
                }
            }
        }


        filterChain.doFilter(request, response);
    }
}
