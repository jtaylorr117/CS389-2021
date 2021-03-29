package com.example.mixologymania;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button dateButton;
    private DatePicker picker;
    private int userAge;

    //CONSTANTS USED FOR SHARED PREFS TO LOAD USER AGE IF THEY ALREADY ENTEREED IT ON A PREVIOUS LOAD
    public static final String SHAREDPREFS = "sharedprefs";
    public static final String AGE = "userage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets up variables used by Date Picker
        picker = (DatePicker)findViewById(R.id.datePicker1);
        dateButton = (Button) findViewById(R.id.button1);

        //loads the saved user age if there is one
        loadData();

        //Changes the screen automatically if the age data was already stored
        if(userAge != -1 && userAge >= 21){
            setContentView(R.layout.activity_main_menu_screen);
        }else if(userAge != -1 && userAge < 21){
            setContentView(R.layout.activity_main2);
        }


        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // tvw.setText("Selected Date: "+ picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear());
                userAge = getAge(picker.getYear(),picker.getMonth(),picker.getDayOfMonth());

                //saves the user age so it can be loaded on startup
                saveData();

                if(userAge < 21){
                    setContentView(R.layout.activity_main2);
                }else
                {
                    setContentView(R.layout.activity_main_menu_screen);
                }
            }
        });

    }

    //Calculates the users age based on the data given in the date picker
    public static int getAge(int year, int month, int date) {

        Calendar dateOfBirth = Calendar.getInstance();

        dateOfBirth.set(Calendar.YEAR, year);
        dateOfBirth.set(Calendar.MONTH, month);
        dateOfBirth.set(Calendar.DATE, date);

        int age = 0;

        Calendar currentDate = Calendar.getInstance();

        age = currentDate.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

        if (currentDate.get(Calendar.MONTH) == dateOfBirth.get(Calendar.MONTH)) {
            if (currentDate.get(Calendar.DAY_OF_MONTH) < dateOfBirth.get(Calendar.DAY_OF_MONTH))
                age = age - 1;
        } else if (currentDate.get(Calendar.MONTH) < dateOfBirth.get(Calendar.MONTH))
            age = age - 1;
        return age;
    }


    //Saves the user's age data in shared preferences
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHAREDPREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(AGE,userAge);
        editor.apply();
    }

    //Loads the user's age data in shared preferences
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHAREDPREFS,MODE_PRIVATE);
        userAge = sharedPreferences.getInt(AGE,-1);

    }
}