package com.example.mixologymania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class main_menu_screen extends AppCompatActivity {
    private Button enterButton;
    private EditText textInput;
    private TextView displayText;
    private TextView mainText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_screen);

        enterButton = (Button) findViewById(R.id.enterButton);
        textInput = (EditText) findViewById(R.id.tvInput);
        displayText = (TextView) findViewById(R.id.tvDisplay);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getInstance().getReference();
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = textInput.getText().toString();
                displayText.setText(input);
                myRef.child("recipes").child(input).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                       // mainText.setText(task.getResult().child("ingredients").getValue().toString());
                    }
                });
            }
        });


    }

}