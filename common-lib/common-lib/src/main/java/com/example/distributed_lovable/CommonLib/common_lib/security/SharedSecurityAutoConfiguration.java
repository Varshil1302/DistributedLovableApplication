package com.example.distributed_lovable.CommonLib.common_lib.security;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@AutoConfiguration
public class SharedSecurityAutoConfiguration
{
    @Bean
    public JwtService getJwtService()
    {
        return new JwtService();
    }

    @Bean
    public JwtAuthFilter getJwtAuhFilter(JwtService jwtService, HandlerExceptionResolver handlerExceptionResolver)
    {
        return new JwtAuthFilter(jwtService,handlerExceptionResolver);
    }

    @Bean
    public RequestInterceptor getRequestInterceptor()
    {
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("Feign thread = {}", Thread.currentThread().getName());
            log.info("Authentication = {}", authentication);
            if(authentication!=null && authentication.getCredentials() instanceof String token)
            {
                requestTemplate.header("Authorization","Bearer " + token);
            }
        };
    }
}
