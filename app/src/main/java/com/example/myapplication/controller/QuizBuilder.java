package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class QuizBuilder extends AppCompatActivity {
    private TextView mQuestionText;
    private String questionStr;
    private Button mStartButton;
    public static final String QUESTION_TEXT_INPUT = "com.example.myapplication.questionText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_builder);
        findAllViews();
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentQuizBuilder = new Intent(QuizBuilder.this, QuizActivity.class);
                intentQuizBuilder.putExtra(QUESTION_TEXT_INPUT, mQuestionText.getText().toString());
                startActivity(intentQuizBuilder);
            }
        });
    }

    private void findAllViews() {
        mQuestionText = findViewById(R.id.questionText);
        mStartButton = findViewById(R.id.startButton);
    }


}