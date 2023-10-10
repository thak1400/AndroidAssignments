package com.code.wlu.abdulrahman.myapplication;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoginActivityTest {

    @Test
    public void validatePwd()
    {
        assertTrue(LoginActivity.validatePwd(""));
        assertFalse(LoginActivity.validatePwd("abc"));
    }

    @Test
    public void onCreate()
    {
        assertEquals(0, LoginActivity.class.getName().compareTo("com.code.wlu.abdulrahman.myapplication.LoginActivity"));
        assertNotEquals(0, LoginActivity.class.getName().compareTo("com.code.wlu.abdulrahman.myapplication.CW"));
        //assertTrue();
    }

    @Test
    public void onStart()
    {
        assertFalse(LoginActivity.class.isArray());
    }
}