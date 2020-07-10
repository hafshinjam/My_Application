package com.example.myapplication.model;


import java.io.Serializable;

public class SettingModel implements Serializable {
    private Size textSize;
    private BackgroundColorSetting backgroundColorSetting;
    private boolean trueFalse;
    private boolean nextPrevFirstLast;
    private boolean cheatButton;

    public SettingModel() {
        this.textSize = Size.MEDIUM;
        this.backgroundColorSetting = BackgroundColorSetting.WHITE;
        this.trueFalse = true;
        this.nextPrevFirstLast = true;
        this.cheatButton = true;
    }

    public Size getTextSize() {
        return textSize;
    }

    public void setTextSize(Size textSize) {
        this.textSize = textSize;
    }

    public BackgroundColorSetting getBackgroundColorSetting() {
        return backgroundColorSetting;
    }

    public void setBackgroundColorSetting(BackgroundColorSetting backgroundColorSetting) {
        this.backgroundColorSetting = backgroundColorSetting;
    }

    public boolean isTrueFalse() {
        return trueFalse;
    }

    public void setTrueFalse(boolean trueFalse) {
        this.trueFalse = trueFalse;
    }

    public boolean isNextPrevFirstLast() {
        return nextPrevFirstLast;
    }

    public void setNextPrevFirstLast(boolean nextPrevFirstLast) {
        this.nextPrevFirstLast = nextPrevFirstLast;
    }

    public boolean isCheatButton() {
        return cheatButton;
    }

    public void setCheatButton(boolean cheatButton) {
        this.cheatButton = cheatButton;
    }
}
