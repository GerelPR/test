package com.example.biydaalt.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Энэ нь лаборатори менежментийн систем дэх хэрэглэгчийн ангилал юм.
 * Хэрэглэгч нь системд оролцох ажил үүргийнх нь дагуу ажилтан, лабораторийн менежер, дээд ажилтан гэх мэт системд оролцох ялгаатай эрх эдэлдэг.
 */
public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String role;

    // Static set to keep track of unique names
    private static final Set<String> uniqueNames = new HashSet<>();

    // ---- Constructors ----

    // Default constructor
    public User() {
        this.userId = generateUniqueUserId();
    }

    // Constructor for creating a new user
    public User(String name, String email, String password, String role) {
        // Check if the name is unique
        // Validate inputs
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        
        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (!isUniqueName(name)) {
            throw new IllegalArgumentException("Name must be unique. '" + name + "' is already taken.");
        }
        // Generate a unique userId
        this.userId = generateUniqueUserId();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;

        // Register the name as used to ensure no duplication in the future
        uniqueNames.add(name);
    }

    // ---- Methods ----

    /**
     * Generate a unique user ID using UUID.
     */
    private String generateUniqueUserId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Check if the name is unique.
     */
    private boolean isUniqueName(String name) {
        return !uniqueNames.contains(name);
    }

    /**
     * Хэрэглэгчийн нэвтрэх үйлдэл
     */
    public void loginId() {
        System.out.println(name + " (" + userId + ") амжилттай нэвтэрлээ.");
    }

    /**
     * Хэрэглэгчийн гарах үйлдэл
     */
    public void logout() {
        System.out.println(name + " системээс гарлаа.");
    }

    /**
     * Хэрэглэгчийн профайлыг шинэчлэх үйлдэл
     */
    public void updateProfile(String newName, String newEmail, String newRole) {
        if (!newName.equals(this.name) && !isUniqueName(newName)) {
            throw new IllegalArgumentException("Name must be unique. '" + newName + "' is already taken.");
        }

        // Update unique names set
        uniqueNames.remove(this.name);
        uniqueNames.add(newName);

        this.name = newName;
        this.email = newEmail;
        this.role = newRole;
        System.out.println("Профайл амжилттай шинэчлэгдлээ!");
    }

    // Helper method to validate email
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // ---- Getters and Setters ----

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!isUniqueName(name)) {
            throw new IllegalArgumentException("Name must be unique. '" + name + "' is already taken.");
        }

        // Update unique names set
        uniqueNames.remove(this.name);
        uniqueNames.add(name);

        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // ---- toString Method ----

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
