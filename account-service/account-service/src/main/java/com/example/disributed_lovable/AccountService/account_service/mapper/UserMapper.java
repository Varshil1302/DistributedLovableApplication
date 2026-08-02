package com.example.disributed_lovable.AccountService.account_service.mapper;

import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.SignupRequest;
import com.example.disributed_lovable.AccountService.account_service.dto.subscription.user.UserProfileResponse;
import com.example.disributed_lovable.AccountService.account_service.entity.User;
import com.example.distributed_lovable.CommonLib.common_lib.dto.Userdto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", source = "passwordHash")
    @Mapping(target = "username",source = "email")
    @Mapping(target = "id",source = "userId")
    Userdto toUserDto(User user);

    UserProfileResponse toUserProfileResponse(User user);

    @Mapping(target = "email", source = "username")
    @Mapping(target = "passwordHash", source = "password")
    User toUser(SignupRequest signupRequest);

}
