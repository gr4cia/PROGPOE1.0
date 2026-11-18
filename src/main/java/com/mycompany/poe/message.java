/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

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
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status;

    private static int totalSent = 0;

   
    public static List<message> sentList = new ArrayList<>();
    public static List<message> disregardedList = new ArrayList<>();
    public static List<message> storedList = new ArrayList<>();
    public static List<String> hashList = new ArrayList<>();
    public static List<String> idList = new ArrayList<>();

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
        sentList.clear();
        disregardedList.clear();
        storedList.clear();
        hashList.clear();
        idList.clear();
    }

    public static void addToLists(message m) {
        if (m.getStatus().equals("Sent")) sentList.add(m);
        if (m.getStatus().equals("Disregarded")) disregardedList.add(m);
        if (m.getStatus().equals("Stored")) storedList.add(m);
        hashList.add(m.getMessageHash());
        idList.add(m.getMessageID());
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
            
            java.io.File file = new java.io.File(fileName);
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
        for (int i = 0; i < sentList.size(); i++) {
            out += sentList.get(i).getMessage();
            if (i < sentList.size() - 1) out += ", ";
        }
        return out;
    }

    public static String getLongestMessage() {
        List<message> all = new ArrayList<>();
        all.addAll(sentList); all.addAll(storedList); all.addAll(disregardedList);
        String longest = "";
        for (message m : all) {
            if (m.getMessage().length() > longest.length()) longest = m.getMessage();
        }
        return longest;
    }

    public static String searchByID(String id) {
        List<message> all = new ArrayList<>();
        all.addAll(sentList); all.addAll(storedList); all.addAll(disregardedList);
        for (message m : all) {
            if (m.getMessageID().equals(id)) return m.getMessage();
        }
        return "Message not found.";
    }

    public static String searchByRecipient(String recipient) {
        String out = "";
        for (message m : sentList) {
            if (m.getRecipient().equals(recipient)) out += m.getMessage() + ", ";
        }
        for (message m : storedList) {
            if (m.getRecipient().equals(recipient)) out += m.getMessage() + ", ";
        }
        if (out.length() > 2) out = out.substring(0, out.length() - 2);
        return out;
    }

    public static String deleteByHash(String hash) {
        List<message> all = new ArrayList<>();
        all.addAll(sentList); all.addAll(storedList); all.addAll(disregardedList);
        for (message m : all) {
            if (m.getMessageHash().equals(hash)) {
                String msgText = m.getMessage();
                sentList.remove(m); storedList.remove(m); disregardedList.remove(m);
                return "Message: \"" + msgText + "\" successfully deleted.";
            }
        }
        return "Message not found.";
    }

    public static String displayReport() {
        String out = "";
        for (message m : sentList) {
            out += "Message Hash: " + m.getMessageHash() + "\n";
            out += "Recipient: " + m.getRecipient() + "\n";
            out += "Message: " + m.getMessage() + "\n\n";
        }
        return out;
    }

    public static String displayStoredSenderRecipient() {
        String out = "";
        for (message m : storedList) {
            out += "Sender: Developer | Recipient: " + m.getRecipient() + "\n";
        }
        return out;
    }

    public static String getLongestStoredMessage() {
        String longest = "";
        for (message m : storedList) {
            if (m.getMessage().length() > longest.length()) longest = m.getMessage();
        }
        return longest;
    }

    public static String searchStoredByID(String id) {
        for (message m : storedList) {
            if (m.getMessageID().equals(id)) return "Recipient: " + m.getRecipient() + "\nMessage: " + m.getMessage();
        }
        return "Not found.";
    }

    public static String displayFullStoredReport() {
        String out = "";
        for (message m : storedList) {
            out += m.getFullDetails() + "\n\n";
        }
        return out;
    }
}