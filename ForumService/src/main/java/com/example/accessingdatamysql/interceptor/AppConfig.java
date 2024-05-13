package com.example.accessingdatamysql.interceptor;

import com.example.accessingdatamysql.exceptions.GlobalExceptionHandler;
import com.example.accessingdatamysql.grpc.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Autowired
    private MyInterceptor myInterceptor;
    @Bean
    public GrpcClient grpcClient() {
        return GrpcClient.get();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(myInterceptor).addPathPatterns("/forum/**");
    }

}
