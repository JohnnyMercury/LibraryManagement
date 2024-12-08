package com.library.libraryclient.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.http.*;
import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.libraryclient.model.Book;

public class AddBookController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField copiesField;

    private HttpClient httpClient = HttpClient.newHttpClient();
    private ObjectMapper objectMapper = new ObjectMapper();

    public void handleAdd(ActionEvent event) {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String isbn = isbnField.getText().trim();
        String copiesStr = copiesField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || copiesStr.isEmpty()) {
            showAlert("Validation Error", "All fields are required.");
            return;
        }

        int availableCopies;
        try {
            availableCopies = Integer.parseInt(copiesStr);
            if (availableCopies < 0) {
                showAlert("Validation Error", "Available copies cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Available copies must be a valid number.");
            return;
        }

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setIsbn(isbn);
        newBook.setAvailableCopies(availableCopies);

        try {
            String requestBody = objectMapper.writeValueAsString(newBook);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/books/add"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                showInfoAlert("Success", "Book added successfully.");
                closeWindow();
            } else {
                String errorMessage = "Unable to add book.";
                String responseBody = response.body();
                try {
                    errorMessage = objectMapper.readTree(responseBody).path("message").asText(errorMessage);
                } catch (Exception e) {
                    // Использовать дефолтное сообщение, если парсинг не удался
                }
                showAlert("Error", errorMessage + "\nResponse: " + responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while adding the book.");
        }
    }


    public void handleCancel(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); // Убирает заголовок
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // Убирает заголовок
        alert.setContentText(content);
        alert.showAndWait();
    }
}
