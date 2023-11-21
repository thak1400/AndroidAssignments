package com.code.wlu.abdulrahman.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends ChatWindow {
    Button button, start_Chat, test_Toolbar,weather_Button;
    private static String ACTIVITY_NAME="Main Activity";

    Spinner spinCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME,"On Create called");
        spinCity=findViewById(R.id.spinCity);
        button=findViewById(R.id.button);
        start_Chat=findViewById(R.id.start_Chat);
        test_Toolbar=findViewById(R.id.test_Toolbar);
        weather_Button=findViewById(R.id.weather_Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent,10);
            }
        });

        start_Chat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.i(ACTIVITY_NAME,"User clicked Start Chat button");
                Intent intent = new Intent(MainActivity.this,ChatWindow.class);
                startActivityForResult(intent,5);
            }
        });

        test_Toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME,"User clicked Test Toolbar button");
                Intent intent = new Intent(MainActivity.this,TestToolbar.class);
                startActivityForResult(intent,6);
            }
        });

        weather_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME,"User clicked Weather Forecast button");
                Intent intent = new Intent(MainActivity.this, WeatherForecast.class);

                startActivityForResult(intent,6);

            }
        });

        ArrayAdapter<CharSequence> arrAd=ArrayAdapter.createFromResource(this,R.array.CAN, android.R.layout.simple_spinner_item);
        arrAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCity.setAdapter(arrAd);

        weather_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city=spinCity.getSelectedItem().toString();
                if(!city.isEmpty())
                {
                    Intent intent = new Intent(MainActivity.this, WeatherForecast.class);
                    intent.putExtra("SelectedCity",city);
                    startActivity(intent);
                }
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
        Log.i(ACTIVITY_NAME,"On Start called");
        Log.i(ACTIVITY_NAME,MainActivity.class.getName());
        super.onStart();
    }

    protected void onRestart() {
        Log.i(ACTIVITY_NAME,"On Restart called");
        super.onRestart();
    }

    protected void onResume() {
        Log.i(ACTIVITY_NAME,"On Resume called");
        super.onResume();
    }

    protected void onPause() {
        Log.i(ACTIVITY_NAME,"On Pause called");
        super.onPause();
    }

    protected void onStop() {
        Log.i(ACTIVITY_NAME,"On Stop called");
        super.onStop();
    }

    protected void onDestroy() {
        Log.i(ACTIVITY_NAME,"On destroy called");
        super.onDestroy();
    }

    public static boolean validateActivityName(String actName)
    {
        return actName.equals(ACTIVITY_NAME);
    }

}