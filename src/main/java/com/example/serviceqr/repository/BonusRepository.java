package com.example.serviceqr.repository;

import com.example.serviceqr.models.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
    Optional<Bonus> findByUserId(Long userId);

}