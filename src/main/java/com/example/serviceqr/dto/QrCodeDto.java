package com.example.serviceqr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QrCodeDto {
    private Long id;
    private String code;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean isUsed;
}
