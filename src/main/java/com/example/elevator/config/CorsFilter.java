package com.example.elevator.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CorsFilter implements Filter {

    @Value("${elevator.cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${elevator.cors.allowed-methods}")
    private String allowedMethods;

    @Value("${elevator.cors.allowed-headers}")
    private String allowedHeaders;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", allowedOrigins);
        httpResponse.setHeader("Access-Control-Allow-Methods", allowedMethods);
        httpResponse.setHeader("Access-Control-Allow-Headers", allowedHeaders);
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        chain.doFilter(request, response);
    }
}
