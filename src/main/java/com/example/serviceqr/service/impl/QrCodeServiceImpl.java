package com.example.serviceqr.service.impl;

import com.example.serviceqr.models.Bonus;
import com.example.serviceqr.models.Order;
import com.example.serviceqr.models.QrCode;
import com.example.serviceqr.repository.BonusRepository;
import com.example.serviceqr.repository.OrderRepository;
import com.example.serviceqr.repository.QrCodeRepository;
import com.example.serviceqr.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrCodeServiceImpl implements QrCodeService {

    private final QrCodeRepository qrCodeRepository;
    private final TelegramService telegramService;
    private final BonusService bonusService;
    private final BonusRepository bonusRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public String redeemQrCode(String token) {
        log.info("Получен запрос на использование QR-кода: {}", token);

        Optional<QrCode> qrCodeOpt = qrCodeRepository.findByCode(token);

        if (qrCodeOpt.isEmpty()) {
            log.warn("Попытка использовать недействительный QR-код: {}", token);
            return "❌ Недействительный QR-код!";
        }

        QrCode qrCode = qrCodeOpt.get();

        if (qrCode.isUsed()) {
            log.warn("Попытка повторного использования QR-кода: {}", token);
            return "⚠️ QR-код уже использован!";
        }

        if (qrCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.warn("Попытка использовать просроченный QR-код: {}", token);
            return "⏳ Срок действия QR-кода истёк!";
        }

        qrCode.setUsed(true);
        qrCodeRepository.save(qrCode);
        log.info("QR-код {} успешно использован", token);

        // Обновление бонусов пользователя
        Bonus bonus = bonusRepository.findByUserId(qrCode.getUserId()).orElse(null);
        if (bonus != null) {
            log.info("Обнуление бонусов для пользователя ID {}", qrCode.getUserId());
            bonus.setBalance(0);
            bonus.setLastUpdate(LocalDateTime.now());
            bonusRepository.save(bonus);
        }

        // Обновление статуса заказа
        Order order = orderRepository.findById(qrCode.getOrderId()).orElse(null);
        if (order != null) {
            log.info("Обновление статуса заказа ID {} на 'DONE'", order.getId());
            order.setStatus("DONE");
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);
        }

        // Отправка уведомления пользователю
        log.info("Отправка сообщения пользователю ID {} через Telegram", qrCode.getUserId());
        telegramService.sendMessage(qrCode.getUserId(), "✅ Бонус успешно использован!");

        return "🎉 Бонус успешно использован!";
    }
}
