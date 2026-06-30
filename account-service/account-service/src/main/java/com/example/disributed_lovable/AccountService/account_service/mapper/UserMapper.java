package com.example.disributed_lovable.AccountService.account_service.mapper;

import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.SignupRequest;
import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.UserProfileResponse;
import com.example.disributed_lovable.AccountService.account_service.entity.User;
import com.example.disributed_lovable.CommonLib.common_lib.dto.Userdto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Userdto toUserDto(User user);

    UserProfileResponse toUserProfileResponse(User user);

    User toUser(SignupRequest signupRequest);

}
