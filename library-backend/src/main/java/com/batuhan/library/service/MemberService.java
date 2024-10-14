package com.batuhan.library.service;

import com.batuhan.library.dto.MemberDTO;

import java.util.List;

public interface MemberService {
    MemberDTO addMember(MemberDTO memberDTO);
    List<MemberDTO> getAllMembers();
    MemberDTO getMemberById(Long id);
    MemberDTO updateMember(MemberDTO memberDTO);
    void deleteMember(Long id);
    List<MemberDTO> findMembersByCriteria(Long id, String firstName, String lastName, String barcodeNumber);
}