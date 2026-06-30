package com.example.disributed_lovable.AccountService.account_service.dto.subscription.user;

public record UserProfileResponse(
        Long id,
        String email,
        String name,
        String avatarUrl
) {
}
