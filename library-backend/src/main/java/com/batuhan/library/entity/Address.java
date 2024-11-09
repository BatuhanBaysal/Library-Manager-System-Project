package com.batuhan.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "address")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String addressName;

    @Column(nullable = false)
    @NotNull
    private String addressNumber;

    @Column
    private String zipCode;

    @Column(nullable = false)
    @NotNull
    private String placeName;

    @Column(nullable = false)
    @NotNull
    private String country;

    @Column
    private String additionalInfo;
}