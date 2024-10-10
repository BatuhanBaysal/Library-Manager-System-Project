package com.batuhan.library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String addressName;

    @Column(nullable = false)
    private String addressNumber;

    private String zipCode;

    @Column(nullable = false)
    private String placeName;

    @Column(nullable = false)
    private String country;

    private String additionalInfo;
}