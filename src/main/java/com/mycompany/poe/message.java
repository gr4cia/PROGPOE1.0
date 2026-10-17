/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

import java.util.Random;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 *
 * @author Student
 */
public class message {
   private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;
    private String status;
    
      private static int totalCaptured = 0;
    private static int totalSent = 0;
    private static String sentReport = "";

    public message(int messageNumber, String recipient, String message) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        this.status = "";
    }

    public static String generateMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(rand.nextInt(10));
        }
        return id.toString();
    }

    public boolean checkMessageID() {
        return messageID.length() == 10;
    }

    public String getGeneratedIDMessage() {
        return "Message ID generated: " + messageID;
    }
   public String checkRecipientCell() {
        if (recipient == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        if (recipient.startsWith("+") && recipient.length() >= 10 && recipient.length() <= 13) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public boolean isRecipientValid() {
        return checkRecipientCell().equals("Cell phone number successfully captured.");
    }
   public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int over = message.length() - 250;
            return "Message exceeds 250 characters by " + over + ", please reduce the size.";
        }
    }

    public String checkMessageLengthTest() {
        return checkMessageLength();
    }

    public boolean isMessageLengthValid() {
        return message.length() <= 250;
    }
     public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        if (message == null || message.trim().isEmpty()) {
            return (firstTwo + ":" + messageNumber + ":EMPTY").toUpperCase();
        }

        String des = message.trim();

        // First word - same as masterclass
        String firstWord;
        int firstSpace = des.indexOf(" ");
        if (firstSpace == -1) {
            firstWord = des;
        } else {
            firstWord = des.substring(0, firstSpace);
        }
    
  

