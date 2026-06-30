package com.example.disributed_lovable.AccountService.account_service.service.impl;


import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.AuthResponse;
import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.LoginRequestBody;
import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.SignupRequest;
import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.UserProfileResponse;
import com.example.disributed_lovable.AccountService.account_service.entity.User;
import com.example.disributed_lovable.AccountService.account_service.mapper.UserMapper;
import com.example.disributed_lovable.AccountService.account_service.repository.UserRepository;
import com.example.disributed_lovable.AccountService.account_service.service.AuthService;
import com.example.disributed_lovable.CommonLib.common_lib.error.BadRequestException;
import com.example.disributed_lovable.CommonLib.common_lib.security.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class AuthServiceImp implements AuthService
{

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequestBody loginRequest)
    {
        boolean isExists=userRepository.existsByEmail(loginRequest.username());
        if(!isExists) throw new BadRequestException("User is not exsits now...");
        User user=userRepository.findByEmail(loginRequest.username()).orElse(null);
        boolean isPassMatch= BCrypt.checkpw(loginRequest.password(),user.getPasswordHash());
        if(!isPassMatch) throw  new BadRequestException("Password mismatch");
        String jwtToken=jwtService.generateJWT(userMapper.toUserDto(user));
        return new AuthResponse(jwtToken, userMapper.toUserProfileResponse(user));
    }

    @Override
    public UserProfileResponse signup(SignupRequest signupRequest)
    {
        userRepository.findByEmail(signupRequest.username()).ifPresent(user->{
           throw new BadRequestException("User is already exists");
        });
        User user=userMapper.toUser(signupRequest);
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userRepository.save(user);
        return new UserProfileResponse(user.getUserId(), user.getEmail(), user.getName(), user.getAvatarUrl());
    }
}
