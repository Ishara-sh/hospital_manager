
package com.example.hospital_manager.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JsonAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    private final ErrorResponseWriter writer;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {

        writer.write(
                response,
                401,
                "Authentication required or invalid token",
                request.getRequestURI()
        );
    }
}
