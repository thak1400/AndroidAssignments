package com.code.wlu.abdulrahman.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    ListView list_View;
    EditText chat_Edit_Text;


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
        send_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sent_Text = String.valueOf(chat_Edit_Text.getText());
                if(!validateInputMessage(sent_Text))
                {
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

}

