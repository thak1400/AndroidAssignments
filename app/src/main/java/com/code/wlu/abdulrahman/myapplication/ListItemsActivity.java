package com.code.wlu.abdulrahman.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ListItemsActivity extends AppCompatActivity {
    ImageButton img_Button;
    CheckBox checkbox_Button;
    Switch switch_Button;
    private String Activity_Name="ListItemsActivity Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(Activity_Name,"On Create called");
        img_Button=findViewById(R.id.button_Img);
        switch_Button=findViewById(R.id.switch_Button);
        checkbox_Button=findViewById(R.id.checkbox_Button);
        img_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   startActivityForResult(takePictureIntent, 1);

            }
        });
        switch_Button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(switch_Button.isChecked()){

                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(ListItemsActivity.this , R.string.switch_On, duration);
                    toast.show();
                }
                else{

                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(ListItemsActivity.this , R.string.switch_Off, duration);
                    toast.show();
                }

            }
        });
        checkbox_Button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
               builder.setMessage(R.string.dialog_message) //Add a dialog message to strings.xml

                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent resultIntent = new Intent(  );
                                resultIntent.putExtra("Response",getResources().getString(R.string.here_is_my_response));
                                        //("Response",R.string.here_is_my_response);
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .show();
            }
        });
    }

    protected void print(String str1){
//        Snackbar snack=Snackbar.make(CoordinatorLayout,str1,Snackbar.LENGTH_LONG);
//        snack.show();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(ListItemsActivity.this , str1, duration);
        toast.show();
    }
    protected void onStart() {
        Log.i(Activity_Name,"On Start called");
        super.onStart();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageButton btnImg = findViewById(R.id.button_Img);
            btnImg.setImageBitmap(imageBitmap);
        }
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