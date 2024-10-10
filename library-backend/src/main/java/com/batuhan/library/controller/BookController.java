package com.batuhan.library.controller;

import com.batuhan.library.dto.BookDTO;
import com.batuhan.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
@AllArgsConstructor
public class BookController {
    private BookService bookService;

    @PostMapping("addBook")
    // e. g. URL: http://localhost:8080/api/books/addBook -> POST
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        BookDTO savedBookDTO = bookService.addBook(bookDTO);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }

    @GetMapping("listAll")
    // e. g. URL: http://localhost:8080/api/books/listAll -> GET
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> allBooks = bookService.getAllBooks();
        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @GetMapping("{id}")
    // e. g. URL: http://localhost:8080/api/books/1 -> GET
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long bookId) {
        BookDTO bookDTO = bookService.getBookById(bookId);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @PatchMapping("updateBook/{id}")
    // e. g. URL: http://localhost:8080/api/books/updateBook/1 -> PATCH
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        bookDTO.setId(id);
        BookDTO updateBook = bookService.updateBook(bookDTO);
        return new ResponseEntity<>(updateBook, HttpStatus.OK);
    }

    @DeleteMapping("deleteBook/{id}")
    // e. g. URL: http://localhost:8080/api/books/deleteBook/4 -> DELETE
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>("Book successfully deleted.", HttpStatus.OK);
    }
}