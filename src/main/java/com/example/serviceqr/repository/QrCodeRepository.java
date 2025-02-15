package com.example.serviceqr.repository;

import com.example.serviceqr.models.QrCode;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, String> {
    Optional<QrCode> findByCode(String token);
}