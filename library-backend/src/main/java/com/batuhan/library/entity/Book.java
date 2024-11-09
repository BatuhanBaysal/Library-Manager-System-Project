package com.batuhan.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "books")
@Data // @AllArgsConstructor, @NoArgsConstructor, @Getter, @Setter, @ToString, and @EqualsAndHashCode.
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String title;

    @Column(nullable = false)
    @NotNull
    private String author;

    @Column(nullable = false)
    @NotNull
    private String isbn;

    @Column(nullable = false)
    @NotNull
    private String publisher;

    @Column(nullable = false)
    @NotNull
    private Integer yearOfPublication;

    @Column(nullable = false)
    @NotNull
    private String placeOfPublication;

    @Column(nullable = false)
    @NotNull
    private Integer noOfAvailableCopies;

    @Column(nullable = false)
    @NotNull
    private String barcodeNumber;
}