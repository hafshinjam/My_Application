package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class cheating extends AppCompatActivity {
    private boolean correctAnswer;
    private Button mCheatButton;
    private TextView mCheat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cheating);
        findAllViews();
        Intent cheatingIntent = getIntent();
        correctAnswer = cheatingIntent.getBooleanExtra("com.example.myapplication.CURRENT_CHEAT", true);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (correctAnswer)
                    mCheat.setText(R.string.button_true);
                else
                    mCheat.setText(R.string.button_false);
            }
        });

    }

    private void findAllViews() {
        mCheat = findViewById(R.id.cheatAnswer);
        mCheatButton = findViewById(R.id.cheatButton);
    }
}