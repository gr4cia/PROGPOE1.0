/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

import java.util.Random;
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
     String lastWord;
        int lastSpace = des.lastIndexOf(" ");
        if (lastSpace == -1) {
            lastWord = des;
        } else {
            lastWord = des.substring(lastSpace + 1);
        }
         firstWord = firstWord.replaceAll("[^a-zA-Z0-9]", "");
        lastWord = lastWord.replaceAll("[^a-zA-Z0-9]", "");

        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
     }

  public String SentMessage(String choice) {
        String c = choice.toLowerCase();
        if (c.equals("1") || c.contains("send")) {
            String status = "Sent";
            return "Message successfully sent.";
        } else if (c.equals("2") || c.contains("disregard") || c.contains("discard") || c.equals("0")) {
            String status = "Disregarded";
            return "Press 0 to delete the message.";
        } else if (c.equals("3") || c.contains("store")) {
            String status = "Stored";
            return "Message successfully stored.";
        }
        return "Invalid option.";
    }

   public void recordMessage() {
        totalCaptured = totalCaptured + 1;
        if (status.equals("Sent")) {
            totalSent = totalSent + 1;
            sentReport = sentReport + getFullDetails() + "\n----------------\n";
        }
        if (status.equals("Sent") || status.equals("Stored")) {
            storeMessage();
        }
    }
 public String getFullDetails() {
        return "MessageID: " + messageID +
               "\nMessageHash: " + messageHash +
               "\nRecipient: " + recipient +
               "\nMessage: " + message;
    }

    public static String printMessagesStatic() {
        if (sentReport.equals("")) {
            return "No messages sent.";
        }
        return sentReport;
    }

    public int returnTotalMessages() {
        return totalSent;
    }

    public static int getTotalMessagesStatic() {
        return totalSent;
    }
     public void storeMessage() {
        String fileName = "messages.json";
        String existing = "[]";
        try {
            if (Files.exists(Paths.get(fileName))) {
                existing = new String(Files.readAllBytes(Paths.get(fileName)));
                if (existing.trim().isEmpty()) existing = "[]";
            }
        } catch (IOException e) {
            existing = "[]";
        }

        String safeMsg = message.replace("\"", "'");
        String jsonObj = "{\"MessageID\":\"" + messageID + "\",\"MessageNumber\":" + messageNumber + ",\"MessageHash\":\"" + messageHash + "\",\"Recipient\":\"" + recipient + "\",\"Message\":\"" + safeMsg + "\"}";

        String newContent;
        if (existing.trim().equals("[]")) {
            newContent = "[" + jsonObj + "]";
        } else {
            newContent = existing.trim();
            if (newContent.endsWith("]")) {
                newContent = newContent.substring(0, newContent.length() - 1);
                if (!newContent.trim().endsWith("[")) newContent += ",";
                newContent += jsonObj + "]";
            } else {
                newContent = "[" + jsonObj + "]";
            }
        }

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(newContent);
        } catch (IOException e) {
            System.out.println("Error storing: " + e.getMessage());
        }
    }
      public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
}
