package com.code.wlu.abdulrahman.myapplication;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChatDatabaseHelperTest {

    @Test
    public void onCreate() {
    }

    @Test
    public void onUpgrade() {
    }

    @Test
    public void dbNameChecker() {
        assertTrue(ChatDatabaseHelper.dbNameChecker("Messages.db"));
    }

    @Test
    public void tableNameChecker() {
        assertTrue(ChatDatabaseHelper.tableNameChecker("Messages_Table"));
    }

    @Test
    public void versChecker() {
        assertTrue(ChatDatabaseHelper.versChecker(4));
    }

    @Test
    public void msgIDChecker()
    {
        assertTrue(ChatDatabaseHelper.msgIDChecker("Msgs"));
    }

    @Test
    public void keyIDChecker()
    {
        assertTrue(ChatDatabaseHelper.keyIDChecker("id"));
    }

    @Test
    public void validateActivityName()
    {
        assertTrue(ChatDatabaseHelper.validateActivityName("ChatDatabaseHelper"));
    }
}