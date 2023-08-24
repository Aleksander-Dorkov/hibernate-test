package com.sasho.hibernate.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter implements Filter {
    private final JWTService jwtService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Log request
        logMessage("Request", request.getMethod(), request.getRequestURI(), 0);

        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7); // Skip "Bearer "
            UsernamePasswordAuthenticationToken authentication = this.jwtService.validateAndCreateAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);

        // Log response
        logMessage("Response", request.getMethod(), request.getRequestURI(), response.getStatus());
    }

    private void logMessage(String type, String method, String url, int statusCode) {
        System.out.println("*".repeat(70) +
                "\nType: %s".formatted(type) +
                "\nMethod: %s".formatted(method) +
                "\nUrl: %s".formatted(url) +
                (statusCode != 0 ? "\nStatus Code: %d".formatted(statusCode) : ""));
    }

    // Other methods as needed
}
