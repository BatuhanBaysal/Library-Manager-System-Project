package com.batuhan.library.controller;

import com.batuhan.library.dto.AddressDTO;
import com.batuhan.library.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/address")
public class AddressController {
    private AddressService addressService;

    @PostMapping("createAddress")
    // e. g. URL: http://localhost:8080/api/address/createAddress -> POST
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO) {
        AddressDTO createAddressDTO = addressService.createAddress(addressDTO);
        return new ResponseEntity<>(createAddressDTO, HttpStatus.OK);
    }

    @GetMapping("listAll")
    // e. g. URL: http://localhost:8080/api/address/listAll -> GET
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        List<AddressDTO> allAddresses = addressService.getAllAddresses();
        return new ResponseEntity<>(allAddresses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    // e. g. URL: http://localhost:8080/api/address/1 -> GET
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        AddressDTO addressDTO = addressService.getAddressById(id);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @PatchMapping("updateAddress/{id}")
    // e. g. URL: http://localhost:8080/api/address/updateAddress/1 -> PATCH
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        addressDTO.setId(id);
        AddressDTO updatedAddress = addressService.updateAddress(addressDTO);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("deleteAddress/{id}")
    // e. g. URL: http://localhost:8080/api/address/deleteAddress/3 -> DELETE
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>("Address successfully deleted.", HttpStatus.OK);
    }
}