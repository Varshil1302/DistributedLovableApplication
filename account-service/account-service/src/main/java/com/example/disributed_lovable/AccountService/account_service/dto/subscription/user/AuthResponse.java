package com.example.disributed_lovable.AccountService.account_service.dto.subscription.user;

public record AuthResponse(
        String token,
        UserProfileResponse user
) {
}
