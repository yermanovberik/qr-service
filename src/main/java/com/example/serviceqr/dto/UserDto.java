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
    private String district;
    private String address;
    private String phone;
    private Boolean isVerified;
    private ResidentDto resident;
    private BonusDto bonus;

        public UserDto(Long id, Long userId, String iin, String district,String address, String phone, Boolean isVerified) {
            this.id = id;
            this.userId = userId;
            this.iin = iin;
            this.district =district;
            this.address = address;
            this.phone = phone;
            this.isVerified = isVerified;
        }
}
