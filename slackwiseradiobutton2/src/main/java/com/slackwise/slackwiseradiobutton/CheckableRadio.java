package com.slackwise.slackwiseradiobutton;

import android.view.View;
import android.widget.Checkable;


public interface CheckableRadio extends Checkable {
    void addOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener);
    void removeOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener);

    public static interface OnCheckedChangeListener {
        void onCheckedChanged(View buttonView, boolean isChecked);
    }
}