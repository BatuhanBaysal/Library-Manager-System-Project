package com.batuhan.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table
@Data
public class CheckoutRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @NotNull
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull
    private Book book;

    @Column(nullable = false)
    @NotNull
    private LocalDate checkoutDate;

    @Column(nullable = false)
    @NotNull
    private LocalDate dueDate;

    @Column
    private LocalDate returnDate;

    @Column
    private Double overdueFine;
}