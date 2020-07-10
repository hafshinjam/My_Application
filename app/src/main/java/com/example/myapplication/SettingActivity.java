package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.myapplication.model.BackgroundColorSetting;
import com.example.myapplication.model.SettingModel;
import com.example.myapplication.model.Size;

import static com.example.myapplication.controller.QuizActivity.CURRENT_SETTING;

public class SettingActivity extends AppCompatActivity {
    private RadioGroup mSize;
    private RadioGroup mBackgroundColor;
    private RadioButton mColor;
    private CheckBox mCheatCheck;
    private CheckBox mNextPrevFirstLastCheck;
    private CheckBox mTrueFalseCheck;
    private RadioButton mSizeRadio;
    private TextView mSettingText;
    private TextView mSizeText;
    private Button mBackButton;
    private SettingModel settingModel = new SettingModel();
    public static String TEMP_SETTING = "tempSetting";
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        findAllViews();
        Intent receiveIntent = getIntent();
        settingModel = (SettingModel) receiveIntent.getSerializableExtra(CURRENT_SETTING);
        setPrecondition();

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectSizeId = mSize.getCheckedRadioButtonId();
                int selectColorId = mBackgroundColor.getCheckedRadioButtonId();
                mSizeRadio = findViewById(selectSizeId);
                mBackButton = findViewById(selectSizeId);
                switch (selectSizeId) {
                    case R.id.smallRadio:
                        settingModel.setTextSize(Size.SMALL);
                        break;
                    case R.id.mediumRadio:
                        settingModel.setTextSize(Size.MEDIUM);
                        break;
                    case R.id.largeRadio:
                        settingModel.setTextSize(Size.LARGE);
                        break;
                }
                switch (selectColorId) {
                    case R.id.whiteRadioButton:
                        settingModel.setBackgroundColorSetting(BackgroundColorSetting.WHITE);
                        break;
                    case R.id.lightBlueRadioButton:
                        settingModel.setBackgroundColorSetting(BackgroundColorSetting.LIGHTBLUE);
                        break;
                    case R.id.lightRedRadioButton:
                        settingModel.setBackgroundColorSetting(BackgroundColorSetting.LIGHTRED);
                        break;
                    case R.id.lightGreenRadioButton:
                        settingModel.setBackgroundColorSetting(BackgroundColorSetting.LIGHTGREEN);
                        break;
                }
                if (!mCheatCheck.isChecked())
                    settingModel.setCheatButton(false);
                else
                    settingModel.setCheatButton(true);

                if (!mTrueFalseCheck.isChecked())
                    settingModel.setTrueFalse(false);
                else
                    settingModel.setTrueFalse(true);

                if (!mNextPrevFirstLastCheck.isChecked())
                    settingModel.setNextPrevFirstLast(false);
                else
                    settingModel.setNextPrevFirstLast(true);

                Intent intent = new Intent();
                intent.putExtra(TEMP_SETTING, settingModel);
                setResult(RESULT_OK, intent);
                finish();

            }
        });


    }

    private void setPrecondition() {
        switch (settingModel.getTextSize()) {
            case SMALL:
                mSize.check(R.id.smallRadio);
                break;
            case LARGE:
                mSize.check(R.id.largeRadio);
                break;
            case MEDIUM:
                mSize.check(R.id.mediumRadio);
        }
        switch (settingModel.getBackgroundColorSetting()) {
            case LIGHTGREEN:
                mBackgroundColor.check(R.id.lightGreenRadioButton);
                break;
            case LIGHTRED:
                mBackgroundColor.check(R.id.lightRedRadioButton);
                break;
            case LIGHTBLUE:
                mBackgroundColor.check(R.id.lightBlueRadioButton);
                break;
            case WHITE:
                mBackgroundColor.check(R.id.whiteRadioButton);
                break;

        }
        if (settingModel.isCheatButton())
            mCheatCheck.setChecked(true);
        else
            mCheatCheck.setChecked(false);

        if (settingModel.isNextPrevFirstLast())
            mNextPrevFirstLastCheck.setChecked(true);
        else
            mNextPrevFirstLastCheck.setChecked(false);

        if (settingModel.isTrueFalse())
            mTrueFalseCheck.setChecked(true);
        else
            mTrueFalseCheck.setChecked(false);
    }

    @Override
    public void onBackPressed() {

    }

    private void findAllViews() {
        mSize = findViewById(R.id.SizeRadioGroup);
        mSettingText = findViewById(R.id.settingText);
        mSizeText = findViewById(R.id.sizeTextRadio);
        mBackButton = findViewById(R.id.backButtonSetting);
        mBackgroundColor = findViewById(R.id.ColorRadioGroup);
        mTrueFalseCheck = findViewById(R.id.trueFalseDisableCheck);
        mNextPrevFirstLastCheck = findViewById(R.id.nextPrevFirstLastDisableCheck);
        mCheatCheck = findViewById(R.id.cheatDisableCheck);
    }
}