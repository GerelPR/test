package com.example.biydaalt.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.example.biydaalt.model.User;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    // Add a new user to the database with hashed password
    public void createUser(User user) throws SQLException {
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
        String sql = "SELECT * FROM users WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"), // this will store the hashed password
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
}
