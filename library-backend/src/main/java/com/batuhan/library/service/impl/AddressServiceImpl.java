package com.batuhan.library.service.impl;

import com.batuhan.library.dto.AddressDTO;
import com.batuhan.library.entity.Address;
import com.batuhan.library.mapper.AddressMapper;
import com.batuhan.library.repository.AddressRepository;
import com.batuhan.library.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = AddressMapper.mapToAddressEntity(addressDTO);
        address = addressRepository.save(address);
        return AddressMapper.mapToAddressDTO(address);
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(AddressMapper::mapToAddressDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO getAddressById(Long id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        Address address = optionalAddress.get();
        return AddressMapper.mapToAddressDTO(address);
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) {
        Optional<Address> optionalAddress = addressRepository.findById(addressDTO.getId());
        Address addressToUpdate = optionalAddress.get();
        updateAddressEntityFromDTO(addressToUpdate, addressDTO);
        Address updatedAddress = addressRepository.save(addressToUpdate);
        return AddressMapper.mapToAddressDTO(updatedAddress);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    public  void updateAddressEntityFromDTO(Address addressToUpdate, AddressDTO addressDTO) {
        if (addressDTO.getAddressName() != null) {
            addressToUpdate.setAddressName(addressDTO.getAddressName());
        }
        if (addressDTO.getAddressNumber() != null) {
            addressToUpdate.setAddressNumber(addressDTO.getAddressNumber());
        }
        if (addressDTO.getZipCode() != null) {
            addressToUpdate.setZipCode(addressDTO.getZipCode());
        }
        if (addressDTO.getPlaceName() != null) {
            addressToUpdate.setPlaceName(addressDTO.getPlaceName());
        }
        if (addressDTO.getCountry() != null) {
            addressToUpdate.setCountry(addressDTO.getCountry());
        }
        if (addressDTO.getAdditionalInfo() != null) {
            addressToUpdate.setAdditionalInfo(addressDTO.getAdditionalInfo());
        }
    }
}