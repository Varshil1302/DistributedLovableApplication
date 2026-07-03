package com.example.disributed_lovable.AccountService.account_service.mapper;


import com.example.disributed_lovable.AccountService.account_service.dto.subscription.PlanResponse;
import com.example.disributed_lovable.AccountService.account_service.dto.subscription.SubscriptionResponse;
import com.example.disributed_lovable.AccountService.account_service.entity.Plan;
import com.example.disributed_lovable.AccountService.account_service.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    PlanResponse fromPlantoPlanResponse(Plan plan);

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

}
