package com.example.mixologymania;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
    private TextView tValidityCheck;
    private Button mButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private int userAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button);

        tValidityCheck= (TextView)findViewById(R.id.tvValidity);

        mButton.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view) {
               Calendar cal = Calendar.getInstance();
               int year = cal.get(Calendar.YEAR);
               int month = cal.get(Calendar.MONTH);
               int day = cal.get(Calendar.DAY_OF_MONTH);

               DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo, mDateSetListener, year, month, day);

               //Makes date picker window transparent
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               dialog.show();
           }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                userAge = getAge(year,month,dayOfMonth);
                if(userAge < 21){
                    tValidityCheck.setText("You are NOT 21 or older!");
                }else{
                    tValidityCheck.setText("You are at least 21 years old!");
                }
            }
        };

    }
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
}