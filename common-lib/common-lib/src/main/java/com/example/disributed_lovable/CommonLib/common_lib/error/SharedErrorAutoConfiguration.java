package com.example.disributed_lovable.CommonLib.common_lib.error;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class SharedErrorAutoConfiguration
{
    @Bean
    public GlobalExceptionHandler getGobalException()
    {
        return new GlobalExceptionHandler();
    }
}
