// Directory: /Users/nizatr007/Documents/ProjectLib/library-server/src/main/java/com/library/service
// Filename: TransactionService.java

package com.library.service;

import com.library.model.Book;
import com.library.model.Transaction;
import com.library.model.User;
import com.library.repository.BookRepository;
import com.library.repository.TransactionRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public Transaction borrowBook(Long userId, Long bookId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Пользователь не найден"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new Exception("Книга не найдена"));

        if (book.getAvailableCopies() <= 0) {
            throw new Exception("Нет доступных копий книги");
        }

        // Уменьшаем количество доступных копий
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        // Создаем транзакцию
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setAction("BORROW");
        transaction.setDate(LocalDate.now());

        return transactionRepository.save(transaction);
    }

    public Transaction returnBook(Long userId, Long bookId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Пользователь не найден"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new Exception("Книга не найдена"));

        // Увеличиваем количество доступных копий
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        // Создаем транзакцию
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setAction("RETURN");
        transaction.setDate(LocalDate.now());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByUser(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Пользователь не найден"));

        return transactionRepository.findByUser(user);
    }
}
