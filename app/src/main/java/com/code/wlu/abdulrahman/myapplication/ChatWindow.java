package com.code.wlu.abdulrahman.myapplication;

import static com.code.wlu.abdulrahman.myapplication.ChatDatabaseHelper.KEY_MESSAGE;
import static com.code.wlu.abdulrahman.myapplication.ChatDatabaseHelper.TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    Button send_Button;
    int REQUEST_FOR_ACTIVITY=10;
    static String ACTIVITY_NAME="ChatWindow";
    ListView list_View;
    EditText chat_Edit_Text;
    FrameLayout displayMessageDetails;

    ChatAdapter messageAdapter;

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
        displayMessageDetails = findViewById(R.id.displayMessageDetails);
        Boolean frameExists = displayMessageDetails == null ? false : true;
        messageAdapter =new ChatAdapter( this );
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
        list_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (frameExists) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("msg_id", id);
                    bundle.putString("msg", chats_Arr_List.get(position));
                    bundle.putInt("position", position);
                    MessageFragment ms = new MessageFragment(ChatWindow.this);
                    ms.setArguments(bundle);
                    FragmentTransaction tranX=getSupportFragmentManager().beginTransaction();
                    tranX.setReorderingAllowed(true);
                    tranX.replace(R.id.displayMessageDetails, ms);
                    tranX.commit();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putLong("msg_id", id);
                    bundle.putString("msg", chats_Arr_List.get(position));
                    bundle.putInt("position", position);
                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    intent.putExtras(bundle);

                    startActivityForResult(intent, REQUEST_FOR_ACTIVITY);
                }
            }
        });
    }
    public void deleteMessage(long msgId, int position) {
        try {
            ChatDatabaseHelper dbHelper = ChatDatabaseHelper.getInstance(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ChatDatabaseHelper.TABLE_NAME,ChatDatabaseHelper.KEY_ID + "= ?",  new String[] { String.valueOf(msgId) });
            chats_Arr_List.remove(messageAdapter.getItem(position));
            messageAdapter.remove(messageAdapter.getItem(position));
            messageAdapter.notifyDataSetChanged();
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_FOR_ACTIVITY) {
            try {
                long msgId = data.getLongExtra("msg_id", 0);
                int position = data.getIntExtra("position", 0);
                ChatDatabaseHelper dbHelper = ChatDatabaseHelper.getInstance(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                db.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID + "= ?",  new String[] { String.valueOf(msgId) });
                chats_Arr_List.remove(messageAdapter.getItem(position));
                messageAdapter.remove(messageAdapter.getItem(position));
                messageAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }
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

