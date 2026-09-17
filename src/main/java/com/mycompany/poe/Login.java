/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package com.mycompany.poe;

/**
 *
 * @author Student
 */

public class Login {
    private String username;
    private String password;
    private String cell;
    private String firstName;
    private String lastName;

    public Login() {}

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        boolean hasUpper = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[^a-zA-Z0-9].*");
        return password.length() >= 8 && hasUpper && hasNumber && hasSpecial;
    }

    public boolean checkCellPhoneNumber(String cell) {
        return cell.matches("^\\+27\\d{9}$");
    }

    public String getUsernameCaptureMessage(String username) {
        if (checkUserName(username)) {
            return "Username successfully captured.";
        } else {
            return "Username is not correctly formatted, please ensure that your username contains an underscore"
                    + " and is no more than 5 characters in length.";
        }
    }

    public String getPasswordCaptureMessage(String password) {
        if (checkPasswordComplexity(password)) {
            return "Password successfully captured.";
        } else {
            return "Password is not correctly formatted, please ensure that the"
                    + " password contains at least 8 characters, a capital letter, a number and a special character.";
        }
    }

    public String getCellPhoneCaptureMessage(String cell) {
        if (checkCellPhoneNumber(cell)) {
            return "Cell number successfully captured.";
        } else {
            return "Cell number is incorrectly formatted or does not contain an international code, please correct the number.";
        }
    }

    
    public void registerUser(String username, String password, String cell, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cell = cell;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean loginUser(String username, String password) {
        if (this.username == null || this.password == null) return false;
        return this.username.equals(username) && this.password.equals(password);
    }

    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}