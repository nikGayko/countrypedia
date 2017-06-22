package com.example.nick.countrypedia.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.nick.countrypedia.NotifyObject;
import com.example.nick.countrypedia.R;
import com.example.nick.countrypedia.model.ImageLoader.ImageLoader;
import com.example.nick.countrypedia.model.restprovider.Provider;
import com.example.nick.countrypedia.view.item.Country;

import java.text.Normalizer;

public class CountryActivity extends AppCompatActivity implements NotifyObject{

    ImageView mFlag;
    EditText mCountry;
    EditText mCapital;
    EditText mRegion;
    EditText mSubRegion;
    EditText mPopulation;
    EditText mArea;
    EditText mCurrency;
    EditText mLanguage;

    ProgressBar mProgressBar;
    ScrollView mScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        final String countryName = getIntent().getStringExtra("id");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        mFlag = ((ImageView) findViewById(R.id.image_flag));
        mCountry = (EditText) findViewById(R.id.country);
        mCapital = ((EditText) findViewById(R.id.capital));
        mRegion  = ((EditText) findViewById(R.id.region));
        mSubRegion = ((EditText) findViewById(R.id.sub_region));
        mPopulation = ((EditText) findViewById(R.id.population));
        mArea = ((EditText) findViewById(R.id.area));
        mCurrency = ((EditText) findViewById(R.id.currency));
        mLanguage = ((EditText) findViewById(R.id.language));

        mProgressBar = ((ProgressBar) findViewById(R.id.progress));
        mScrollView = ((ScrollView) findViewById(R.id.scroll));

        mScrollView.setVisibility(View.INVISIBLE);

        final Handler handler = new Handler();


        new Thread(new Runnable() {
            @Override
            public void run() {
                final Country country = new Provider().getCountry(countryName);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        sendNotify(country);
                    }
                });

            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void sendNotify(Country country) {
        displayCountry(country);
    }

    private void displayCountry(Country country) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mScrollView.setVisibility(View.VISIBLE);

        ImageLoader.getLoader().drawBitmap(country.getFlag(), mFlag);
        mCountry.setText(country.getName());
        mCapital.setText(country.getCapital());
        mRegion.setText(country.getRegion());
        mSubRegion.setText(country.getSubRegion());
        mPopulation.setText(Formatter.seperate(country.getPopulation(), 3, ' '));
        mArea.setText(Formatter.seperate(country.getArea(), 3, ' ') + " km²");
        mCurrency.setText(country.getCurrency());
        mLanguage.setText(country.getLanguage());

    }
}
