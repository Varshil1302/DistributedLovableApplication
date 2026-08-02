package com.example.disributed_lovable.AccountService.account_service.controller;

import com.example.disributed_lovable.AccountService.account_service.mapper.UserMapper;
import com.example.disributed_lovable.AccountService.account_service.repository.UserRepository;
import com.example.distributed_lovable.CommonLib.common_lib.dto.Userdto;
import com.example.distributed_lovable.CommonLib.common_lib.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/internal/v1")
@RequiredArgsConstructor
public class InternalAccountController
{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping("/user/{userId}")
    public Userdto getUserByUserId(@PathVariable Long userId)
    {
       return userRepository.findById(userId).map(userMapper::toUserDto)
               .orElseThrow(()->new ResourceNotFoundException("User is Not Availble for respective id::"+userId));
    }

    @GetMapping("/user/by-email")
    public Userdto getUserByEmail(@RequestParam String email)
    {
        log.info("Email is ::"+email);
        return userRepository.findByEmailIgnoreCase(email)
                .map(userMapper::toUserDto)
                .orElseThrow(()->new ResourceNotFoundException("User is Not Availble for respective email::"+email));
    }

}
