package com.example.disributed_lovable.CommonLib.common_lib.security;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;

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
}
