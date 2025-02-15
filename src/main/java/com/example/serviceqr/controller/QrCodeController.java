package com.example.serviceqr.controller;


import com.example.serviceqr.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
@RequiredArgsConstructor
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @GetMapping("/redeem")
    public ResponseEntity<String> redeemQr(@RequestParam String token) {
        String responseMessage = qrCodeService.redeemQrCode(token);
        return ResponseEntity.ok(responseMessage);
    }
}
