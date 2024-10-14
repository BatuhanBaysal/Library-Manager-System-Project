package com.batuhan.library.mapper;

import com.batuhan.library.dto.MemberDTO;
import com.batuhan.library.entity.Member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MemberMapper {

    public static MemberDTO mapToMemberDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setFirstName(member.getFirstName());
        memberDTO.setLastName(member.getLastName());

        // store LocalDate as String
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        if(member.getDateOfBirth() != null) {
            memberDTO.setDateOfBirth(member.getDateOfBirth().format(formatter));
        }

        // map address
        if (member.getAddress() != null) {
            memberDTO.setAddress(AddressMapper.mapToAddressDTO(member.getAddress()));
        }

        memberDTO.setEmail(member.getEmail());
        memberDTO.setPhone(member.getPhone());
        memberDTO.setBarcodeNumber(member.getBarcodeNumber());

        // store LocalDate as String
        if (member.getMembershipStarted() != null) {
            memberDTO.setMembershipStarted(member.getMembershipStarted().format(formatter));
        }
        if (member.getMembershipEnded() != null) {
            memberDTO.setMembershipEnded(member.getMembershipEnded().format(formatter));
        }

        memberDTO.setIsActive(member.getIsActive());
        return memberDTO;
    }

    public static Member mapToMemberEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setFirstName(memberDTO.getFirstName());
        member.setLastName(memberDTO.getLastName());

        // map String from dto to LocalDate in entity
        member.setDateOfBirth(LocalDate.parse(memberDTO.getDateOfBirth()));
        // map address
        if (memberDTO.getAddress() != null) {
            member.setAddress(AddressMapper.mapToAddressEntity(memberDTO.getAddress()));
        }

        member.setEmail(memberDTO.getEmail());
        member.setPhone(memberDTO.getPhone());
        member.setBarcodeNumber(memberDTO.getBarcodeNumber());

        // map String from dto to LocalDate in entity
        member.setMembershipStarted(LocalDate.parse(memberDTO.getMembershipStarted()));
        if (memberDTO.getMembershipEnded() != null) {
            member.setMembershipEnded(LocalDate.parse(memberDTO.getMembershipEnded()));
        }

        member.setIsActive(memberDTO.getIsActive());
        return member;
    }
}