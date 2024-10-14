package com.batuhan.library.service.impl;

import com.batuhan.library.controller.BookController;
import com.batuhan.library.dto.BookDTO;
import com.batuhan.library.entity.Book;
import com.batuhan.library.exception.ResourceNotFoundException;
import com.batuhan.library.mapper.BookMapper;
import com.batuhan.library.repository.BookRepository;
import com.batuhan.library.service.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    private BookRepository bookRepository;
    private EntityManager entityManager;

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        logger.info("Trying to add a book: {}", bookDTO);
        Book book = BookMapper.mapToBookEntity(bookDTO);
        logger.info("Book entity after the mapping: {}", book);
        book = bookRepository.save(book);
        logger.info("The book successfully saved in database: {}", book);
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
        // Optional<Book> optionalBook = bookRepository.findById(bookId);
        // Book book = optionalBook.get();
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book", "ID", bookId));
        return BookMapper.mapToBookDTO(book);
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        Optional<Book> bookOptional = bookRepository.findById(bookDTO.getId());
        Book bookToUpdate = bookOptional.orElseThrow(
                () -> new ResourceNotFoundException("Book", "ID", bookDTO.getId())
        );
        updateBookEntityFromDTO(bookToUpdate, bookDTO);
        Book savedBook = bookRepository.save(bookToUpdate);
        return BookMapper.mapToBookDTO(savedBook);
    }

    @Override
    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)){
            throw new ResourceNotFoundException("Book", "ID", bookId);
        }
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<BookDTO> findBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return books.stream()
                .map(BookMapper::mapToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findBooksByTitleAndAuthor(String title, String author) {
        List<Book> books = bookRepository.findByTitleAndAuthor(title, author);
        return books.stream()
                .map(BookMapper::mapToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findBooksByCriteria(String title, String author, String isbn, String barcodeNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book =cq.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();
        if (title != null && !title.isEmpty()) {
            predicates.add(cb.like(cb.lower(book.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (author != null && !author.isEmpty()) {
            predicates.add(cb.like(cb.lower(book.get("author")), "%" + author.toLowerCase() + "%"));
        }
        if (isbn != null && !isbn.isEmpty()) {
            predicates.add(cb.like(cb.lower(book.get("isbn")), "%" + isbn.toLowerCase() + "%"));
        }
        if (barcodeNumber != null && !barcodeNumber.isEmpty()) {
            predicates.add(cb.like(cb.lower(book.get("barcodeNumber")), "%" + barcodeNumber.toLowerCase() + "%"));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        List<Book> result = entityManager.createQuery(cq).getResultList();
        return result.stream()
                .map(BookMapper::mapToBookDTO)
                .collect(Collectors.toList());
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