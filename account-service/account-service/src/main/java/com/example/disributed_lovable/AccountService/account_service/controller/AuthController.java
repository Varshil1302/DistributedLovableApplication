package com.example.disributed_lovable.AccountService.account_service.controller;


import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.AuthResponse;
import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.LoginRequestBody;
import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.SignupRequest;
import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.UserProfileResponse;
import com.example.disributed_lovable.AccountService.account_service.service.AuthService;
import com.example.disributed_lovable.AccountService.account_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController
{
     private final AuthService authService;
     private final UserService userService;

     @PostMapping("/login")
     public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequestBody loginRequest)
     {
         return ResponseEntity.ok(authService.login(loginRequest));
     }

     @PostMapping("/signup")
     public ResponseEntity<UserProfileResponse> signup(@RequestBody SignupRequest signupRequest)
     {
         return ResponseEntity.ok(authService.signup(signupRequest));
     }

     @GetMapping("/me")
     public ResponseEntity<UserProfileResponse> getProfile()
     {
         Long userId=1L;
         return ResponseEntity.ok(userService.getProfile(userId));

     }

}
