package com.example.nick.countrypedia.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.example.nick.countrypedia.R;
import com.example.nick.countrypedia.model.SettingsManager;
import com.example.nick.countrypedia.model.StateManager;
import com.example.nick.countrypedia.model.parameter.DisplayParameter;
import com.example.nick.countrypedia.model.parameter.SearchParameter;

public class SettingsActivity extends AppCompatActivity {

    CheckBoxGroup mCheckBoxGroup;
    CheckBox mCountryCheck;
    CheckBox mCapitalCheck;

    RadioButton mRegionRadio;
    RadioButton mAlphabetRadio;

    SettingsManager mSettingsManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSettingsManager = SettingsManager.getInstance();

        mCheckBoxGroup = new CheckBoxGroup();

        mCountryCheck = ((CheckBox) findViewById(R.id.countryCheck));
        mCapitalCheck = ((CheckBox) findViewById(R.id.capitalCheck));

        mRegionRadio = ((RadioButton) findViewById(R.id.regionRadio));
        mAlphabetRadio = ((RadioButton) findViewById(R.id.alphabetRadio));



        initialization();
    }

    private void initialization() {
        boolean searchCountry = mSettingsManager.isSearchCountry();
        boolean searchCapital = mSettingsManager.isSearchCapital();

        mCountryCheck.setChecked(searchCountry);
        mCapitalCheck.setChecked(searchCapital);

        mCheckBoxGroup.addCheckBox(mCountryCheck);
        mCheckBoxGroup.addCheckBox(mCapitalCheck);

//        checkBoxClicked(SearchParameter.BY_COUNTRY, searchCountry);
//        checkBoxClicked(SearchParameter.BY_CAPITAL, searchCapital);

        switch (mSettingsManager.getDisplayParameter()) {
            case REGION:
                mRegionRadio.setChecked(true);
                break;
            case ALPHABET:
                mAlphabetRadio.setChecked(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public void radioButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.regionRadio:
                mSettingsManager.setDisplayParameter(DisplayParameter.REGION, this);
                mAlphabetRadio.setChecked(false);
                break;
            case R.id.alphabetRadio:
                mSettingsManager.setDisplayParameter(DisplayParameter.ALPHABET, this);
                mRegionRadio.setChecked(false);
                break;
        }
    }

    public void countryCheckClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        mSettingsManager.setSearchCountry(checked, this);
        checkBoxChangeValue(SearchParameter.BY_COUNTRY, checked);
    }

    public void capitalCheckClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        mSettingsManager.setSearchCapital(checked, this);
        checkBoxChangeValue(SearchParameter.BY_CAPITAL, checked);
    }

    private void checkBoxChangeValue(SearchParameter searchParameter, boolean value) {
        if (value) {
            mCheckBoxGroup.incrementCount();
        } else {
            mCheckBoxGroup.decrementCount();
        }
    }
}
