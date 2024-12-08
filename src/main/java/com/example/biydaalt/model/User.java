package com.example.biydaalt.model;

/**
 * Энэ нь лаборатори менежментийн систем дэх хэрэглэгчийн ангилал юм.
 * Хэрэглэгч нь системд оролцох ажил үүргийнх нь дагуу ажилтан, лабораторийн менежер, дээд ажилтан гэх мэт системд оролцох ялгаатай эрх эдэлдэг.
 */
public class User {
    private String userId;
    private String name;
    private String email;
    private String role;

    // ---- Constructors ----

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(String userId, String name, String email, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // ---- Methods ----

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
        this.name = newName;
        this.email = newEmail;
        this.role = newRole;
        System.out.println("Профайл амжилттай шинэчлэгдлээ!");
    }

    // ---- Getters and Setters ----

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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