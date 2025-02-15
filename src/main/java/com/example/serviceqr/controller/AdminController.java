package com.example.serviceqr.controller;

import com.example.serviceqr.dto.BonusDto;
import com.example.serviceqr.dto.QrCodeDto;
import com.example.serviceqr.dto.ResidentDto;
import com.example.serviceqr.dto.UserDto;
import com.example.serviceqr.service.QrCodeService;
import com.example.serviceqr.service.impl.AdminService;
import com.example.serviceqr.service.impl.BonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/residents")
    public ResponseEntity<List<ResidentDto>> getAllResidents() {
        return ResponseEntity.ok(adminService.getAllResidents());
    }

    @GetMapping("/qr-codes")
    public ResponseEntity<List<QrCodeDto>> getAllQrCodes() {
        return ResponseEntity.ok(adminService.getAllQrCodes());
    }

    @GetMapping("/bonuses")
    public ResponseEntity<List<BonusDto>> getAllBonuses() {
        return ResponseEntity.ok(adminService.getAllBonuses());
    }

}
