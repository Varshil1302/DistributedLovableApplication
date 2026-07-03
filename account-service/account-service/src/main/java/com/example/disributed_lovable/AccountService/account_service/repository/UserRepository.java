package com.example.disributed_lovable.AccountService.account_service.repository;


import com.example.disributed_lovable.AccountService.account_service.entity.User;
import com.stripe.net.HttpHeaders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>
{

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByUsernameIgnoreCase(String email);
}
