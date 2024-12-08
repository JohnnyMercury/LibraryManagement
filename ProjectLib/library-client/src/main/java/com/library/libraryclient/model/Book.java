package com.library.libraryclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private Long bookId;
    private String title;
    private String author;
    private String isbn;
    private int availableCopies;

    // Конструкторы
    public Book() {
    }

    public Book(Long bookId, String title, String author, String isbn, int availableCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.availableCopies = availableCopies;
    }

    // Геттеры и Сеттеры
    public Long getBookId() {
        return bookId;
    }

    @JsonProperty("bookId")
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    @JsonProperty("isbn")
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    @JsonProperty("availableCopies")
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
}
