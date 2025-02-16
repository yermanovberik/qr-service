package com.example.serviceqr.service.impl;

import com.example.serviceqr.dto.BonusDto;
import com.example.serviceqr.dto.QrCodeDto;
import com.example.serviceqr.dto.ResidentDto;
import com.example.serviceqr.dto.UserDto;
import com.example.serviceqr.models.Bonus;
import com.example.serviceqr.models.QrCode;
import com.example.serviceqr.models.Resident;
import com.example.serviceqr.models.Users;
import com.example.serviceqr.repository.BonusRepository;
import com.example.serviceqr.repository.QrCodeRepository;
import com.example.serviceqr.repository.ResidentRepository;
import com.example.serviceqr.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UsersRepository usersRepository;
    private final ResidentRepository residentRepository;
    private final BonusRepository bonusRepository;
    private final QrCodeRepository qrCodeRepository;


    public List<UserDto> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        List<UserDto> response = new ArrayList<>();

        // Получаем всех резидентов за один запрос и создаем Map<UserId, Resident>
        Map<Long, Resident> residentMap = residentRepository.findAll().stream()
                .collect(Collectors.toMap(Resident::getUserId, resident -> resident));

        for (Users user : users) {
            Resident resident = residentMap.get(user.getUserId());

            // Получаем бонус пользователя (можно оптимизировать в future)
            Bonus bonus = bonusRepository.findByUserId(user.getUserId()).orElse(null);

            // Создаем DTO пользователя
            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getUserId(),
                    user.getIin(),
                    user.getDistrict(),
                    user.getAddress(),
                    user.getPhone(),
                    user.getIsVerified()
            );

            // Добавляем ResidentDto, если есть резидент
            if (resident != null) {
                ResidentDto residentDto = new ResidentDto(
                        resident.getId(),
                        resident.getUserId(),
                        resident.getAdults(),
                        resident.getChildren(),
                        resident.getRenters(),
                        resident.getTimestamp()
                );
                userDto.setResident(residentDto);
            }

            // Добавляем BonusDto, если есть бонус
            if (bonus != null) {
                BonusDto bonusDto = new BonusDto(
                        bonus.getId(),
                        bonus.getUserId(),
                        bonus.getBalance(),
                        bonus.getLastUpdate()
                );
                userDto.setBonus(bonusDto); // ✅ Устанавливаем в `UserDto`
            }

            response.add(userDto);
        }

        return response;
    }


    public List<BonusDto> getAllBonuses() {
        List<Bonus> bonuses = bonusRepository.findAll();
        List<BonusDto> response = new ArrayList<>();
        for (Bonus bonus : bonuses) {
            response.add(new BonusDto(bonus.getId(), bonus.getUserId(), bonus.getBalance(), bonus.getLastUpdate()));
        }
        return response;
    }
    public List<QrCodeDto> getAllQrCodes() {
        List<QrCode> qrCodes = qrCodeRepository.findAll();
        List<QrCodeDto> response = new ArrayList<>();
        for (QrCode qrCode : qrCodes) {
            response.add(new QrCodeDto(qrCode.getId(), qrCode.getCode(), qrCode.getUserId(), qrCode.getCreatedAt(), qrCode.getExpiresAt(), qrCode.isUsed()));
        }
        return response;
    }

    public List<ResidentDto> getAllResidents() {
        List<Resident> residents = residentRepository.findAll();
        List<ResidentDto> response = new ArrayList<>();
        for (Resident resident : residents) {
            response.add(new ResidentDto(resident.getId(), resident.getUserId(), resident.getAdults(), resident.getChildren(),resident.getRenters(), resident.getTimestamp()));
        }
        return response;
    }
}
