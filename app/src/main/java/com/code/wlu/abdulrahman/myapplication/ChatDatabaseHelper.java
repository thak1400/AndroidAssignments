package com.code.wlu.abdulrahman.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    private static ChatDatabaseHelper sInstance;
    static String DATABASE_NAME="Messages.db";
    public static String TABLE_NAME="Messages_Table";
    static String KEY_ID="id";
    public static String KEY_MESSAGE="Msgs";
    static int VERSION_NUM=7;
    static String ACTIVITY_NAME="ChatDatabaseHelper";
    public ChatDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public static synchronized ChatDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ChatDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_Msgs_Table="CREATE TABLE "+ TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_MESSAGE + " TEXT)";
        sqLiteDatabase.execSQL(create_Msgs_Table);
        Log.i(ACTIVITY_NAME, "Calling onCreate");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + i + " newVersion= " + i1);
    }

    public static boolean dbNameChecker(String nameDB)
    {
        return DATABASE_NAME.equals(nameDB);
    }
    public static boolean tableNameChecker(String nameTBL)
    {
        return TABLE_NAME.equals(nameTBL);
    }
    public static boolean versChecker(int vers)
    {
        return VERSION_NUM == vers;
    }

    public static boolean keyIDChecker(String keyID)
    {
        return keyID.equals(KEY_ID);
    }

    public static boolean msgIDChecker(String msgID)
    {
        return msgID.equals(KEY_MESSAGE);
    }

    public static boolean validateActivityName(String actName)
    {
        return actName.equals(ACTIVITY_NAME);
    }
}
