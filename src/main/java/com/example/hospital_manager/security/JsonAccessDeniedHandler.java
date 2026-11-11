
package com.example.hospital_manager.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JsonAccessDeniedHandler
        implements AccessDeniedHandler {

    private final ErrorResponseWriter writer;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exception)
            throws IOException, ServletException {

        writer.write(
                response,
                403,
                "You do not have permission to access this resource",
                request.getRequestURI()
        );
    }
}
