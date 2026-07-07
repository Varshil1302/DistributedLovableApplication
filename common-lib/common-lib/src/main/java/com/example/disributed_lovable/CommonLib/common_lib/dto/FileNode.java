package com.example.disributed_lovable.CommonLib.common_lib.dto;

public record FileNode(
        String path
) {
    @Override
    public String toString() {
        return path;
    }
}
