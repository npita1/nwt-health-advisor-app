package com.example.accessingdatamysql.interceptor;
import com.example.accessingdatamysql.grpc.GrpcClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Base64;
import java.util.logging.Logger;

import static org.springframework.security.config.Elements.JWT;


@Component
public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    private GrpcClient grpcClient;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex){
        // Dohvatimo potrebne informacije o akciji

        String token = request.getHeader("Authorization");
        int userId = getUserIdFromToken(token);
        String action = request.getMethod();
        String resource = request.getRequestURI();
        String status = HttpStatus.valueOf(response.getStatus()).toString();

        // Pozovemo GrpcClient da zabilježi detalje akcije
        grpcClient.log(userId, resource, action, status);
    }
    private int getUserIdFromToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            try {
                // Uklonite "Bearer " prefiks da biste dobili stvarni token
                token = token.substring(7);

                // Podelite token na delove
                String[] parts = token.split("\\.");
                if (parts.length == 3) {
                    String payload = parts[1];

                    // Dekodirajte payload
                    byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
                    String decodedString = new String(decodedBytes);

                    // Pronađite claim "role" u dekodiranom stringu
                    String role = getClaimFromPayload(decodedString, "role");

                    if ("ROLE_USER".equals(role)) {
                        return 1;
                    } else if ("ROLE_DOCTOR".equals(role)) {
                        return 2;
                    } else if ("ROLE_ADMIN".equals(role)) {
                        return 3;
                    } else {
                        return 0; // guest
                    }
                }
            } catch (Exception exception) {
                // Nevažeći token ili greška pri dekodiranju
                return 0; // tretirajte kao gosta ako je token nevažeći
            }
        }
        return 0; // gost ako nema tokena
    }

    private String getClaimFromPayload(String payload, String claim) {
        String claimString = "\"" + claim + "\":\"";
        int startIndex = payload.indexOf(claimString);
        if (startIndex != -1) {
            startIndex += claimString.length();
            int endIndex = payload.indexOf("\"", startIndex);
            if (endIndex != -1) {
                return payload.substring(startIndex, endIndex);
            }
        }
        return null;
    }
}
