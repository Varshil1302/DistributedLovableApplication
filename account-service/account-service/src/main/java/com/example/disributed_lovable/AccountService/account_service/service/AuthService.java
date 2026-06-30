package com.example.disributed_lovable.AccountService.account_service.service;


import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.AuthResponse;
import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.LoginRequestBody;
import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.SignupRequest;
import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.UserProfileResponse;

public interface AuthService
{

     AuthResponse login(LoginRequestBody loginRequest);

     UserProfileResponse signup(SignupRequest signupRequest);
}
