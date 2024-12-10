package com.example.biydaalt.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mindrot.jbcrypt.BCrypt;

import com.example.biydaalt.model.User;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    // Add a new user to the database with hashed password
    public void createUser(User user) throws SQLException {
        // Check if the user already exists by email or name
        if (isUserExists(user.getEmail(), user.getName())) {
            throw new IllegalArgumentException("A user with this email or name already exists.");
        }

        String sql = "INSERT INTO users (user_id, name, email, password, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUserId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            // Hash the password before storing
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            stmt.setString(4, hashedPassword);
            stmt.setString(5, user.getRole());
            stmt.executeUpdate();
        }
    }

    // Retrieve a user from the database by name
    public User getUserByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        String sql = "SELECT * FROM users WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Create User object using all fields from the database
                    return new User(
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"), // Store hashed password
                        rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            // Log the error instead of printing stack trace
            Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE, "Database error", e);
        }

        return null;
    }

    // Retrieve a user from the database by userId
    public User getUserById(String userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getString("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"), // Store hashed password
                    rs.getString("role")
                );
            }
        }
        return null;
    }

    // Update a user's profile in the database
    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, role = ? WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getUserId());
            stmt.executeUpdate();
            System.out.println("User updated successfully in the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a user from the database
    public void deleteUser(String userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.executeUpdate();
            System.out.println("User deleted successfully from the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if a user exists by email or name
    public boolean isUserExists(String email, String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ? OR name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // If count > 0, user exists
                }
            }
        }
        return false;
    }
}
