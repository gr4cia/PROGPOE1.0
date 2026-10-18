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

        // --- Registration Part ---
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();
        System.out.print("Enter cellphone (+27...): ");
        String cell = input.nextLine();

        String regResult;
        regResult = system.registerUser(username, password, cell, firstName, lastName);
        System.out.println(regResult);

        if (regResult.contains("successfully")) {
            // --- Login Part ---
            System.out.print("\nEnter login username: ");
            String loginUser = input.nextLine();
            System.out.print("Enter login password: ");
            String loginPass = input.nextLine();

            System.out.println(system.returnLoginStatus(loginUser, loginPass));

            if (!system.loginUser(loginUser, loginPass)) {
                input.close();
                return;
            }

            System.out.println("\nWelcome to QuickChat.");

            System.out.print("How many messages do you wish to enter? ");
            int limit = Integer.parseInt(input.nextLine());

            int entered = 0;
            boolean running = true;

            while (running) {
                System.out.println("\n--- QuickChat Menu ---");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.print("Choose: ");
                String menuChoice = input.nextLine();

                if (menuChoice.equals("1")) {
                    if (entered >= limit) {
                        System.out.println("You have reached your message limit.");
                        continue;
                    }

                    System.out.print("Enter Recipient: ");
                    String recipient = input.nextLine();
                    
                    message tempCheck = new message(entered + 1, recipient, "test");
                    String recResult = tempCheck.checkRecipientCell();
                    System.out.println(recResult);
                    if (!tempCheck.isRecipientValid()) {
                        continue;
                    }

                    System.out.print("Enter Message (max 250 chars): ");
                    String text = input.nextLine();
                    message msg = new message(entered + 1, recipient, text);
                    
                    String lenResult = msg.checkMessageLength();
                    System.out.println(lenResult);
                    if (!msg.isMessageLengthValid()) {
                        continue;
                    }

                    System.out.println(msg.getGeneratedIDMessage());
                    System.out.println("Message Hash: " + msg.getMessageHash());

                    System.out.println("\n1) Send Message\n2) Disregard Message\n3) Store Message to send later");
                    System.out.print("Choose: ");
                    String sendChoice = input.nextLine();

                    String sentResult = msg.SentMessage(sendChoice);
                    System.out.println(sentResult);

                    if (sendChoice.equals("2")) {
                        System.out.print("Enter 0 to confirm delete: ");
                        String confirm = input.nextLine();
                        if (confirm.equals("0")) {
                            System.out.println("Message deleted.");
                        }
                    } else {
                        msg.recordMessage();
                        System.out.println("\n" + msg.getFullDetails());
                    }
                    
                    entered = entered + 1;

                } else if (menuChoice.equals("2")) {
                    System.out.println("Coming soon.");
                    
                } else if (menuChoice.equals("3")) {
                    System.out.println("Total messages sent: " + message.getTotalMessagesStatic());
                    System.out.println("Goodbye.");
                    running = false;
                    
                } else {
                    System.out.println("Invalid choice.");
                }
            }

        } else {
            System.out.println("\nRegistration failed. Please fix the errors.");
        }

        input.close();
    }
}