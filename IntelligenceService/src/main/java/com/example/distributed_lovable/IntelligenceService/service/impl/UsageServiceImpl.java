package com.example.distributed_lovable.IntelligenceService.service.impl;


import com.example.distributed_lovable.IntelligenceService.dto.chat.subscription.PlanLimitsResponse;
import com.example.distributed_lovable.IntelligenceService.dto.chat.subscription.UsageTodayResponse;
import com.example.distributed_lovable.IntelligenceService.service.UsageService;
import org.springframework.stereotype.Service;

@Service
public class UsageServiceImpl implements UsageService
{

    @Override
    public UsageTodayResponse getTodayUsageOfUser(Long userId) {
        return null;
    }

    @Override
    public PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId) {
        return null;
    }
}
