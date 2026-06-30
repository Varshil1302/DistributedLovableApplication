package com.example.disributed_lovable.CommonLib.common_lib.error;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ApiError(
        HttpStatus httpStatus,
        String message,
        Instant timeStamp
) {

    public ApiError(HttpStatus httpStatus, String message)
    {
        this(httpStatus,message,Instant.now());
    }

}
