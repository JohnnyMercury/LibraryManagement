// Directory: /Users/nizatr007/Documents/ProjectLib/library-server/src/main/java/com/library/controller
// Filename: TransactionController.java

package com.library.controller;

import com.library.model.Transaction;
import com.library.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для обработки транзакций (взятие и возврат книг)
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Взять книгу
     *
     * @param userId ID пользователя
     * @param bookId ID книги
     * @return Созданная транзакция
     */
    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        try {
            Transaction transaction = transactionService.borrowBook(userId, bookId);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Вернуть книгу
     *
     * @param userId ID пользователя
     * @param bookId ID книги
     * @return Созданная транзакция
     */
    @PostMapping("/return")
    public ResponseEntity<?> returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        try {
            Transaction transaction = transactionService.returnBook(userId, bookId);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Получить все транзакции пользователя
     *
     * @param userId ID пользователя
     * @return Список транзакций
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserTransactions(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionsByUser(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
