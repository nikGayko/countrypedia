package com.example.nick.countrypedia.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.example.nick.countrypedia.R;
import com.example.nick.countrypedia.model.SearchParameter;
import com.example.nick.countrypedia.model.StateManager;

public class SettingsActivity extends AppCompatActivity {

    CheckBoxGroup mCheckBoxGroup;
    CheckBox mCountryCheck;
    CheckBox mCapitalCheck;

    RadioButton mRegionRadio;
    RadioButton mAlphabetRadio;

    StateManager mStateManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mStateManager = StateManager.getInstance();

        mCheckBoxGroup = new CheckBoxGroup();

        mCountryCheck = ((CheckBox) findViewById(R.id.countryCheck));
        mCapitalCheck = ((CheckBox) findViewById(R.id.capitalCheck));

        mCheckBoxGroup.addCheckBox(mCountryCheck);
        mCheckBoxGroup.addCheckBox(mCapitalCheck);

        mRegionRadio = ((RadioButton) findViewById(R.id.regionRadio));
        mAlphabetRadio = ((RadioButton) findViewById(R.id.alphabetRadio));


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public void radioButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.regionRadio:
                mAlphabetRadio.setChecked(false);
                break;
            case R.id.alphabetRadio:
                mRegionRadio.setChecked(false);
                break;
        }
    }

    public void checkBoxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            mCheckBoxGroup.incrementCount();
            mStateManager.addSearchParameter(getSearchParameterById(checkBox.getId()));
        } else {
            mCheckBoxGroup.decrementCount();
            mStateManager.removeSearchParameter(getSearchParameterById(checkBox.getId()));
        }
    }

    private SearchParameter getSearchParameterById(@IdRes int id) {
        switch (id) {
            case R.id.capitalCheck:
                return SearchParameter.BY_CAPITAL;
            case R.id.countryCheck:
                return SearchParameter.BY_COUNTRY;
            default:
                return null;
        }
    }
}
