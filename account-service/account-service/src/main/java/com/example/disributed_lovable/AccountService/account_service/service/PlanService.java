package com.example.disributed_lovable.AccountService.account_service.service;



import com.example.disributed_lovable.AccountService.account_service.dto.subscription.PlanResponse;

import java.util.List;

public interface PlanService
{

     List<PlanResponse> getAllActivePlans();
}
