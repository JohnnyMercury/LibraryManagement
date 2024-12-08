// Directory: /Users/nizatr007/Documents/ProjectLib/library-server/src/main/java/com/library/service
// Filename: BookService.java

package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> searchBooks(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public void deleteBook(Long bookId) throws Exception {
        if (!bookRepository.existsById(bookId)) {
            throw new Exception("Книга с ID " + bookId + " не найдена");
        }
        bookRepository.deleteById(bookId);
    }

    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }
}
