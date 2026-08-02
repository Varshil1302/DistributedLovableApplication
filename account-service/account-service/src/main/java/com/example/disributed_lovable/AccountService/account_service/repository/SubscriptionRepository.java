package com.example.disributed_lovable.AccountService.account_service.repository;


import com.example.disributed_lovable.AccountService.account_service.entity.Subscription;
import com.example.distributed_lovable.CommonLib.common_lib.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long>
{

    Optional<Subscription> findByUserUserIdAndStatusIn(Long userId, Set<SubscriptionStatus> active);

    boolean existsByStripeSubscriptionId(String subscriptionId);

    Optional<Subscription> findByStripeSubscriptionId(String subScriptionId);
}
