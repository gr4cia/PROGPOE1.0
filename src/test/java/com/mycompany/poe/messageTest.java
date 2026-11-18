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

   
    @Test
    public void testRecipientValid() {
        message msg = new message(1, "+27718693002", "Hi");
        assertTrue(msg.isRecipientValid());
    }

    @Test
    public void testRecipientInvalid() {
        message msg = new message(1, "+1234567890", "Hi");
        assertFalse(msg.isRecipientValid());
    }

    @Test
    public void testMessageLengthValid() {
        message msg = new message(1, "+27718693002", "Hi");
        assertTrue(msg.isMessageLengthValid());
    }

    @Test
    public void testSentMessageSend() {
        message msg = new message(1, "+27718693002", "Hi");
        assertEquals("Message successfully sent.", msg.SentMessage("1"));
    }

    @Test
    public void testSentMessageDisregard() {
        message msg = new message(1, "+27718693002", "Hi");
        assertEquals("Press 0 to delete message.", msg.SentMessage("2"));
    }

    @Test
    public void testSentMessageStore() {
        message msg = new message(1, "+27718693002", "Hi");
        assertEquals("Message successfully stored.", msg.SentMessage("3"));
    }

    @Test
    public void testHashLoopWithPart2Data() {
        String[] messages = {
            "Hi Mike, can you join us for dinner tonight",
            "Hi Keegan, did you receive the payment?"
        };
        String[] recipients = { "+27718693002", "+27834557896" };
        String[] expectedEnds = { "HITONIGHT", "HIPAYMENT" };
        for (int i = 0; i < messages.length; i++) {
            message msg = new message(i + 1, recipients[i], messages[i]);
            String hash = msg.getMessageHash();
            assertTrue(hash.endsWith(":" + (i + 1) + ":" + expectedEnds[i]));
        }
    }

    @Test
    public void testSentMessageStoreAndTotal() {
        message.clearAllLists();
        message.resetTotal();
        int before = message.getTotalMessagesStatic();
        message msg = new message(1, "+27718693002", "Hi");
        msg.SentMessage("3");
        msg.recordMessage();
        assertEquals(before, message.getTotalMessagesStatic());
    }

    private message makeTestData() {
        message.clearAllLists();
        message.resetTotal();
        message m1 = new message(1, "+27834557896", "Did you get the cake?");
        m1.SentMessage("1"); m1.recordMessage();
        message m2 = new message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m2.SentMessage("3"); m2.recordMessage();
        message m3 = new message(3, "+27834484567", "Yohooo, I am at your gate.");
        m3.SentMessage("2"); m3.recordMessage();
        message m4 = new message(4, "+27838884567", "It is dinner time!");
        m4.setMessageID("0838884567");
        m4.SentMessage("1"); m4.recordMessage();
        message m5 = new message(5, "+27838884567", "Ok, I am leaving without you.");
        m5.SentMessage("3"); m5.recordMessage();
        return m2;
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        makeTestData();
        assertEquals("Did you get the cake?, It is dinner time!", message.getSentMessages());
    }

    @Test
    public void testDisplayLongestMessage() {
        makeTestData();
        assertEquals("Where are you? You are late! I have asked you to be on time.", message.getLongestMessage());
    }

    @Test
    public void testSearchForMessageID() {
        makeTestData();
       
        String foundID = "";
        for (int i = 0; i < message.messageCount; i++) {
            if (message.messageArray[i].equals("It is dinner time!")) foundID = message.messageIDArray[i];
        }
        assertEquals("It is dinner time!", message.searchByID(foundID));
    }

    @Test
    public void testSearchAllMessagesByRecipient() {
        makeTestData();
      assertEquals("Where are you? You are late! I have asked you to be on time., It is dinner time!, Ok, I am leaving without you.", message.searchByRecipient("+27838884567"));
    }

    @Test
    public void testDeleteMessageUsingHash() {
        message m2 = makeTestData();
        String hash = m2.getMessageHash();
        String result = message.deleteByHash(hash);
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", result);
    }

    @Test
    public void testDisplayReport() {
        makeTestData();
        String report = message.displayReport();
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time!"));
        assertTrue(report.contains("+27834557896"));
    }

    @Test
    public void testReadJSONFile() {
        String content = message.readStoredMessagesFromFile();
        assertNotNull(content);
    }
}