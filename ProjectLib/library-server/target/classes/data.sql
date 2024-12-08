-- Вставка ролей
INSERT INTO roles (role_name) VALUES ('User');
INSERT INTO roles (role_name) VALUES ('Admin');

-- Вставка начальных книг
INSERT INTO books (title, author, isbn, available_copies) VALUES
                                                              ('1984', 'George Orwell', '9780451524935', 5),
                                                              ('To Kill a Mockingbird', 'Harper Lee', '9780060935467', 3),
                                                              ('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 4),
                                                              ('Brave New World', 'Aldous Huxley', '9780060850524', 2),
                                                              ('Moby Dick', 'Herman Melville', '9781503280786', 1);

-- Вставка административного пользователя
-- Пароль: 'adminpass' (зашифрованный с использованием BCrypt)
INSERT INTO users (username, password, email, role_id)
SELECT 'admin', '$2a$10$Dow1Dv7KxCixO7cd6JoOJeE0Kw1rKyqicnhIV6uXkVqRvP96JQFfO', 'admin@example.com', role_id
FROM roles
WHERE role_name = 'Admin';
