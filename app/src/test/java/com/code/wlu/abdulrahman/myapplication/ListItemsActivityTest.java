package com.code.wlu.abdulrahman.myapplication;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListItemsActivityTest {

    @Test
    public void onCreate()
    {
        assertEquals(0, ListItemsActivity.class.getName().compareTo("com.code.wlu.abdulrahman.myapplication.ListItemsActivity"));
        assertNotEquals(0, ListItemsActivityTest.class.getName().compareTo("com.code.wlu.abdulrahman.myapplication.CW"));
    }
    @Test
    public void onStart()
    {
        assertFalse(ListItemsActivity.class.isArray());
    }
    @Test
    public void print()
        {
            assertFalse(ListItemsActivity.class.isPrimitive());
        }

    @Test
    public void strLeng()
    {
        assertTrue(ListItemsActivity.strLeng("abc"));
    }

}