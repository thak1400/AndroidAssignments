package com.code.wlu.abdulrahman.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends MainActivity {
    private String Activity_Name="Login Activity";
    Button login_Button;
    EditText login_email;
    EditText password_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(Activity_Name,"On Create called");
        login_email=findViewById(R.id.login_email_text);
        login_Button=findViewById(R.id.button_Login);
        password_text=findViewById(R.id.password_text);
        SharedPreferences prefs = getSharedPreferences("email_Stored", 0);
        String my_Email=prefs.getString("DefaultEmail", "email@domain.com");
        login_email.setText(my_Email);
        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fetched_Password= String.valueOf(password_text.getText());
                String fetched_Email= String.valueOf(login_email.getText());

                //check email
                //check pass
                //set data
                if(!validateEmailId(fetched_Email)){
                    int duration=Toast.LENGTH_SHORT;
                    Toast toast=Toast.makeText(LoginActivity.this,R.string.email_Toast,duration);
                    toast.show();
                }
                else if(validatePwd(fetched_Password)) {
                    int duration= Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(LoginActivity.this,R.string.password_Toast,duration);
                    toast.show();
                }
                else{
                    setEmailData(fetched_Email);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }

        });
    }
    protected void setEmailData(String email){
        SharedPreferences prefs = getSharedPreferences("email_Stored", 0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("DefaultEmail",email);
        edit.commit();
    }

    protected boolean validateEmailId(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    protected  static boolean validatePwd(String str)
    {
        if(str.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    protected void onStart() {
        Log.i(Activity_Name,"On Start called");
        Log.i(Activity_Name,LoginActivity.class.getName());
        super.onStart();
    }

    protected void onRestart() {
        Log.i(Activity_Name,"On Restart called");
        super.onRestart();
    }

    protected void onResume() {
        Log.i(Activity_Name,"On Resume called");
        super.onResume();
    }

    protected void onPause() {
        Log.i(Activity_Name,"On Pause called");
        super.onPause();
    }

    protected void onStop() {
        Log.i(Activity_Name,"On Stop called");
        super.onStop();
    }

    protected void onDestroy() {
        Log.i(Activity_Name,"On destroy called");
        super.onDestroy();
    }
}