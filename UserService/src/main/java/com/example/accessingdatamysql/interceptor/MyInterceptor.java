package com.example.accessingdatamysql.interceptor;

import com.example.accessingdatamysql.grpc.GrpcClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.logging.Logger;


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

        int userId=0;
        if(request.getAttribute("userId")!=null){
            userId = (Integer) request.getAttribute("userId");
        }
        String action = request.getMethod();
        String resource = request.getRequestURI();
        String status = response.getStatus() == 200 ? "Uspješno završena akcija" : "Greška";

        // Pozovemo GrpcClient da zabilježi detalje akcije
        grpcClient.log(userId, resource, action, status);
    }
}