package com.code.wlu.abdulrahman.myapplication;

import static android.app.PendingIntent.getActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.code.wlu.abdulrahman.myapplication.databinding.ActivityTestToolbarBinding;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;

    private static String ACTIVITY_NAME="TestToolbar";
    EditText messageDialog;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        messageDialog=findViewById(R.id.messageDialog);

//        setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.emailSnackString, Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    public boolean onCreateOptionsMenu (Menu m)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem mi)
    {
        SharedPreferences prefs = getSharedPreferences("dialog_Messages",0);
        String defaultDialogMessage= prefs.getString("DialogMessage",getResources().getString(R.string.toolbarMenuSnackItem1));
//        int id=mi.getItemId();
//        switch (id){
//            case R.id.action_one:
//
//        }
        if(mi.getItemId() == R.id.action_one) {
            Log.d("Toolbar", "Option 1 selected");
            Snackbar.make(findViewById(R.id.action_one), defaultDialogMessage, Snackbar.LENGTH_LONG).show();
        }
        else if (mi.getItemId() == R.id.action_two) {
            Log.d("Toolbar", "Option 2 selected");
            AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
            builder.setTitle(R.string.toolbarMenuItem2Dialog);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else if (mi.getItemId() == R.id.action_three) {
            Log.d("Toolbar", "Option 3 selected");
            AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
            LayoutInflater inflater = TestToolbar.this.getLayoutInflater();
            final View dialogView=inflater.inflate(R.layout.message_dialog, null);
            builder.setView(dialogView)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            messageDialog=dialogView.findViewById(R.id.messageDialog);
                            message= messageDialog.getText().toString();
                            SharedPreferences.Editor editor=prefs.edit();
                            editor.putString("DialogMessage",message);
                            editor.commit();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
//                            LoginDialogFragment.this.getDialog().cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else if (mi.getItemId() == R.id.dropdown_menu) {
            Log.d("Toolbar", "Settings selected");
            Toast.makeText(this, getResources().getString(R.string.version)+" "+getResources().getString(R.string.devName), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    protected static boolean validateDialogInput(String input)
    {
        if(!input.isEmpty())
        {
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean validateActivityName(String actName)
    {
        return actName.equals(ACTIVITY_NAME);
    }
}