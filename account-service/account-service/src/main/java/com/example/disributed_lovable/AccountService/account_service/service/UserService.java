package com.example.disributed_lovable.AccountService.account_service.service;


import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.UserProfileResponse;

public interface UserService
{
    UserProfileResponse getProfile(Long userId);
}
