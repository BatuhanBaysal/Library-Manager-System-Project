package com.batuhan.library.service.impl;

import com.batuhan.library.dto.RegisterDTO;
import com.batuhan.library.entity.CheckoutRegister;
import com.batuhan.library.mapper.RegisterMapper;
import com.batuhan.library.repository.CheckoutRegisterRepository;
import com.batuhan.library.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    @Value("${library.loanPeriodInDays}")
    private int loanPeriodInDays;

    @Value("${library.overdueFineRate}")
    private double overdueFineRate;

    private final RegisterMapper registerMapper;
    private final CheckoutRegisterRepository checkoutRegisterRepository;

    @Override
    public RegisterDTO createRegister(RegisterDTO registerDTO) {
        CheckoutRegister checkoutRegister = registerMapper.mapToCheckoutRegistryEntity(registerDTO);

        //calculate due date
        LocalDate dueDate = calculateDueDate(checkoutRegister.getCheckoutDate());
        checkoutRegister.setDueDate(dueDate);

        checkoutRegister = checkoutRegisterRepository.save(checkoutRegister);
        return registerMapper.mapToRegisterDTO(checkoutRegister);
    }

    @Override
    public List<RegisterDTO> getAllRegisters() {
        List<CheckoutRegister> checkoutRegisters = checkoutRegisterRepository.findAll();
        return checkoutRegisters.stream()
                .map(registerMapper::mapToRegisterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RegisterDTO getRegisterById(Long id) {
        Optional<CheckoutRegister> checkoutRegisterOptional = checkoutRegisterRepository.findById(id);
        CheckoutRegister checkoutRegister = checkoutRegisterOptional.get();
        return registerMapper.mapToRegisterDTO(checkoutRegister);
    }

    @Override
    public RegisterDTO updateRegister(RegisterDTO registerDTO) {
        Optional<CheckoutRegister> checkoutRegisterOptional = checkoutRegisterRepository.findById(registerDTO.getId());
        CheckoutRegister checkoutRegisterToUpdate = checkoutRegisterOptional.get();
        updateCheckoutRegisterFromDTO(checkoutRegisterToUpdate, registerDTO);
        calculateOverDueFine(checkoutRegisterToUpdate);
        CheckoutRegister updatedCheckoutRegister = checkoutRegisterRepository.save(checkoutRegisterToUpdate);
        return registerMapper.mapToRegisterDTO(updatedCheckoutRegister);
    }

    @Override
    public void deleteRegister(Long id) {
        checkoutRegisterRepository.deleteById(id);
    }

    @Override
    public List<RegisterDTO> getRegisterByMemberId(Long memberId) {
        List<CheckoutRegister> checkoutRegisters = checkoutRegisterRepository.findByMemberId(memberId);
        return checkoutRegisters.stream()
                .map(registerMapper::mapToRegisterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RegisterDTO> getRegisterByBookId(Long bookId) {
        List<CheckoutRegister> checkoutRegisters = checkoutRegisterRepository.findByBookId(bookId);
        return checkoutRegisters.stream()
                .map(registerMapper::mapToRegisterDTO)
                .collect(Collectors.toList());
    }

    private void calculateOverDueFine(CheckoutRegister checkoutRegister) {
        if (checkoutRegister.getReturnDate() != null &&
                checkoutRegister.getReturnDate().isAfter(checkoutRegister.getDueDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(checkoutRegister.getDueDate(), checkoutRegister.getReturnDate());
            double overdueFine = daysOverdue * overdueFineRate;
            checkoutRegister.setOverdueFine(overdueFine);
        }
    }

    private void updateCheckoutRegisterFromDTO(CheckoutRegister checkoutRegisterToUpdate, RegisterDTO registerDTO) {
        if (registerDTO.getDueDate() != null) {
            checkoutRegisterToUpdate.setDueDate(LocalDate.parse(registerDTO.getDueDate()));
        }
        if (registerDTO.getReturnDate() != null) {
            checkoutRegisterToUpdate.setReturnDate(LocalDate.parse(registerDTO.getReturnDate()));
        }
    }

    private LocalDate calculateDueDate(LocalDate checkoutDate) {
        return checkoutDate.plusDays(loanPeriodInDays);
    }
}