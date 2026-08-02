package com.example.distributed_lovable.CommonLib.common_lib.event;

public record FileStoredRequestEvent(
        Long projectId,
        Long sagaId,
        String filePath,
        String content,
        Long userId
) {
}
