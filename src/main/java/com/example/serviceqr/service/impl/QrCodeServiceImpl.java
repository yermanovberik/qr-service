package com.example.serviceqr.service.impl;

import com.example.serviceqr.models.Bonus;
import com.example.serviceqr.models.Order;
import com.example.serviceqr.models.QrCode;
import com.example.serviceqr.repository.BonusRepository;
import com.example.serviceqr.repository.OrderRepository;
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
    private final BonusRepository bonusRepository;
    private final OrderRepository orderRepository;
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

        Bonus bonus = bonusRepository.findByUserId(qrCode.getUserId())
                .orElse(null);

        if(bonus!=null) {
            bonus.setBalance(0);
            bonus.setLastUpdate(LocalDateTime.now());
            bonusRepository.save(bonus);
        }

        Order order = orderRepository.findById(qrCode.getOrderId())
                        .orElse(null);

        if(order != null) {
            order.setStatus("DONE");
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);
        }

        telegramService.sendMessage(qrCode.getUserId(), "✅ Бонус успешно использован!");

        return "🎉 Бонус успешно использован!";
    }
}
