package com.example.accessingdatamysql.ApiGateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/authentication/register",
            "/authentication/login",
            "/authentication/refresh-token",
            "/webjars/",
            "/v2/",
            "/actuator/",
            "/swagger-resources/",
            "/v3/api-docs/",
            "/swagger-ui/",
            "/swagger-ui.html"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}