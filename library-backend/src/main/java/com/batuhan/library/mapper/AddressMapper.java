package com.batuhan.library.mapper;

import com.batuhan.library.dto.AddressDTO;
import com.batuhan.library.entity.Address;

public class AddressMapper {

    public static AddressDTO mapToAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setAddressName(address.getAddressName());
        addressDTO.setAddressNumber(address.getAddressNumber());
        addressDTO.setZipCode(address.getZipCode());
        addressDTO.setPlaceName(address.getPlaceName());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setAdditionalInfo(address.getAdditionalInfo());
        return addressDTO;
    }

    public static Address mapToAddressEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setAddressName(addressDTO.getAddressName());
        address.setAddressNumber(addressDTO.getAddressNumber());
        address.setZipCode(addressDTO.getZipCode());
        address.setPlaceName(addressDTO.getPlaceName());
        address.setCountry(addressDTO.getCountry());
        address.setAdditionalInfo(addressDTO.getAdditionalInfo());
        return address;
    }
}