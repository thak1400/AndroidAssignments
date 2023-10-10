package com.code.wlu.abdulrahman.myapplication;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainActivityTest {

    @Test
    public void onCreate()
    {
        assertEquals(0, MainActivity.class.getName().compareTo("com.code.wlu.abdulrahman.myapplication.MainActivity"));
    }

    @Test
    public void onStart()
    {
        assertFalse(MainActivity.class.isArray());
    }

}