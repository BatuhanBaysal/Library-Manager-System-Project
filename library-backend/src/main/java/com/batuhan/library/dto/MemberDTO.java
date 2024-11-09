package com.batuhan.library.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class MemberDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private AddressDTO address;
    @Email
    private String email;
    private String phone;
    private String membershipStarted;
    private String membershipEnded;
    private Boolean isActive;
    private String barcodeNumber;
}