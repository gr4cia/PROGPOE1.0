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

        // ---------- PART 1 - REGISTRATION ----------
        System.out.println("=== Registration ===");
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();
        System.out.print("Enter username (must contain _ and <=5 chars): ");
        String username = input.nextLine();
        System.out.print("Enter password (8 chars, capital, number, special): ");
        String password = input.nextLine();
        System.out.print("Enter cell (+27xxxxxxxxx): ");
        String cell = input.nextLine();

        String regResult = system.registerUser(username, password, cell, firstName, lastName);
        System.out.println(regResult);

        if (!regResult.contains("successfully")) {
            System.out.println("Registration failed. Fix errors and run again.");
            input.close();
            return;
        }

        // ---------- PART 1 - LOGIN ----------
        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String loginUser = input.nextLine();
        System.out.print("Enter password: ");
        String loginPass = input.nextLine();

        System.out.println(system.returnLoginStatus(loginUser, loginPass));

        if (!system.loginUser(loginUser, loginPass)) {
            input.close();
            return;
        }

        // ---------- PART 2 & 3 - QUICKCHAT ----------
        System.out.println("\nWelcome to QuickChat.");
        System.out.print("How many messages do you wish to enter? ");
        int limit = Integer.parseInt(input.nextLine());

        int messagesEntered = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    if (messagesEntered >= limit) {
                        System.out.println("You have reached your message limit of " + limit);
                        break;
                    }

                    System.out.print("Enter recipient (+27... 10-13 chars): ");
                    String recipient = input.nextLine();
                    
                    // Check recipient using your message class
                    message temp = new message(messagesEntered + 1, recipient, "test");
                    System.out.println(temp.checkRecipientCell());
                    if (!temp.isRecipientValid()) {
                        break;
                    }

                    System.out.print("Enter message (max 250 chars): ");
                    String text = input.nextLine();

                    message msg = new message(messagesEntered + 1, recipient, text);
                    System.out.println(msg.checkMessageLength());
                    if (!msg.isMessageLengthValid()) {
                        break;
                    }

                    System.out.println(msg.getGeneratedIDMessage());
                    System.out.println("Message Hash: " + msg.getMessageHash());
                    System.out.println(msg.getFullDetails());

                    System.out.println("\n1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Choose: ");
                    String sendOption = input.nextLine();

                    String result = msg.SentMessage(sendOption);
                    System.out.println(result);

                    if (sendOption.equals("2")) {
                        System.out.print("Enter 0 to delete: ");
                        String del = input.nextLine();
                        if (del.equals("0")) {
                            System.out.println("Message deleted.");
                        }
                        messagesEntered++;
                    } else if (sendOption.equals("1") || sendOption.equals("3")) {
                        // THIS CREATES THE FILE stored_message_XXXXXXXXXX.json
                        msg.recordMessage();
                        messagesEntered++;
                        System.out.println("File created: stored_message_" + msg.getMessageID() + ".json");
                    }
                    break;

                case "2":
                    System.out.println("\n" + message.printMessagesStatic());
                    break;

                case "3":
                    System.out.println("\nTotal messages sent: " + message.getTotalMessagesStatic());
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
        input.close();
    }
}