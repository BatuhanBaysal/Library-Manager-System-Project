package com.batuhan.library.controller;

import com.batuhan.library.dto.MemberDTO;
import com.batuhan.library.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/members")
public class MemberController {
    private MemberService memberService;

    @PostMapping("addMember")
    // e. g. URL: http://localhost:8080/api/members/addMember -> POST
    public ResponseEntity<MemberDTO> addMember(@RequestBody MemberDTO memberDTO) {
        MemberDTO savedMember = memberService.addMember(memberDTO);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @GetMapping("listAll")
    // e. g. URL: http://localhost:8080/api/members/listAll -> GET
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> allMembers = memberService.getAllMembers();
        return new ResponseEntity<>(allMembers, HttpStatus.OK);
    }

    @GetMapping("{id}")
    // e. g. URL: http://localhost:8080/api/members/1 -> GET
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
        MemberDTO memberDTO= memberService.getMemberById(id);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @PatchMapping("updateMember/{id}")
    // e. g. URL: http://localhost:8080/api/members/updateMember/1 -> PATCH
    public ResponseEntity<MemberDTO> updateMemberById(@PathVariable Long id, @RequestBody MemberDTO memberDTO) {
        memberDTO.setId(id);
        MemberDTO updatedMember= memberService.updateMember(memberDTO);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @DeleteMapping("deleteMember/{id}")
    // e. g. URL: http://localhost:8080/api/members/deleteMember/2 -> DELETE
    public ResponseEntity<String> deleteMemberById(@PathVariable Long id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>("Member successfully deleted.", HttpStatus.OK);
    }

    @GetMapping("search")
    // e. g. URL: http://localhost:8080/api/members/search?firstName=bat&lastName=ba -> GET
    public ResponseEntity<List<MemberDTO>> searchMembers(
            @RequestParam(required = false) Long cardNumber,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String barcodeNumber) {
        List<MemberDTO> members = memberService.findMembersByCriteria(cardNumber, firstName, lastName, barcodeNumber);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }
}