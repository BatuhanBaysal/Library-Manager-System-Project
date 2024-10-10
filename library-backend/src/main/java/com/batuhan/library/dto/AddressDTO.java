package com.batuhan.library.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    private String addressName;
    private String addressNumber;
    private String zipCode;
    private String placeName;
    private String country;
    private String additionalInfo;
}