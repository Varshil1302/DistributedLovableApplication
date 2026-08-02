package com.example.distributed_lovable.APIGateway.api_gateway.filter;


import com.example.distributed_lovable.APIGateway.api_gateway.config.SecurityProperties;
import com.example.distributed_lovable.APIGateway.api_gateway.service.GatewayAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;

@Component
@Slf4j
@RequiredArgsConstructor
public class GatewayJwtAuthFilter implements GlobalFilter, Order {


    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final GatewayAuthService gatewayAuthService;
    private final SecurityProperties securityProperties;

    @Override
    public int value() {
        return -1;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        log.info("Path is::{}",path);
        log.info("public Routes::{}",securityProperties.getPublicRoutes());
        boolean isPublic = securityProperties.getPublicRoutes().stream().anyMatch(p->antPathMatcher.match(p,path));
        log.info("Public::{}",isPublic);
        if(isPublic)
        {
            log.info("Request is public, continue: {}",path);
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer "))
        {

        }
        String token = authHeader.substring(7);
        try{
            gatewayAuthService.validateToken(token);
            log.info("Token is valid for this secure path: {}",path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return chain.filter(exchange);
    }
}
