package com.library.libraryclient.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.http.*;
import java.net.URI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.libraryclient.model.Book;

import java.io.IOException;
import java.util.List;

public class DashboardController {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Book> booksTable;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, Integer> copiesColumn;

    private HttpClient httpClient = HttpClient.newHttpClient();
    private ObjectMapper objectMapper = new ObjectMapper();
    private ObservableList<Book> booksList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));

        loadAllBooks();
    }


    private void loadAllBooks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/books/all"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Book> books = objectMapper.readValue(response.body(), new TypeReference<List<Book>>() {});
                booksList.setAll(books);
                booksTable.setItems(booksList);
            } else {
                showAlert("Error", "Failed to load books.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while loading books.");
        }
    }

    @FXML
    public void handleSearch(ActionEvent event) {
        String title = searchField.getText().trim();
        try {
            String encodedTitle = java.net.URLEncoder.encode(title, "UTF-8");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/books/search?title=" + encodedTitle))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                List<Book> books = objectMapper.readValue(response.body(), new TypeReference<List<Book>>() {});
                booksList.setAll(books);
            } else {
                showAlert("Error", "Failed to search books.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred during search.");
        }
    }

    @FXML
    public void handleAddBook(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add_book.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Add New Book");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Обновляем список после закрытия окна
            loadAllBooks();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open Add Book window.");
        }
    }

    public void handleDeleteBook(ActionEvent event) {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert("Selection Error", "Please select a book to delete.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete the selected book?");
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    String deleteUri = "http://localhost:8080/books/delete/" + selectedBook.getBookId();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(deleteUri))
                            .DELETE()
                            .build();

                    HttpResponse<String> responseDelete = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                    if (responseDelete.statusCode() == 204) { // No Content
                        showInfoAlert("Success", "Book deleted successfully.");
                        loadAllBooks();
                    } else {
                        String errorMessage = "Unable to delete book.";
                        try {
                            errorMessage = objectMapper.readTree(responseDelete.body()).path("message").asText(errorMessage);
                        } catch (Exception e) {
                            // Игнорировать, использовать дефолтное сообщение
                        }
                        showAlert("Error", errorMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Error", "An error occurred while deleting the book.");
                }
            }
        });
    }

    @FXML
    public void refreshBooks(ActionEvent event) {
        loadAllBooks();
    }


    public void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Stage stage = (Stage) searchField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to logout.");
        }
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
