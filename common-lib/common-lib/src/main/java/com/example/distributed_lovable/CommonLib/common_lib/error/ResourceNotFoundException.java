package com.example.distributed_lovable.CommonLib.common_lib.error;

public class ResourceNotFoundException extends RuntimeException
{

    public ResourceNotFoundException(String message)
    {
        super(message);
    }
}
