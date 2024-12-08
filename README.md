# LibraryManagement
LibraryManagement Erasmus+
# Library Management System

A comprehensive Library Management System consisting of a Spring Boot server and a JavaFX client. This application allows users to register, authenticate, manage books, and perform transactions such as borrowing and returning books. The system employs role-based access control to differentiate between regular users and administrators.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
  - [Server Setup](#server-setup)
  - [Client Setup](#client-setup)
- [Running the Application](#running-the-application)
  - [Starting the Server](#starting-the-server)
  - [Launching the Client](#launching-the-client)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Database Console](#database-console)
- [Security](#security)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features

- **User Management**
  - User registration with role assignment (`User` or `Admin`)
  - User authentication using HTTP Basic Authentication
- **Book Management**
  - Add new books
  - View all books
  - Search books by title
  - Delete books
- **Transaction Management**
  - Borrow books
  - Return books
  - View user-specific transaction history
- **Role-Based Access Control**
  - Regular users can borrow and return books
- **Secure Password Storage**
  - Passwords are hashed using BCrypt
- **Interactive Client Interface**
  - JavaFX-based GUI for seamless user interaction
- **In-Memory H2 Database**
  - Quick setup and testing with H2 console support

## Technologies Used

### Server

- **Language:** Java 21
- **Framework:** Spring Boot 3.4.0
- **Data Persistence:** Spring Data JPA
- **Security:** Spring Security
- **Database:** H2 (In-Memory)
- **Build Tool:** Maven
- **Other Libraries:** Lombok

### Client

- **Language:** Java
- **Framework:** JavaFX 21
- **HTTP Client:** Java 11 `HttpClient`
- **JSON Processing:** Jackson Databind
- **Build Tool:** Maven

## Prerequisites

- **Java Development Kit (JDK) 21** or higher
- **Maven 3.8.1** or higher
- **Git** (optional, for cloning the repository)
- **IDE** such as IntelliJ IDEA, Eclipse, or VS Code (optional)

## Installation

### Server Setup

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/library-management-system.git
   ```

2. **Navigate to the Server Directory**

   ```bash
   cd library-management-system/library-server
   ```

3. **Build the Server Application**

   ```bash
   mvn clean install
   ```

4. **Run the Server**

   ```bash
   mvn spring-boot:run
   ```

   The server will start on `http://localhost:8080`.

### Client Setup

1. **Navigate to the Client Directory**

   ```bash
   cd library-management-system/library-client
   ```

2. **Build the Client Application**

   ```bash
   mvn clean install
   ```

3. **Run the Client**

   ```bash
   mvn javafx:run
   ```

   The JavaFX client will launch, presenting the login screen.

## Running the Application

### Starting the Server

Ensure that the server is running before launching the client. The server initializes an in-memory H2 database and sets up all necessary RESTful endpoints.

- **Access H2 Console (Optional):**

  Navigate to `http://localhost:8080/h2-console` in your browser to access the H2 database console.

  - **JDBC URL:** `jdbc:h2:mem:librarydb`
  - **Username:** `sa`
  - **Password:** *(leave blank)*

### Launching the Client

After starting the server:

1. **Run the Client Application** using the Maven JavaFX plugin as shown above.
2. **Login or Register:**
   - **Register:** Create a new user account.
   - **Login:** Use your credentials to access the dashboard.

## Usage

1. **Registration:**
   - Click on the "Register" button.
   - Fill in the username, email, and password fields.
   - Submit to create a new account.

2. **Login:**
   - Enter your registered username and password.
   - Click "Login" to access the dashboard.

3. **Dashboard Operations:**
   - **View All Books:** Displays a list of all available books.
   - **Search Books:** Enter a title to search for specific books.
   - **Add Book:** (Admin only) Add new books to the library.
   - **Delete Book:** (Admin only) Remove books from the library.
   - **Refresh:** Reload the book list.
   - **Logout:** Exit the current session.

4. **Transactions:**
   - **Borrow Book:** Select a book and initiate a borrow transaction.
   - **Return Book:** Select a borrowed book to return.

## API Documentation

### User Endpoints

- **Register User**

  ```http
  POST /users/register
  ```

  **Request Body:**

  ```json
  {
    "username": "john_doe",
    "email": "john@example.com",
    "password": "securepassword"
  }
  ```

- **Login User**

  ```http
  POST /users/login
  ```

  **Request Body:**

  ```json
  {
    "username": "john_doe",
    "password": "securepassword"
  }
  ```

### Book Endpoints

- **Add Book** 

  ```http
  POST /books/add
  ```

  **Request Body:**

  ```json
  {
    "title": "Effective Java",
    "author": "Joshua Bloch",
    "isbn": "978-0134685991",
    "availableCopies": 5
  }
  ```

- **Get All Books**

  ```http
  GET /books/all
  ```

- **Search Books by Title**

  ```http
  GET /books/search?title=Java
  ```

- **Delete Book** 

  ```http
  DELETE /books/delete/{bookId}
  ```

### Transaction Endpoints

- **Borrow Book**

  ```http
  POST /transactions/borrow?userId=1&bookId=2
  ```

- **Return Book**

  ```http
  POST /transactions/return?userId=1&bookId=2
  ```

- **Get User Transactions**

  ```http
  GET /transactions/user/{userId}
  ```

## Database Console

The application uses an in-memory H2 database for quick setup and testing. To access the H2 console:

1. **Navigate to the H2 Console:**

   ```
   http://localhost:8080/h2-console
   ```

2. **Configure the Connection:**

   - **JDBC URL:** `jdbc:h2:mem:librarydb`
   - **Username:** `sa`
   - **Password:** *(leave blank)*

3. **Connect to View and Manage Data:**

   Use SQL queries to inspect tables such as `books`, `users`, `roles`, and `transactions`.

## Security

- **Authentication:** Implemented using HTTP Basic Authentication.
- **Password Encryption:** Passwords are securely stored using BCrypt hashing.
- **Role-Based Access Control:** Differentiates between `User` and `Admin` roles to restrict access to certain endpoints and functionalities.
- **CORS and CSRF:** CSRF protection is disabled for simplicity. In production, ensure to enable and configure it appropriately.

**Note:** For production environments, consider enhancing security measures, enabling HTTPS, and using more robust authentication mechanisms like JWT.

## Project Structure

### Server (`library-server`)

- **`src/main/java/com/library/`**
  - **`LibraryServerApplication.java`**: Main application class.
  - **`model/`**: JPA entities (`Book`, `User`, `Role`, `Transaction`).
  - **`repository/`**: Spring Data JPA repositories.
  - **`service/`**: Business logic services (`BookService`, `UserService`, `TransactionService`).
  - **`controller/`**: REST controllers (`BookController`, `UserController`, `TransactionController`).
  - **`config/`**: Configuration classes (`SecurityConfig`, `DataInitializer`).
  - **`exception/`**: Global exception handler (`GlobalExceptionHandler`).

- **`src/main/resources/`**
  - **`application.properties`**: Configuration properties for Spring Boot.

### Client (`library-client`)

- **`src/main/java/com/library/libraryclient/`**
  - **`MainApplication.java`**: JavaFX application entry point.
  - **`controller/`**: JavaFX controllers (`LoginController`, `RegisterController`, `DashboardController`, `AddBookController`).
  - **`model/`**: Client-side models (`User`, `Book`).
  - **`util/`**: Utility classes (`AuthContext`).

- **`src/main/resources/`**
  - **`*.fxml`**: JavaFX UI layout files (`login.fxml`, `register.fxml`, `dashboard.fxml`, `add_book.fxml`).

## Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the Repository**
2. **Create a Feature Branch**

   ```bash
   git checkout -b feature/YourFeature
   ```

3. **Commit Your Changes**

   ```bash
   git commit -m "Add some feature"
   ```

4. **Push to the Branch**

   ```bash
   git push origin feature/YourFeature
   ```
