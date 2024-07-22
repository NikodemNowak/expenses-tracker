package com.nikodem.expenses_tracker.repository;

import com.nikodem.expenses_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<List<User>> findAllByExpiredIsFalse();
    Optional<User> findByIdAndExpiredIsFalse(UUID id);
    Optional<User> findByEmailAndExpiredIsFalse(String email);
}
