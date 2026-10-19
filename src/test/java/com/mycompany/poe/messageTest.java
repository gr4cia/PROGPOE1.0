/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

/**
 *
 * @author Student
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class messageTest {



public class MessageTest {

 
    @Test
    public void testRecipientValid() {
        message msg = new message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        System.out.println(msg.checkRecipientCell());
        assertTrue(msg.isRecipientValid());
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientInvalid() {
        message msg = new message(1, "08575975889", "Hi Mike, can you join us for dinner tonight");
        System.out.println(msg.checkRecipientCell());
        assertFalse(msg.isRecipientValid());
        assertTrue(msg.checkRecipientCell().contains("incorrectly formatted"));
    }

    @Test
    public void testMessageLengthValid() {
        message msg = new message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertTrue(msg.isMessageLengthValid());
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthTooLong() {
        String longMsg = "a".repeat(300);
        message msg = new message(1, "+27718693002", longMsg);
        assertFalse(msg.isMessageLengthValid());
        assertTrue(msg.checkMessageLength().contains("exceeded"));
    }

    @Test
    public void testMessageHash() {
        message msg = new message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        String hash = msg.getMessageHash();
        System.out.println("Hash: " + hash);
        assertNotNull(hash);
        assertTrue(hash.contains(":"));
    }

    @Test
    public void testMessageIDLength() {
        String id = message.generateMessageID();
        System.out.println("ID: " + id);
        assertEquals(10, id.length());
    }

    @Test
    public void testSentMessageOptions() {
        message msg = new message(1, "+27718693002", "Hi Mike");
        assertEquals("Message successfully sent.", msg.SentMessage("1"));
        assertTrue(msg.SentMessage("2").contains("Press 0"));
        assertEquals("Message successfully stored.", msg.SentMessage("3"));
    }
    
    @Test
    public void testTotalMessages() {
        int before = message.getTotalMessagesStatic();
        message msg = new message(1, "+27718693002", "Test");
        msg.recordMessage();
        assertEquals(before + 1, message.getTotalMessagesStatic());
    }
}   
}
