package com.example.distributed_lovable.IntelligenceService.service;


import com.example.distributed_lovable.IntelligenceService.dto.chat.subscription.PlanLimitsResponse;
import com.example.distributed_lovable.IntelligenceService.dto.chat.subscription.UsageTodayResponse;

public interface UsageService {

    UsageTodayResponse getTodayUsageOfUser(Long userId);

    PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId);
}
