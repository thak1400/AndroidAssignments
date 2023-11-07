package com.code.wlu.abdulrahman.myapplication;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChatWindowTest {

    @Test
    public void validateInputMessageTest()
    {
        assertTrue(ChatWindow.validateInputMessage(""));
        assertFalse(ChatWindow.validateInputMessage("Test Message"));
    }
    @Test
    public void onCreate()
    {
        assertEquals(0, ChatWindow.class.getName().compareTo("com.code.wlu.abdulrahman.myapplication.ChatWindow"));
        assertNotEquals(0, ChatWindow.class.getName().compareTo("com.code.wlu.abdulrahman.myapplication.CW"));
    }

    @Test
    public void onStart()
    {
        assertFalse(ChatWindow.class.getClass().isArray());
    }

    public void validateActivityName()
    {
        assertTrue(ChatWindow.validateActivityName("ChatWindow"));
    }
}