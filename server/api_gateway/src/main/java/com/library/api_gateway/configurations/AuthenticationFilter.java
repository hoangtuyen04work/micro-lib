package com.library.api_gateway.configurations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.api_gateway.dtos.ApiResponse;
import com.library.api_gateway.services.AuthService;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    AuthService authService;
    @Autowired
    ObjectMapper objectMapper;
    @NonFinal
    @Value("${app.api-prefix}")
    private String apiPrefix;
    @NonFinal
    private String[] publicEndPoint = {"/auth/**"};
    private final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(isPublicEndPoint(exchange.getRequest()))
            return  chain.filter(exchange);
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        String token = authHeader.getFirst().replace("Bearer ", "");
        return authService.authenticate(token).flatMap(unAuthenticate->{
            if(CollectionUtils.isEmpty(authHeader))
                return  chain.filter(exchange);
            else return unAuthenticated(exchange.getResponse());
        }).onErrorResume(throwable -> unAuthenticated(exchange.getResponse()));
    }

    Mono<Void> unAuthenticated(ServerHttpResponse response){
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(401)
                .message("Unauthentication")
                .build();
        String body;
        try{
            body = objectMapper.writeValueAsString(apiResponse);
        }
        catch(JsonProcessingException e){
            body = "{\"code\":1401,\"message\":\"Unauthenticated\"}";
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes()))
        );
    }

    @Override
    public int getOrder() {
        return -1;
    }
    private boolean isPublicEndPoint(ServerHttpRequest request){
        String path = request.getURI().getPath();
        for(String endpoint : publicEndPoint){
            if(matcher.match(apiPrefix + endpoint, path)){
                return true;
            }
        }
        return false;
    }
}
