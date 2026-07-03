package com.example.disributed_lovable.AccountService.account_service.service.impl;


import com.example.disributed_lovable.AccountService.account_service.dto.subscription.PlanResponse;
import com.example.disributed_lovable.AccountService.account_service.service.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService
{

    @Override
    public List<PlanResponse> getAllActivePlans() {
        return List.of();
    }
}
