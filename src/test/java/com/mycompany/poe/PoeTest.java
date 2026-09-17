package com.mycompany.poe;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PoeTest {

    private Login system;

    @BeforeEach
    void setUp() {
        system = new Login();
    }

    @Test
    void testUsernameCorrectlyFormatted() {
        assertTrue(system.checkUserName("sip_3"));
        assertEquals("Username successfully captured.", system.getUsernameCaptureMessage("sip_3"));
    }

    @Test
    void testUsernameIncorrectlyFormatted() {
        assertFalse(system.checkUserName("sipho!!!!"));
        assertEquals("Username is not correctly formatted, please ensure that your username "
                + "contains an underscore and is no more than 5 characters in length.", system.getUsernameCaptureMessage("sipho!!!!"));
    }

    @Test
    void testPasswordCorrectlyFormatted() {
        assertTrue(system.checkPasswordComplexity("S@f3Code!"));
        assertEquals("Password successfully captured.", system.getPasswordCaptureMessage("S@f3Code!"));
    }

    @Test
    void testPasswordIncorrectlyFormatted() {
        assertFalse(system.checkPasswordComplexity("weakpass"));
        assertEquals("Password is not correctly formatted, please ensure that the password contains at least"
                + " 8 characters, a capital letter, a number and a special character.", system.getPasswordCaptureMessage("weakpass"));
    }

    @Test
    void testCellPhoneCorrectlyFormatted_BriefData() {
        assertTrue(system.checkCellPhoneNumber("+27838968976"));
        assertEquals("Cell number successfully captured.", system.getCellPhoneCaptureMessage("+27838968976"));
    }

    @Test
    void testCellPhoneIncorrectlyFormatted_BriefData() {
        assertFalse(system.checkCellPhoneNumber("08966553"));
        assertFalse(system.checkCellPhoneNumber("0834567890"));
        assertEquals("Cell number is incorrectly formatted or does not contain an international code, please correct the number.",
                system.getCellPhoneCaptureMessage("08966553"));
    }

    // ========== LOGIN TESTS ==========

    @Test
    void testLoginSuccessful() {
        system.registerUser("sip_3", "S@f3Code!", "+27834567890", "Sipho", "Dlamini");
        assertTrue(system.loginUser("sip_3", "S@f3Code!"));
        assertEquals("Welcome Sipho Dlamini, it is great to see you again.", system.returnLoginStatus("sip_3", "S@f3Code!"));
    }

    @Test
    void testLoginFailed() {
        system.registerUser("sip_3", "S@f3Code!", "+27834567890", "Sipho", "Dlamini");
        assertFalse(system.loginUser("wrong", "wrong"));
        assertEquals("Username or password incorrect, please try again.", system.returnLoginStatus("wrong", "wrong"));
    }
}