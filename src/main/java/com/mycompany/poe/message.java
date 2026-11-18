/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Student
 */
public class message {
    // Instance variables
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status;

    private static int totalSent = 0;

    public static String[] messageIDArray = new String[100];
    public static String[] recipientArray = new String[100];
    public static String[] messageArray = new String[100];
    public static String[] hashArray = new String[100];
    public static String[] flagArray = new String[100]; 
    public static int messageCount = 0;

    public static String[] sentMessagesArray = new String[100];
    public static int sentCount = 0;

    public message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        this.status = "";
    }

    public static String generateMessageID() {
        Random rand = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id = id + rand.nextInt(10);
        }
        return id;
    }

   
    public void setMessageID(String id) {
        this.messageID = id;
    }

    
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getStatus() { return status; }
    public int getMessageNumber() { return messageNumber; }

   
    public boolean isRecipientValid() {
        return recipient.startsWith("+27") && recipient.length() == 12;
    }

    public String checkRecipientCell() {
        if (isRecipientValid()) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    
    public boolean isMessageLengthValid() {
        return messageText.length() <= 250;
    }

    
    public String createMessageHash() {
        String[] words = messageText.split(" ");
        String first = words[0];
        String last = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
        String hash = messageID.substring(0, 2) + ":" + messageNumber + ":" + first.toUpperCase() + last;
        this.messageHash = hash;
        return hash;
    }

    
    public String SentMessage(String option) {
        if (option.equals("1")) {
            status = "Sent";
            return "Message successfully sent.";
        } else if (option.equals("2")) {
            status = "Disregarded";
            return "Press 0 to delete message.";
        } else if (option.equals("3")) {
            status = "Stored";
            return "Message successfully stored.";
        } else if (option.equals("0")) {
            status = "Deleted";
            return "Message deleted.";
        }
        return "Invalid option.";
    }

    public String getFullDetails() {
        return "Message ID: " + messageID + "\n"
             + "Message Hash: " + messageHash + "\n"
             + "Recipient: " + recipient + "\n"
             + "Message: " + messageText;
    }

    
    public static void clearAllLists() {
        messageCount = 0;
        sentCount = 0;
        for (int i = 0; i < 100; i++) {
            messageIDArray[i] = null;
            recipientArray[i] = null;
            messageArray[i] = null;
            hashArray[i] = null;
            flagArray[i] = null;
            sentMessagesArray[i] = null;
        }
    }

    
    public static void addToLists(message m) {
        messageIDArray[messageCount] = m.getMessageID();
        recipientArray[messageCount] = m.getRecipient();
        messageArray[messageCount] = m.getMessage();
        hashArray[messageCount] = m.getMessageHash();
        flagArray[messageCount] = m.getStatus();
        messageCount++;

        if (m.getStatus().equals("Sent")) {
            sentMessagesArray[sentCount] = m.getMessage();
            sentCount++;
        }
    }

    public void recordMessage() {
        if (status.equals("Sent")) {
            totalSent++;
        }
        addToLists(this);
        if (status.equals("Stored")) {
            storeMessage();
        }
    }

    public static int getTotalMessagesStatic() {
        return totalSent;
    }

    public static void resetTotal() {
        totalSent = 0;
    }

    
    public void storeMessage() {
        try {
            String fileName = "stored_messages.json";
            File file = new File(fileName);
            String existing = "";
            if (file.exists()) {
                java.util.Scanner sc = new java.util.Scanner(file);
                while (sc.hasNextLine()) existing += sc.nextLine();
                sc.close();
            }
            String newEntry = "{\"id\":\"" + messageID + "\",\"hash\":\"" + messageHash + "\",\"recipient\":\"" + recipient + "\",\"message\":\"" + messageText.replace("\"", "") + "\"}";
            String toWrite;
            if (existing.isEmpty() || existing.equals("[]")) {
                toWrite = "[" + newEntry + "]";
            } else {
                toWrite = existing.substring(0, existing.length() - 1) + "," + newEntry + "]";
            }
            FileWriter writer = new FileWriter(fileName);
            writer.write(toWrite);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public static String getSentMessages() {
        String out = "";
        for (int i = 0; i < sentCount; i++) {
            out += sentMessagesArray[i];
            if (i < sentCount - 1) out += ", ";
        }
        return out;
    }
  
    public static String getLongestMessage() {
        String longest = "";
        for (int i = 0; i < messageCount; i++) {
            if (messageArray[i].length() > longest.length()) longest = messageArray[i];
        }
        return longest;
    }

    public static String searchByID(String id) {
        for (int i = 0; i < messageCount; i++) {
            if (messageIDArray[i].equals(id)) return messageArray[i];
        }
        return "Message not found.";
    }

    public static String searchByRecipient(String recipient) {
        String out = "";
        for (int i = 0; i < messageCount; i++) {
            if (recipientArray[i].equals(recipient) && (flagArray[i].equals("Sent") || flagArray[i].equals("Stored"))) {
                out += messageArray[i] + ", ";
            }
        }
        if (out.length() > 2) out = out.substring(0, out.length() - 2);
        return out;
    }

    public static String deleteByHash(String hash) {
        for (int i = 0; i < messageCount; i++) {
            if (hashArray[i].equals(hash)) {
                String msgText = messageArray[i];
                for (int j = i; j < messageCount - 1; j++) {
                    messageIDArray[j] = messageIDArray[j + 1];
                    recipientArray[j] = recipientArray[j + 1];
                    messageArray[j] = messageArray[j + 1];
                    hashArray[j] = hashArray[j + 1];
                    flagArray[j] = flagArray[j + 1];
                }
                messageCount--;
                return "Message: \"" + msgText + "\" successfully deleted.";
            }
        }
        return "Message not found.";
    }

    public static String readStoredMessagesFromFile() {
        try {
            File file = new File("stored_messages.json");
            if (!file.exists()) return "No stored messages found.";
            java.util.Scanner sc = new java.util.Scanner(file);
            String content = "";
            while (sc.hasNextLine()) content += sc.nextLine();
            sc.close();
            return content;
        } catch (Exception e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    public static String displayReport() {
        String out = "";
        for (int i = 0; i < messageCount; i++) {
            if (flagArray[i].equals("Sent")) {
                out += "Message Hash: " + hashArray[i] + "\n";
                out += "Recipient: " + recipientArray[i] + "\n";
                out += "Message: " + messageArray[i] + "\n\n";
            }
        }
        return out;
    }

    public static String displayStoredSenderRecipient() {
        String out = "";
        for (int i = 0; i < messageCount; i++) {
            if (flagArray[i].equals("Stored")) {
                out += "Sender: Developer | Recipient: " + recipientArray[i] + "\n";
            }
        }
        return out;
    }

    public static String getLongestStoredMessage() {
        String longest = "";
        for (int i = 0; i < messageCount; i++) {
            if (flagArray[i].equals("Stored") && messageArray[i].length() > longest.length()) {
                longest = messageArray[i];
            }
        }
        return longest;
    }

    public static String searchStoredByID(String id) {
        for (int i = 0; i < messageCount; i++) {
            if (flagArray[i].equals("Stored") && messageIDArray[i].equals(id)) {
                return "Recipient: " + recipientArray[i] + "\nMessage: " + messageArray[i];
            }
        }
        return "Not found.";
    }

    public static String displayFullStoredReport() {
        String out = "";
        for (int i = 0; i < messageCount; i++) {
            if (flagArray[i].equals("Stored")) {
                out += "Message ID: " + messageIDArray[i] + "\n";
                out += "Message Hash: " + hashArray[i] + "\n";
                out += "Recipient: " + recipientArray[i] + "\n";
                out += "Message: " + messageArray[i] + "\n\n";
            }
        }
        return out;
    }
}