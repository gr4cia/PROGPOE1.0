/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

/**
 *
 * @author Student
 */
class Login {
    private String username;
    private String password;
    private String cellNumber;
    private String firstName;
    private String lastName;

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) return false;
        
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        String specialChars = "!@#$%^&*()-+";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasCapital = true;
            if (Character.isDigit(c)) hasNumber = true;
            if (specialChars.contains(String.valueOf(c))) hasSpecial = true;
        }
        return hasCapital && hasNumber && hasSpecial;
    }

    public boolean checkCellPhoneNumber(String cell) {
        return cell.startsWith("+27") && cell.length() == 12 && cell.substring(3).matches("\\d{9}");
    }

    public String registerUser(String username, String password, String cell) {
        String message = "";
        if (!checkUserName(username)) {
            message += "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.\n";
        } else {
            message += "Username successfully captured.\n";
        }

        if (!checkPasswordComplexity(password)) {
            message += "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number and a special character.\n";
        } else {
            message += "Password successfully captured.\n";
        }

        if (!checkCellPhoneNumber(cell)) {
            message += "Cell phone number incorrectly formatted or does not contain international code.";
        } else {
            message += "Cell phone number successfully added.";
            this.username = username;
            this.password = password;
            this.cellNumber = cell;
        }
        return message;
    }

    public boolean loginUser(String username, String password) {
        return username.equals(this.username) && password.equals(this.password);
    }

    public String returnLoginStatus(String username, String password, String firstName, String lastName) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}   

