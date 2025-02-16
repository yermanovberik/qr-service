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
public class ResidentDto {
    private Long id;
    private Long userId;
    private Long adults;
    private Long children;
    private Long renters;
    private LocalDateTime timestamp;
}