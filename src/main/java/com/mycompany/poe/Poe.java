/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package com.mycompany.poe;

/**
 *
 * @author Student
 */

import java.util.Scanner;

public class Poe {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login system = new Login();

        System.out.print("Enter First Name: ");
        String firstName = input.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = input.nextLine();

        System.out.print("Enter Username (must contain _ and <=5 chars): ");
        String username = input.nextLine();
        System.out.println(system.getUsernameCaptureMessage(username));

        System.out.print("Enter Password (8+ chars, Capital, Number, Special): ");
        String password = input.nextLine();
        System.out.println(system.getPasswordCaptureMessage(password));

        System.out.print("Enter Cell Phone (+27... 9 digits): ");
        String cell = input.nextLine();
        System.out.println(system.getCellPhoneCaptureMessage(cell));

        if (system.checkUserName(username) && system.checkPasswordComplexity(password) && system.checkCellPhoneNumber(cell)) {
            system.registerUser(username, password, cell, firstName, lastName);
            System.out.println("\nRegistration successful!");

            System.out.println("\n--- LOGIN ---");
            System.out.print("Enter Username: ");
            String loginUser = input.nextLine();
            System.out.print("Enter Password: ");
            String loginPass = input.nextLine();

            System.out.println(system.returnLoginStatus(loginUser, loginPass));
        } else {
            System.out.println("\nRegistration failed. Please fix the errors above.");
        }
        input.close();
    }
}