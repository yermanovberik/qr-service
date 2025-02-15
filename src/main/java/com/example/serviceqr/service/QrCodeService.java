package com.example.serviceqr.service;

import com.example.serviceqr.models.QrCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public interface QrCodeService {

      String redeemQrCode(String token);
}
