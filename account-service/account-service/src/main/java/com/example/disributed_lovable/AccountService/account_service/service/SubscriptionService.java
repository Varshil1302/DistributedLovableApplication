package com.example.disributed_lovable.AccountService.account_service.service;



import com.example.disributed_lovable.AccountService.account_service.dto.subscription.SubscriptionResponse;
import com.example.disributed_lovable.CommonLib.common_lib.enums.SubscriptionStatus;

import java.time.Instant;

public interface SubscriptionService
{

     SubscriptionResponse getCurrentSubscription();

    void activateSubcription(Long userId, Long planId, String subscriptionId, String customerId);

    void updateSubscription(String subscriptionId, SubscriptionStatus subscriptionStatus, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId);

    void cancelSubscription(String subscriptionId);

    void renewSubscriptionPeriod(String subScriptionId, Instant periodStart, Instant periodEnd);

    void marksubscriptionPastDue(String subScriptionId);
}
