package com.example.myapplication.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.SettingActivity;
import com.example.myapplication.model.BackgroundColorSetting;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.SettingModel;

import java.util.ArrayList;

import static com.example.myapplication.SettingActivity.TEMP_SETTING;

public class QuizActivity extends AppCompatActivity {
    public static final int SETTING_ACTIVITY_REQUEST_CODE = 0;
    private Button mButtonTrue;
    private Button mButtonFalse;
    private Button mCheatButton;
    private ImageButton mSettingButton;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonLast;
    private ImageButton mButtonFirst;
    private ImageButton mResetButton;
    private TextView mTextViewQuestion;
    private TextView mScoreText;
    private String score = "GAME_SCORE";
    private String state = "QUESTION_STATE";
    private String current = "CURRENT_QUESTION";
    private String currentCheat = "com.example.myapplication.CURRENT_CHEAT";
    public static final String CURRENT_SETTING = "com.example.myapplication.controller.currentSetting";
    private boolean[] mStates = {true, true, true, true, true, true};
    private int mScore = 0;
    private String mQuestionInput;
    private int mCurrentIndex = 0;
    public static ArrayList<Question> mQuestionBank = new ArrayList<>();
    private SettingModel mSetting = new SettingModel();
    private LinearLayout mQuizLayout;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(score, mScore);
        outState.putBooleanArray(state, mStates);
        outState.putInt(current, mCurrentIndex);
        outState.putSerializable(CURRENT_SETTING, mSetting);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED && data == null)
            return;
        if (requestCode == QuizActivity.SETTING_ACTIVITY_REQUEST_CODE) {
            mSetting = (SettingModel) data.getSerializableExtra(TEMP_SETTING);
            updateView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent tempIntent = getIntent();
        mQuestionInput = tempIntent.getStringExtra(QuizBuilder.QUESTION_TEXT_INPUT);
        parseQuestions();

        //inflate: convert layout xml to actual java objects to be displayed
        setContentView(R.layout.activity_quiz);

        //it must be the first task we do after inflate
        findAllViews();
        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(score);
            mCurrentIndex = savedInstanceState.getInt(current);
            mStates = savedInstanceState.getBooleanArray(state);
            mScoreText.setText("" + mScore);
        }
        setClickListeners();

        updateQuestion();
        updateView();
    }

    //always used mandatory
    private void findAllViews() {
        mButtonTrue = findViewById(R.id.button_true);
        mButtonFalse = findViewById(R.id.button_false);
        mButtonNext = findViewById(R.id.button_next);
        mButtonPrevious = findViewById(R.id.button_previous);
        mTextViewQuestion = findViewById(R.id.text_view_question);
        mButtonFirst = findViewById(R.id.first_button);
        mButtonLast = findViewById(R.id.last_Button);
        mResetButton = findViewById(R.id.reset_button);
        mScoreText = findViewById(R.id.scores);
        mCheatButton = findViewById(R.id.cheatButton);
        mQuizLayout = findViewById(R.id.quizLayout);
        mSettingButton = findViewById(R.id.settingButton);

    }

    //change question in textView using current question in bank
    private void updateQuestion() {
        checkIfFinished();
        Question currentQuestion = mQuestionBank.get(mCurrentIndex);
        mTextViewQuestion.setText(currentQuestion.getQuestionText());
        QuestionColor temp = currentQuestion.getmColor();
        int colorTemp = 0;
        switch (temp) {
            case RED:
                colorTemp = Color.RED;
                break;
            case BLUE:
                colorTemp = Color.BLUE;
                break;
            case GREEN:
                colorTemp = Color.GREEN;
                break;
            case BLACK:
                colorTemp = Color.BLACK;
                break;
            default:
                colorTemp = Color.BLACK;
                break;
        }
        mTextViewQuestion.setTextColor(colorTemp);
        if (!mStates[mCurrentIndex]) {
            mButtonFalse.setVisibility(View.GONE);
            mButtonTrue.setVisibility(View.GONE);
        }

    }

    private void setClickListeners() {
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);


            }
        });
        mSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingIntent = new Intent(QuizActivity.this, SettingActivity.class);
                settingIntent.putExtra(CURRENT_SETTING, mSetting);
                startActivityForResult(settingIntent, SETTING_ACTIVITY_REQUEST_CODE);
            }
        });


        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < mQuestionBank.size(); i++) {
                    mStates[i] = true;
                    mCurrentIndex = 0;
                    mScore = 0;
                    mScoreText.setText("" + mScore);
                    mButtonFirst.setVisibility(View.VISIBLE);
                    mButtonLast.setVisibility(View.VISIBLE);
                    mButtonTrue.setVisibility(View.VISIBLE);
                    mButtonFalse.setVisibility(View.VISIBLE);
                    mTextViewQuestion.setVisibility(View.VISIBLE);
                    mButtonNext.setVisibility(View.VISIBLE);
                    mButtonPrevious.setVisibility(View.VISIBLE);
                    updateQuestion();
                }
                updateView();
            }
        });
        mButtonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = mQuestionBank.size() - 1;
                if (mStates[mCurrentIndex]) {
                    mButtonTrue.setVisibility(View.VISIBLE);
                    mButtonFalse.setVisibility(View.VISIBLE);
                } else {
                    mButtonTrue.setVisibility(View.GONE);
                    mButtonFalse.setVisibility(View.GONE);
                }
                updateQuestion();
                updateView();
            }
        });
        mButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = 0;
                if (mStates[mCurrentIndex]) {
                    mButtonTrue.setVisibility(View.VISIBLE);
                    mButtonFalse.setVisibility(View.VISIBLE);
                } else {
                    mButtonTrue.setVisibility(View.GONE);
                    mButtonFalse.setVisibility(View.GONE);
                }
                updateQuestion();
                updateView();
            }
        });
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (++mCurrentIndex) % mQuestionBank.size();
                if (mStates[mCurrentIndex]) {
                    mButtonTrue.setVisibility(View.VISIBLE);
                    mButtonFalse.setVisibility(View.VISIBLE);
                } else {
                    mButtonTrue.setVisibility(View.GONE);
                    mButtonFalse.setVisibility(View.GONE);
                }
                updateQuestion();
                updateView();
            }
        });

        mButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (--mCurrentIndex + mQuestionBank.size()) % mQuestionBank.size();
                if (mStates[mCurrentIndex]) {
                    mButtonTrue.setVisibility(View.VISIBLE);
                    mButtonFalse.setVisibility(View.VISIBLE);
                } else {
                    mButtonTrue.setVisibility(View.GONE);
                    mButtonFalse.setVisibility(View.GONE);
                }
                updateQuestion();
                updateView();
            }
        });
        mTextViewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (++mCurrentIndex) % mQuestionBank.size();
                if (mStates[mCurrentIndex]) {
                    mButtonTrue.setVisibility(View.VISIBLE);
                    mButtonFalse.setVisibility(View.VISIBLE);
                } else {
                    mButtonTrue.setVisibility(View.GONE);
                    mButtonFalse.setVisibility(View.GONE);
                }
                updateQuestion();
            }
        });
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizActivityIntent = new Intent(QuizActivity.this, cheating.class);
                quizActivityIntent.putExtra(currentCheat, mQuestionBank.get(mCurrentIndex).isAnswerTrue());
                startActivityForResult(quizActivityIntent, SETTING_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void updateView() {
        setBackgroundColor();
        setTextSize();
        setButtonVisibility();
    }

    private void setButtonVisibility() {
        if (!mSetting.isTrueFalse()) {
            mButtonTrue.setVisibility(View.GONE);
            mButtonFalse.setVisibility(View.INVISIBLE);
        } else {
            mButtonTrue.setVisibility(View.VISIBLE);
            mButtonFalse.setVisibility(View.VISIBLE);
        }

        if (!mSetting.isNextPrevFirstLast()) {
            mButtonNext.setVisibility(View.INVISIBLE);
            mButtonPrevious.setVisibility(View.INVISIBLE);
            mButtonFirst.setVisibility(View.INVISIBLE);
            mButtonLast.setVisibility(View.INVISIBLE);
        } else {
            mButtonPrevious.setVisibility(View.VISIBLE);
            mButtonNext.setVisibility(View.VISIBLE);
            mButtonFirst.setVisibility(View.VISIBLE);
            mButtonLast.setVisibility(View.VISIBLE);
        }

        if (!mSetting.isCheatButton()){
            mCheatButton.setVisibility(View.INVISIBLE);
        }
        else mCheatButton.setVisibility(View.VISIBLE);
    }

    private void setTextSize() {
        switch (mSetting.getTextSize()) {
            case MEDIUM:
                mTextViewQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                break;
            case LARGE:
                mTextViewQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                break;
            case SMALL:
                mTextViewQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                break;
        }
    }

    private void setBackgroundColor() {
        BackgroundColorSetting tempColor = mSetting.getBackgroundColorSetting();
        switch (tempColor) {
            case WHITE:
                mQuizLayout.setBackgroundColor(getColor(R.color.white));
                break;
            case LIGHTBLUE:
                mQuizLayout.setBackgroundColor(getColor(R.color.light_blue));
                break;
            case LIGHTRED:
                mQuizLayout.setBackgroundColor(getColor(R.color.light_red));
                break;
            case LIGHTGREEN:
                mQuizLayout.setBackgroundColor(getColor(R.color.light_green));
                break;
        }
    }

    //check current question answer and show toast.
    private void checkAnswer(boolean userPressed) {
        boolean isAnswerTrue = mQuestionBank.get(mCurrentIndex).isAnswerTrue();
        if (userPressed == isAnswerTrue) {
            Toast.makeText(this, R.string.toast_correct, Toast.LENGTH_LONG).show();
            if (!mQuestionBank.get(mCurrentIndex).ismIsCheated())
                mScore++;
            mScoreText.setText("" + mScore);
        } else {
            Toast.makeText(this, R.string.toast_incorrect, Toast.LENGTH_LONG).show();
            mScoreText.setText("" + mScore);
        }
        mStates[mCurrentIndex] = false;
        mButtonTrue.setVisibility(View.GONE);
        mButtonFalse.setVisibility(View.GONE);
        checkIfFinished();
    }

    private void checkIfFinished() {
        boolean tempCondition = false;
        for (int i = 0; i < mQuestionBank.size(); i++) {
            if (mStates[i]) {
                tempCondition = true;
                break;
            }
        }
        if (tempCondition == false) {
            mButtonFirst.setVisibility(View.GONE);
            mButtonLast.setVisibility(View.GONE);
            mButtonTrue.setVisibility(View.GONE);
            mButtonFalse.setVisibility(View.GONE);
            mTextViewQuestion.setVisibility(View.GONE);
            mButtonNext.setVisibility(View.GONE);
            mButtonPrevious.setVisibility(View.GONE);
            mScoreText.setGravity(View.TEXT_ALIGNMENT_CENTER);
        }
    }

    private void parseQuestions() {
        String tempQuestion;
        Question temp;
        String tempAnswer;
        boolean tempCheated = false;
        String tempCheatable;
        String tempColor;
        String tempStr = mQuestionInput;
        while (tempStr.contains(",")) {
            tempQuestion = tempStr.substring(tempStr.indexOf("{") + 1, tempStr.indexOf("}"));
            tempStr = tempStr.substring(tempStr.indexOf("}") + 1);
            tempAnswer = tempStr.substring(tempStr.indexOf("{") + 1, tempStr.indexOf("}"));
            tempStr = tempStr.substring(tempStr.indexOf("}") + 1);
            tempCheatable = tempStr.substring(tempStr.indexOf("{") + 1, tempStr.indexOf("}"));
            tempStr = tempStr.substring(tempStr.indexOf("}") + 1);
            tempColor = tempStr.substring(tempStr.indexOf("{") + 1, tempStr.indexOf("}"));
            tempStr = tempStr.substring(tempStr.indexOf("}") + 1);
            if (tempAnswer.equalsIgnoreCase("true") && tempCheatable.equalsIgnoreCase("true"))
                temp = new Question(tempQuestion, true, true, tempCheated, tempColor);
            else if (tempAnswer.equalsIgnoreCase("false") && tempCheatable.equalsIgnoreCase("true"))
                temp = new Question(tempQuestion, false, true, tempCheated, tempColor);
            else if (tempAnswer.equalsIgnoreCase("true") && tempCheatable.equalsIgnoreCase("false"))
                temp = new Question(tempQuestion, true, false, tempCheated, tempColor);
            else
                temp = new Question(tempQuestion, false, false, tempCheated, tempColor);
            mQuestionBank.add(temp);
        }
    }

}