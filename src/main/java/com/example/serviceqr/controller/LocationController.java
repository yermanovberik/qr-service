package com.example.serviceqr.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
class LocationController {

    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search?format=json&q=";
    private final RestTemplate restTemplate = new RestTemplate();

    private Map<String, String> getCoordinates(String address) {
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(NOMINATIM_URL + address, List.class);
            response.getBody();
            if (!response.getBody().isEmpty()) {
                Map<String, Object> location = (Map<String, Object>) response.getBody().get(0);
                Map<String, String> coordinates = new HashMap<>();
                coordinates.put("lat", location.get("lat").toString());
                coordinates.put("lng", location.get("lon").toString());
                return coordinates;
            }
        } catch (Exception e) {
            System.err.println("Ошибка при запросе к Nominatim: " + e.getMessage());
        }
        return null;
    }

    @GetMapping("/locations")
    public Map<String, Object> getLocations() {
        List<String> clients = List.of(
                "Алматы, ул Пушкина 12",
                "Алматы, проспект Абая 50",
                "Алматы, ул Достык 100"
        );

        List<String> recyclingMachines = List.of(
                "Алматы, Абая 100",
                "Алматы, Сейфулина 609"
        );

        List<String> waterPoints = List.of(
                "Алматы, Назарбаева 58",
                "Алматы, Тимирязева 42"
        );

        List<String> factories = List.of(
                "Каскелен, Бурашева 34",
                "Шамалган, Абиш 1"
        );

        Map<String, Object> response = new HashMap<>();
        response.put("clients", clients.stream().map(addr -> Map.of("address", addr, "coordinates", getCoordinates(addr))).toList());
        response.put("recycling_machines", recyclingMachines.stream().map(addr -> Map.of("address", addr, "coordinates", getCoordinates(addr))).toList());
        response.put("water_points", waterPoints.stream().map(addr -> Map.of("address", addr, "coordinates", getCoordinates(addr))).toList());
        response.put("factories", factories.stream().map(addr -> Map.of("address", addr, "coordinates", getCoordinates(addr))).toList());

        return response;
    }

}