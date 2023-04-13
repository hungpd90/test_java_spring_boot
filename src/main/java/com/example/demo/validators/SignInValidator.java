package com.example.demo.validators;

import jakarta.annotation.Nullable;

public class SignInValidator {
    private String email;
    private String password;

    public SignInValidator(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SignInValidator() {
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

    public void validate() throws Exception {
        if (this.getEmail() == null) {
            throw new Exception("Email required");
        }
        if (this.getPassword() == null) {
            throw new Exception("Password required");
        }
    }
}
