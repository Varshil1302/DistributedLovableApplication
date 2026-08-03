package com.example.distributed_lovable.CommonLib.common_lib.event;

import lombok.Builder;

@Builder
public record FileStoredRequestEvent(
        Long projectId,
        Long sagaId,
        String filePath,
        String content,
        Long userId
) {
}
