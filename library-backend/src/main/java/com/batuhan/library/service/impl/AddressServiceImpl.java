package com.batuhan.library.service.impl;

import com.batuhan.library.dto.AddressDTO;
import com.batuhan.library.entity.Address;
import com.batuhan.library.entity.Book;
import com.batuhan.library.exception.ResourceNotFoundException;
import com.batuhan.library.mapper.AddressMapper;
import com.batuhan.library.repository.AddressRepository;
import com.batuhan.library.service.AddressService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);
    private AddressRepository addressRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        logger.info("Trying to add a address: {}", addressDTO);
        Address address = AddressMapper.mapToAddressEntity(addressDTO);
        logger.info("Address entity after the mapping: {}", address);
        address = addressRepository.save(address);
        logger.info("The address successfully saved in database: {}", address);
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
        // Optional<Address> optionalAddress = addressRepository.findById(id);
        // Address address = optionalAddress.get();
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address", "ID", id));
        return AddressMapper.mapToAddressDTO(address);
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) {
        Optional<Address> optionalAddress = addressRepository.findById(addressDTO.getId());
        Address addressToUpdate = optionalAddress.orElseThrow(
                () -> new ResourceNotFoundException("Address", "ID", addressDTO.getId())
        );
        updateAddressEntityFromDTO(addressToUpdate, addressDTO);
        Address updatedAddress = addressRepository.save(addressToUpdate);
        return AddressMapper.mapToAddressDTO(updatedAddress);
    }

    @Override
    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)){
            throw new ResourceNotFoundException("Address", "ID", id);
        }
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