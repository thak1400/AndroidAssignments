package com.code.wlu.abdulrahman.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;
    private String Activity_Name="Main Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(Activity_Name,"On Create called");
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent,10);
            }
        });
    }

    protected  void onActivityResult(int requestCode, int responseCode, Intent data) {

        super.onActivityResult(requestCode, responseCode, data);
        if(requestCode==10 && responseCode== Activity.RESULT_OK){
            Log.i("MainActivity", "Returned to MainActivity.onActivityResult");
            String messagePassed = data.getStringExtra("Response");

            int duration=Toast.LENGTH_SHORT;
            Toast toast=Toast.makeText(MainActivity.this,messagePassed,duration);
            //R.string.response
            toast.show();
        }
    }
    protected void onStart() {
        Log.i(Activity_Name,"On Start called");
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