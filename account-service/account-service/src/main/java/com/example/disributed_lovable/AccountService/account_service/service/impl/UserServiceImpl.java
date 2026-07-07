package com.example.disributed_lovable.AccountService.account_service.service.impl;


import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.UserProfileResponse;
import com.example.disributed_lovable.AccountService.account_service.entity.User;
import com.example.disributed_lovable.AccountService.account_service.repository.UserRepository;
import com.example.disributed_lovable.AccountService.account_service.service.UserService;
import com.example.disributed_lovable.CommonLib.common_lib.dto.Userdto;
import com.example.disributed_lovable.CommonLib.common_lib.error.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService, UserDetailsService
{

    UserRepository userRepository;

    @Override
    public UserProfileResponse getProfile(Long userId) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                         .orElseThrow(()->new ResourceNotFoundException("User is not found with email:: "+username));

        return new Userdto(
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getPasswordHash()
        );
    }
}
