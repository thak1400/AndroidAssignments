package com.code.wlu.abdulrahman.myapplication;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestToolbarTest {

    @Test
    public void validateDialogInput() {
        assertTrue(TestToolbar.validateDialogInput("Input"));
    }

    @Test
    public void validateActivityName()
    {
        assertTrue(TestToolbar.validateActivityName("TestToolbar"));
    }
}