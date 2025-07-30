package com.bankkemal.account.repository;

import com.bankkemal.account.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
     Optional<User> findByUsername(String username);
     List<User> findAllByDeletedFalse();
     Optional<User> findByUserIdAndDeletedFalse(UUID userId);
}

