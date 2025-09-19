package co.edu.eci.blueprints.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final ObjectMapper objectMapper;

    public JwtValidationFilter(JwtDecoder jwtDecoder, ObjectMapper objectMapper) {
        this.jwtDecoder = jwtDecoder;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        
        // Permitir rutas públicas sin validación JWT
        String requestPath = request.getRequestURI();
        if (isPublicEndpoint(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            try {
                // Decodificar y validar el token JWT
                Jwt jwt = jwtDecoder.decode(token);
                
                // Verificar si el token ha expirado
                Instant now = Instant.now();
                Instant expiresAt = jwt.getExpiresAt();
                
                if (expiresAt == null || now.isAfter(expiresAt)) {
                    handleExpiredToken(response);
                    return;
                }
                
                // Token válido, continuar con el filtro chain
                filterChain.doFilter(request, response);
                
            } catch (JwtException e) {
                // Token inválido (malformado, firma incorrecta, etc.)
                handleInvalidToken(response, e.getMessage());
                return;
            }
        } else {
            // No hay token de autorización
            handleMissingToken(response);
            return;
        }
    }

    private boolean isPublicEndpoint(String requestPath) {
        return requestPath.startsWith("/auth/login") ||
               requestPath.startsWith("/actuator/health") ||
               requestPath.startsWith("/v3/api-docs") ||
               requestPath.startsWith("/swagger-ui") ||
               requestPath.equals("/swagger-ui.html");
    }

    private void handleExpiredToken(HttpServletResponse response) throws IOException {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("error", "token_expired");
        errorBody.put("message", "El token JWT ha expirado");
        errorBody.put("timestamp", Instant.now().toString());
        
        sendErrorResponse(response, HttpStatus.UNAUTHORIZED, errorBody);
    }

    private void handleInvalidToken(HttpServletResponse response, String message) throws IOException {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("error", "invalid_token");
        errorBody.put("message", "Token JWT inválido: " + message);
        errorBody.put("timestamp", Instant.now().toString());
        
        sendErrorResponse(response, HttpStatus.UNAUTHORIZED, errorBody);
    }

    private void handleMissingToken(HttpServletResponse response) throws IOException {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("error", "missing_token");
        errorBody.put("message", "Se requiere token de autorización");
        errorBody.put("timestamp", Instant.now().toString());
        
        sendErrorResponse(response, HttpStatus.UNAUTHORIZED, errorBody);
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, Map<String, Object> errorBody) 
            throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        String json = objectMapper.writeValueAsString(errorBody);
        response.getWriter().write(json);
    }
}