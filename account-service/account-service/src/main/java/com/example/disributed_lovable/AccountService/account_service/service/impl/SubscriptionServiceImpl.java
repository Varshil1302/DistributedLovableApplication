package com.example.disributed_lovable.AccountService.account_service.service.impl;


import com.example.disributed_lovable.AccountService.account_service.dto.subscription.SubscriptionResponse;
import com.example.disributed_lovable.AccountService.account_service.entity.Plan;
import com.example.disributed_lovable.AccountService.account_service.entity.Subscription;
import com.example.disributed_lovable.AccountService.account_service.entity.User;
import com.example.disributed_lovable.AccountService.account_service.mapper.SubscriptionMapper;
import com.example.disributed_lovable.AccountService.account_service.repository.PlanRepository;
import com.example.disributed_lovable.AccountService.account_service.repository.SubscriptionRepository;
import com.example.disributed_lovable.AccountService.account_service.repository.UserRepository;
import com.example.disributed_lovable.AccountService.account_service.service.SubscriptionService;
import com.example.distributed_lovable.CommonLib.common_lib.enums.SubscriptionStatus;
import com.example.distributed_lovable.CommonLib.common_lib.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService
{
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public SubscriptionResponse getCurrentSubscription() {
       Long userId=getCurrentUser();
        Subscription subscription=subscriptionRepository.findByUserUserIdAndStatusIn(userId, Set.of(SubscriptionStatus.ACTIVE,SubscriptionStatus.PAST_DUE,SubscriptionStatus.TRIALING)).orElse(
                new Subscription()
        );

        return subscriptionMapper.toSubscriptionResponse(subscription);
    }

    @Override
    public void activateSubcription(Long userId, Long planId, String subscriptionId, String customerId) 
    {
           boolean exists = subscriptionRepository.existsByStripeSubscriptionId(subscriptionId);
           if(exists) return;
           User user= getUser(userId);
           Plan plan= getPlan(planId);
           Subscription subscription=Subscription.builder()
                   .stripeSubscriptionId(subscriptionId).user(user).plan(plan).status(SubscriptionStatus.INCOMPLETE)
                                         .build();
           subscriptionRepository.save(subscription);
           log.info("Subscription is created::{}",subscription.getStripeSubscriptionId());
    }

    @Override
    @Transactional
    public void updateSubscription(String subscriptionId, SubscriptionStatus subscriptionStatus,
                                   Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId)
    {

        Subscription subscription = subscriptionRepository.findByStripeSubscriptionId(subscriptionId).orElse(null);
        log.info("sub status:{}"+subscription.getStatus());
        log.info("parameter sub status:{}"+subscriptionStatus);

        boolean HasSubscriptionUpdated=false;

        if(subscriptionStatus!=null && subscription.getStatus() != subscriptionStatus){
            log.info("Subscription Status before Update:{}",subscription.getStatus());
            subscription.setStatus(subscriptionStatus);
            HasSubscriptionUpdated=true;
            log.info("Subscription Status after Update:{}" ,subscription.getStatus());
        }

        if(periodStart != null && !subscription.getCurrentPeriodStart().equals(periodStart)){
            subscription.setCurrentPeriodStart(periodStart);
            HasSubscriptionUpdated=true;
        }

        if(periodEnd != null && !subscription.getCurrentPeriodEnd().equals(periodEnd)){
            subscription.setCurrentPeriodEnd(periodEnd);
            HasSubscriptionUpdated=true;
        }

        if(cancelAtPeriodEnd!=null && !subscription.getCancelAtPeriodEnd().equals(cancelAtPeriodEnd)){
            subscription.setCancelAtPeriodEnd(cancelAtPeriodEnd);
            HasSubscriptionUpdated=true;
        }

        if(planId!=null && subscription.getPlan().getId()!=planId){
            Plan newPlan = getPlan(planId);
            subscription.setPlan(newPlan);
            HasSubscriptionUpdated=true;
        }
        if(HasSubscriptionUpdated)
        {
            log.info("Subscription is updated with subscribeId:{}",subscriptionId);
            subscriptionRepository.save(subscription);
        }

    }

    @Override
    public void cancelSubscription(String subscriptionId) {
        Subscription subscription = subscriptionRepository.findByStripeSubscriptionId(subscriptionId).orElse(null);
        log.info("Subscription Status:{}",subscription.getStatus());
        subscription.setStatus(SubscriptionStatus.CANCELED);
        subscriptionRepository.save(subscription);
        log.info("Subscription Saved and Status:{}",subscription.getStatus());
    }

    @Override
    public void renewSubscriptionPeriod(String subScriptionId, Instant periodStart, Instant periodEnd)
    {
        Subscription subscription = subscriptionRepository.findByStripeSubscriptionId(subScriptionId).orElse(null);

        Instant newStart = periodStart != null ? periodStart : subscription.getCurrentPeriodEnd();
        subscription.setCurrentPeriodStart(newStart);
        subscription.setCurrentPeriodEnd(periodEnd);
        if(subscription.getStatus() == SubscriptionStatus.PAST_DUE || subscription.getStatus() == SubscriptionStatus.INCOMPLETE) {
            subscription.setStatus(SubscriptionStatus.ACTIVE);
        }
        subscriptionRepository.save(subscription);
    }

    @Override
    public void marksubscriptionPastDue(String subScriptionId) {

          Subscription subscription = subscriptionRepository.findByStripeSubscriptionId(subScriptionId).orElse(null);

          if(subscription.getStatus()==SubscriptionStatus.PAST_DUE){
              log.warn("Subscription is already past due");
              return;
          }
          subscription.setStatus(SubscriptionStatus.PAST_DUE);
          subscriptionRepository.save(subscription);
    }
    private Long getCurrentUser() {
        return jwtService.getCurrentUser();
    }
    private Plan getPlan(Long planId) {
        return planRepository.findById(planId).orElseThrow();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}
