package com.example.myapplication.model;

import com.example.myapplication.controller.QuestionColor;

public class Question {
    private String mQuestionText;
    private boolean mAnswerTrue;
    private boolean mIsCheatable;
    private boolean mIsCheated;
    private QuestionColor mColor;

    public String getQuestionText() {
        return mQuestionText;
    }

    public void QuestionText(String text) {
        mQuestionText = text;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean ismIsCheatable() {
        return mIsCheatable;
    }

    public void setmIsCheatable(boolean mIsCheatable) {
        this.mIsCheatable = mIsCheatable;
    }

    public boolean ismIsCheated() {
        return mIsCheated;
    }

    public void setmIsCheated(boolean mIsCheated) {
        this.mIsCheated = mIsCheated;
    }

    public Question(String text, boolean answerTrue, boolean cheatable, boolean isCheated, String color) {
        mQuestionText = text;
        mAnswerTrue = answerTrue;
        mIsCheatable = cheatable;
        mIsCheated = isCheated;
        switch (color) {
            case "green":
                mColor = QuestionColor.GREEN;
                break;
            case "red":
                mColor = QuestionColor.RED;
                break;
            case "blue":
                mColor = QuestionColor.BLUE;
                break;
            case "black":
                mColor=QuestionColor.BLACK;
        }
    }

    public QuestionColor getmColor() {
        return mColor;
    }
}
