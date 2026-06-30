package com.example.disributed_lovable.AccountService.account_service.dto.subscription;

import java.time.Instant;

public record SubscriptionResponse(
        PlanResponse plan,
        String status,
        Instant CurrentPeriodEnd,
        Long tokensUsedThisCycle
) {
}
