package com.example.distributed_lovable.CommonLib.common_lib.error;

public class BadRequestException extends RuntimeException
{
    public BadRequestException(String message)
    {
        super(message);
    }
}
