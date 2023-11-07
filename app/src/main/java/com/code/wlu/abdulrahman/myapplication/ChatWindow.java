package com.code.wlu.abdulrahman.myapplication;

import static com.code.wlu.abdulrahman.myapplication.ChatDatabaseHelper.KEY_MESSAGE;
import static com.code.wlu.abdulrahman.myapplication.ChatDatabaseHelper.TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    Button send_Button;
    static String ACTIVITY_NAME="ChatWindow";
    ListView list_View;
    EditText chat_Edit_Text;

    ChatDatabaseHelper chatDBHelp=new ChatDatabaseHelper(this);
    private SQLiteDatabase db;
    public ArrayList<String> chats_Arr_List=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        send_Button=findViewById(R.id.send_Button);
        list_View=findViewById(R.id.list_View);
        chat_Edit_Text=findViewById(R.id.chat_Edit_Text);

        ChatAdapter messageAdapter =new ChatAdapter( this );
        list_View.setAdapter (messageAdapter);

        db=chatDBHelp.getWritableDatabase();

        String addAllQuery = "SELECT * FROM "+chatDBHelp.TABLE_NAME;
        Cursor cursor = db.rawQuery(addAllQuery,null);
        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount() );

        while(cursor.moveToNext()){
            String msg=cursor.getString(cursor.getColumnIndexOrThrow("Msgs"));
            chats_Arr_List.add(msg);
            messageAdapter.notifyDataSetChanged();
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:"+cursor.getString(cursor.getColumnIndexOrThrow("Msgs")) );
        }
        while(!cursor.isAfterLast() ) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:"+cursor.getString(cursor.getColumnIndexOrThrow("Msgs")) );
        }
        for(int v=0;v<cursor.getColumnCount();v++)
        {
            Log.i(ACTIVITY_NAME, "Column Name: "+cursor.getColumnName(v) );
        }
        cursor.close();
        send_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sent_Text = String.valueOf(chat_Edit_Text.getText());
                if(!validateInputMessage(sent_Text))
                {
                    ContentValues values=new ContentValues();
                    values.put(KEY_MESSAGE,sent_Text);
                    db.insert(TABLE_NAME,null,values);
                    chats_Arr_List.add(sent_Text);
                    messageAdapter.notifyDataSetChanged();
                    chat_Edit_Text.setText("");
                }
                else
                {
                    Toast toast =   Toast.makeText(ChatWindow.this,R.string.emptyMsg,Toast.LENGTH_SHORT);
                    toast.show();
//                    Snackbar snackbar   =   Snackbar.make(findViewById(R.id.list_View),R.string.emptyMsg,Snackbar.LENGTH_SHORT);
//                    snackbar.show();
                }

            }
        });
    }
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME,"On destroy called");
        super.onDestroy();
    }
    protected static boolean validateInputMessage(String msg)
    {
        if(msg.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private class ChatAdapter extends ArrayAdapter<String>
    {

        public ChatAdapter(@NonNull Context context)
        {
            super(context, 0);
        }
        public int getCount()
        {
            return  chats_Arr_List.size();
        }

        public String getItem(int position)
        {
            return chats_Arr_List.get(position);
        }

        @NonNull
        public View getView(int position, View convert_View, @NonNull ViewGroup parent)
        {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if(position%2==0)
            {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                TextView message = result.findViewById((R.id.incoming_Text_View));
                message.setText(   getItem(position)  ); // get the string at position
                return result;
            }
            else
            {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
                TextView message = result.findViewById((R.id.outgoing_Text_View));
                message.setText(   getItem(position)  ); // get the string at position
                return result;
            }

        }
    }

    public static boolean validateActivityName(String actName)
    {
        return actName.equals(ACTIVITY_NAME);
    }

}

