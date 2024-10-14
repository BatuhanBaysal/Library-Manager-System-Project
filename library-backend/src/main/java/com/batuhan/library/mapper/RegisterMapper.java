package com.batuhan.library.mapper;

import com.batuhan.library.dto.RegisterDTO;
import com.batuhan.library.entity.Book;
import com.batuhan.library.entity.CheckoutRegister;
import com.batuhan.library.entity.Member;
import com.batuhan.library.repository.BookRepository;
import com.batuhan.library.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Component
public class RegisterMapper {
    private MemberRepository memberRepository;
    private BookRepository bookRepository;

    public RegisterDTO mapToRegisterDTO(CheckoutRegister checkoutRegister) {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setId(checkoutRegister.getId());
        registerDTO.setMemberId(checkoutRegister.getMember().getId());
        registerDTO.setBookId(checkoutRegister.getBook().getId());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        registerDTO.setCheckoutDate(checkoutRegister.getCheckoutDate().format(formatter));
        registerDTO.setDueDate(checkoutRegister.getDueDate().format(formatter));

        if (checkoutRegister.getReturnDate() != null) {
            registerDTO.setReturnDate(checkoutRegister.getReturnDate().format(formatter));
        }

        registerDTO.setOverdueFine(checkoutRegister.getOverdueFine());
        return  registerDTO;
    }

    public CheckoutRegister mapToCheckoutRegistryEntity(RegisterDTO registerDTO) {
        CheckoutRegister checkoutRegister = new CheckoutRegister();
        checkoutRegister.setId(registerDTO.getId());

        Member member = memberRepository.findById(registerDTO.getMemberId()).get();
        checkoutRegister.setMember(member);
        Book book = bookRepository.findById(registerDTO.getBookId()).get();
        checkoutRegister.setBook(book);

        checkoutRegister.setCheckoutDate(LocalDate.parse(registerDTO.getCheckoutDate()));
        if (registerDTO.getDueDate() != null) {
            checkoutRegister.setDueDate(LocalDate.parse(registerDTO.getDueDate()));
        }
        if (registerDTO.getReturnDate() != null) {
            checkoutRegister.setReturnDate(LocalDate.parse(registerDTO.getReturnDate()));
        }

        checkoutRegister.setOverdueFine(registerDTO.getOverdueFine());
        return checkoutRegister;
    }
}