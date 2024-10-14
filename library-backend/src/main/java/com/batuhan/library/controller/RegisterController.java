package com.batuhan.library.controller;

import com.batuhan.library.dto.RegisterDTO;
import com.batuhan.library.service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/registers")
public class RegisterController {
    private RegisterService registerService;

    @PostMapping("createRegister")
    // URL : http://localhost:8080/api/registers/createRegister -> POST
    public ResponseEntity<RegisterDTO> createRegister(@RequestBody RegisterDTO registerDTO) {
        RegisterDTO register = registerService.createRegister(registerDTO);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }

    @GetMapping("listAll")
    // URL : http://localhost:8080/api/registers/listAll -> GET
    public ResponseEntity<List<RegisterDTO>> getAllRegisters() {
        List<RegisterDTO> registers = registerService.getAllRegisters();
        return new ResponseEntity<>(registers, HttpStatus.OK);
    }

    @GetMapping("{id}")
    // URL : http://localhost:8080/api/registers/1 -> GET
    public ResponseEntity<RegisterDTO> getRegisterById(@PathVariable Long id) {
        RegisterDTO register = registerService.getRegisterById(id);
        return new ResponseEntity<>(register, HttpStatus.OK);
    }

    @PatchMapping("updateRegister/{id}")
    // URL : http://localhost:8080/api/registers/updateRegister/1 -> PATCH
    public ResponseEntity<RegisterDTO> updateRegister(@PathVariable Long id, @RequestBody RegisterDTO registerDTO) {
        registerDTO.setId(id);
        RegisterDTO updatedRegister = registerService.updateRegister(registerDTO);
        return new ResponseEntity<>(updatedRegister, HttpStatus.OK);
    }

    @DeleteMapping("deleteRegister/{id}")
    // URL : http://localhost:8080/api/registers/deleteRegister/2 -> DELETE
    public ResponseEntity<String> deleteRegister(@PathVariable Long id) {
        registerService.deleteRegister(id);
        return new ResponseEntity<>("Checkout register deleted.", HttpStatus.OK);
    }

    @GetMapping("member/{memberId}")
    // URL : http://localhost:8080/api/registers/member/1 -> GET
    public ResponseEntity<List<RegisterDTO>> getRegistersByMember(@PathVariable Long memberId) {
        List<RegisterDTO> registers = registerService.getRegisterByMemberId(memberId);
        return new ResponseEntity<>(registers, HttpStatus.OK);
    }

    @GetMapping("book/{bookId}")
    // URL : http://localhost:8080/api/registers/book/3 -> GET
    public ResponseEntity<List<RegisterDTO>> getRegistersByBook(@PathVariable Long bookId) {
        List<RegisterDTO> registers = registerService.getRegisterByBookId(bookId);
        return new ResponseEntity<>(registers, HttpStatus.OK);
    }
}