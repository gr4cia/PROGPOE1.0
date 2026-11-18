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
        Login login = new Login();

        System.out.print("Enter First Name: ");
        String first = input.nextLine();
        System.out.print("Enter Last Name: ");
        String last = input.nextLine();
        System.out.print("Enter Username (must contain _ and <=5 chars): ");
        String user = input.nextLine();
        System.out.print("Enter Password: ");
        String pass = input.nextLine();

        System.out.println(login.registerUser(user, pass, first, last));

        System.out.print("Login - Enter Username: ");
        String loginUser = input.nextLine();
        System.out.print("Login - Enter Password: ");
        String loginPass = input.nextLine();
        System.out.println(login.returnLoginStatus(loginUser, loginPass));

        if (!login.loginUser(loginUser, loginPass)) {
            System.out.println("Login failed. Exiting.");
            return;
        }

        System.out.println("Welcome to QuickChat.");

        int msgNum = 1;
        while (true) {
            System.out.println("\n1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Stored Messages");
            System.out.println("4. Quit");
            System.out.print("Choose: ");
            String mainChoice = input.nextLine();

            if (mainChoice.equals("4")) {
                System.out.println("Total messages sent: " + message.getTotalMessagesStatic());
                break;
            }

            if (mainChoice.equals("1")) {
                System.out.print("How many messages do you wish to enter? ");
                int limit = Integer.parseInt(input.nextLine());

                for (int i = 0; i < limit; i++) {
                    String recipient = "";
                    while (true) {
                        System.out.print("Enter recipient (+27...): ");
                        recipient = input.nextLine();
                        message temp = new message(msgNum, recipient, "test");
                        System.out.println(temp.checkRecipientCell());
                        if (temp.isRecipientValid()) break;
                    }

                    String text = "";
                    while (true) {
                        System.out.print("Enter message (max 250 chars): ");
                        text = input.nextLine();
                        message temp = new message(msgNum, recipient, text);
                        if (temp.isMessageLengthValid()) break;
                        System.out.println("Please enter a message of less than 250 characters.");
                    }

                    message msg = new message(msgNum, recipient, text);
                    System.out.println("Message Hash: " + msg.getMessageHash());

                    System.out.println("1. Send Message");
                    System.out.println("2. Disregard Message");
                    System.out.println("3. Store Message");
                    System.out.print("Choose: ");
                    String sendChoice = input.nextLine();

                   String result = msg.SentMessage(sendChoice);
System.out.println(result);


if (msg.getStatus().equals("Disregarded")) {
    System.out.print("Press 0 to delete message or press any other key to keep it: ");
    String deleteChoice = input.nextLine();
    if (deleteChoice.equals("0")) {
        msg.SentMessage("0");
        System.out.println("Message deleted.");
        msgNum++;
        continue; 
    }
}


System.out.println(result);


if (msg.getStatus().equals("Disregarded")) {
    System.out.print("Press 0 to delete message or press any other key to keep it: ");
    String deleteChoice = input.nextLine();
    if (deleteChoice.equals("0")) {
        msg.SentMessage("0"); 
        System.out.println("Message deleted.");
        msgNum++;
        continue; 
    }
}

if (msg.getStatus().equals("Sent")) {
    System.out.println(msg.getFullDetails());
}
msg.recordMessage();
msgNum++;
                }
            }

            if (mainChoice.equals("2")) {
                System.out.println("Sent Messages: " + message.getSentMessages());
                System.out.println("Longest: " + message.getLongestMessage());
                System.out.println("--- Message Report ---");
                System.out.println(message.displayReport());
            }

            if (mainChoice.equals("3")) {
                System.out.println("a. Display sender and recipient of stored messages");
                System.out.println("b. Display longest stored message");
                System.out.println("c. Search for message ID");
                System.out.println("d. Search for all messages for a recipient");
                System.out.println("e. Delete a message using hash");
                System.out.println("f. Display report");
                System.out.println("g. Display all stored messages from JSON file");
                System.out.print("Choose a-g: ");
                String c = input.nextLine().toLowerCase();
                if (c.equals("a")) System.out.println(message.displayStoredSenderRecipient());
                if (c.equals("b")) System.out.println(message.getLongestStoredMessage());
                if (c.equals("c")) {
                    System.out.print("Enter Message ID: ");
                    System.out.println(message.searchStoredByID(input.nextLine()));
                }
                if (c.equals("d")) {
                    System.out.print("Enter Recipient: ");
                    System.out.println(message.searchByRecipient(input.nextLine()));
                }
                if (c.equals("e")) {
                    System.out.print("Enter Message Hash: ");
                    System.out.println(message.deleteByHash(input.nextLine()));
                }
                if (c.equals("f")) System.out.println(message.displayFullStoredReport());
                if (c.equals("g")) System.out.println(message.readStoredMessagesFromFile());
            }
        }
        input.close();
    }
}