package com.example.nick.countrypedia.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nick.countrypedia.R;
import com.example.nick.countrypedia.model.StateManager;

public class CountryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        String countryName = getIntent().getStringExtra("id");

    }
}
