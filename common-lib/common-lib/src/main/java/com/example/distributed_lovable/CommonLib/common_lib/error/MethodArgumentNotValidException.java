package com.example.distributed_lovable.CommonLib.common_lib.error;

public class MethodArgumentNotValidException extends RuntimeException
{
    public MethodArgumentNotValidException(String message)
    {
        super(message);
    }
}
