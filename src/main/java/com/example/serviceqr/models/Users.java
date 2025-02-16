package com.example.serviceqr.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private String iin;

    private String address;

    @Column(name = "district")
    private String district;

    private String phone;

    @Column(name = "is_verified")
    private Boolean isVerified;
}
