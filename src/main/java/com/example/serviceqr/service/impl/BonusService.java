package com.example.serviceqr.service.impl;

import com.example.serviceqr.repository.BonusRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BonusService {

    private final BonusRepository bonusRepository;

    @Transactional
    public void resetBonusBalance(Long userId) {
        bonusRepository.findById(userId).ifPresent(bonus -> {
            bonus.setBalance(0);
            bonus.setLastUpdate(LocalDateTime.now());
            bonusRepository.save(bonus);
        });
    }
}