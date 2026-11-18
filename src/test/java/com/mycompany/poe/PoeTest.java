/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package com.mycompany.poe;

/**
 *
 * @author Student
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PoeTest {
    @Test
    public void testUsernameCorrect() {
        Login l = new Login();
        assertTrue(l.checkUserName("kyl_1"));
    }
    @Test
    public void testUsernameIncorrect() {
        Login l = new Login();
        assertFalse(l.checkUserName("kyle!!!!!!!"));
    }
    @Test
    public void testPasswordCorrect() {
        Login l = new Login();
        assertTrue(l.checkPasswordComplexity("Ch&&sec@ke99!"));
    }
    @Test
    public void testPasswordIncorrect() {
        Login l = new Login();
        assertFalse(l.checkPasswordComplexity("password"));
    }
    @Test
    public void testLoginSuccess() {
        Login l = new Login();
        l.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith");
        assertTrue(l.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }
    @Test
    public void testLoginFailed() {
        Login l = new Login();
        l.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith");
        assertFalse(l.loginUser("kyl_1", "wrong"));
    }
}