package com.library.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books") // Переименование таблицы на 'books'
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String title;
    private String author;
    private String isbn;
    private int availableCopies;
}
