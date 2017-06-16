package com.example.nick.countrypedia.view;

import android.widget.CheckBox;

import java.util.HashSet;

class CheckBoxGroup {

    private HashSet<CheckBox> mCheckBoxSet;
    private int mSelectedCount;

    CheckBoxGroup() {
        mCheckBoxSet = new HashSet<>();
    }


    void addCheckBox(CheckBox checkBox) {
        mCheckBoxSet.add(checkBox);
        if(checkBox.isChecked()) {
            incrementCount();
        }
    }

    void removeCheckBox(CheckBox checkBox) {
        mCheckBoxSet.remove(checkBox);
        if(checkBox.isChecked()) {
            decrementCount();
        }
    }

    void updateSelectedCount() {
        int updatedCount = 0;
        for (CheckBox checkBox :
                mCheckBoxSet) {
            if (checkBox.isChecked()) {
                updatedCount++;
            }
        }
        if(updatedCount == 1) {
            blockCheckedLast();
        } else {
            unblockAll();
        }
        mSelectedCount = updatedCount;
    }

    void incrementCount() {
        mSelectedCount++;
        if (mSelectedCount > 1) {
            unblockAll();
        } else {
            blockCheckedLast();
        }
    }

    void decrementCount() {
        mSelectedCount--;
        if(mSelectedCount == 1) {
            blockCheckedLast();
        }

    }

    private void unblockAll() {

        for (CheckBox checkBox :
                mCheckBoxSet) {
            if (!checkBox.isEnabled()) {
                checkBox.setEnabled(true);
                break;
            }
        }
    }

    private void blockCheckedLast() {
        for (CheckBox checkBox :
                mCheckBoxSet) {
            if (checkBox.isChecked()) {
                checkBox.setEnabled(false);
            }
        }
    }
}
