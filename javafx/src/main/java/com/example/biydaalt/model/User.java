package com.example.biydaalt.model;

import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

public class User {
    private String userId;
    private String name;
    private String email;
    private String password; // Only store hashed passwords
    private String role;

    // Default constructor
    public User() {
        this.userId = generateUniqueUserId();
    }

    // Constructor for creating a user with all fields, including userId
    public User(String userId, String name, String email, String password, String role) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (!isValidRole(role)) {
            throw new IllegalArgumentException("Invalid role. Must be 'User' or 'Admin'.");
        }

        this.userId = userId;  // Set the userId directly
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    private String generateUniqueUserId() {
        return UUID.randomUUID().toString();
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private boolean isValidRole(String role) {
        return role != null && (role.equalsIgnoreCase("User") || role.equalsIgnoreCase("Admin"));
    }

    public boolean verifyPassword(String inputPassword) {
        return BCrypt.checkpw(inputPassword, this.password);
    }

    public void updateProfile(String newName, String newEmail, String newRole) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (newEmail == null || !isValidEmail(newEmail)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!isValidRole(newRole)) {
            throw new IllegalArgumentException("Invalid role.");
        }

        this.name = newName;
        this.email = newEmail;
        this.role = newRole;
        System.out.println("Profile updated successfully!");
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
