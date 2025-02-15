package com.example.serviceqr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private Long userId;
    private String iin;
    private String address;
    private String phone;
    private Boolean isVerified;
}
