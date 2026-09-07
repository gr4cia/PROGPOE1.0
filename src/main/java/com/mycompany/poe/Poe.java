/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poe;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Poe {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login login = new Login();

        System.out.print("Enter First Name: ");
        String firstName =input.nextLine();
        
        System.out.print("Enter Last Name: ");
        String lastName =input.nextLine();
        
        
        System.out.print("Enter Username (must contain _ and <=5 chars): ");
        String username = input.nextLine();

        System.out.print("Enter Password (8+ chars, Capital, Number, Special): ");
        String password =input.nextLine();
        
        System.out.print("Enter SA Cell Number (e.g. +27821234567): ");
        String cell =input.nextLine();

        String registrationResult = login.registerUser(username, password, cell);
        System.out.println("\n" + registrationResult);

        if (login.checkUserName(username) && login.checkPasswordComplexity(password) && login.checkCellPhoneNumber(cell)) {
            System.out.println("\n--- LOGIN ---");
            System.out.print("Enter Username: ");
            String loginUser = input.nextLine();
            System.out.print("Enter Password: ");
            String loginPass = input.nextLine();

            System.out.println(login.returnLoginStatus(loginUser, loginPass, firstName, lastName));
        }
      
    }
}
    

