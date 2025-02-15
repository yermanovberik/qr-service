package com.example.serviceqr.service.impl;

import com.example.serviceqr.models.QrCode;
import com.example.serviceqr.repository.QrCodeRepository;
import com.example.serviceqr.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {

    private final QrCodeRepository qrCodeRepository;
    private final TelegramService telegramService;
    private final BonusService bonusService;

    @Transactional
    public String redeemQrCode(String token) {
        Optional<QrCode> qrCodeOpt = qrCodeRepository.findByCode(token);

        if (qrCodeOpt.isEmpty()) {
            return "❌ Недействительный QR-код!";
        }

        QrCode qrCode = qrCodeOpt.get();

        if (qrCode.isUsed()) {
            return "⚠️ QR-код уже использован!";
        }

        if (qrCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "⏳ Срок действия QR-кода истёк!";
        }

        qrCode.setUsed(true);
        qrCodeRepository.save(qrCode);

        bonusService.resetBonusBalance(qrCode.getUserId());

        telegramService.sendMessage(qrCode.getUserId(), "✅ Бонус успешно использован!");

        return "🎉 Бонус успешно использован!";
    }
}
