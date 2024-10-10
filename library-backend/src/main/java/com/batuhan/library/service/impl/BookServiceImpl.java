package com.batuhan.library.service.impl;

import com.batuhan.library.dto.BookDTO;
import com.batuhan.library.entity.Book;
import com.batuhan.library.mapper.BookMapper;
import com.batuhan.library.repository.BookRepository;
import com.batuhan.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = BookMapper.mapToBookEntity(bookDTO);
        book = bookRepository.save(book);
        return BookMapper.mapToBookDTO(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(BookMapper::mapToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.get();
        return BookMapper.mapToBookDTO(book);
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        Optional<Book> bookOptional = bookRepository.findById(bookDTO.getId());
        Book bookToUpdate = bookOptional.get();
        updateBookEntityFromDTO(bookToUpdate, bookDTO);
        Book savedBook = bookRepository.save(bookToUpdate);
        return BookMapper.mapToBookDTO(savedBook);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    private void updateBookEntityFromDTO(Book book, BookDTO bookDTO) {
        if (bookDTO.getTitle() != null) {
            book.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getAuthor() != null) {
            book.setAuthor(bookDTO.getAuthor());
        }
        if (bookDTO.getIsbn() != null) {
            book.setIsbn(bookDTO.getIsbn());
        }
        if (bookDTO.getPublisher() != null) {
            book.setPublisher(bookDTO.getPublisher());
        }
        if (bookDTO.getYearOfPublication() != null) {
            book.setYearOfPublication(bookDTO.getYearOfPublication());
        }
        if (bookDTO.getPlaceOfPublication() != null) {
            book.setPlaceOfPublication(bookDTO.getPlaceOfPublication());
        }
        if (bookDTO.getNoOfAvailableCopies() != null) {
            book.setNoOfAvailableCopies(bookDTO.getNoOfAvailableCopies());
        }
        if (bookDTO.getBarcodeNumber() != null) {
            book.setBarcodeNumber(bookDTO.getBarcodeNumber());
        }
    }
}