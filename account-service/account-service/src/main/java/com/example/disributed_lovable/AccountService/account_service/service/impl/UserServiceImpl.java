package com.example.disributed_lovable.AccountService.account_service.service.impl;


import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.UserProfileResponse;
import com.example.disributed_lovable.AccountService.account_service.repository.UserRepository;
import com.example.disributed_lovable.AccountService.account_service.service.UserService;
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

   /* @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not exists..."));
    }*/
}
