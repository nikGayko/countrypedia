package com.example.nick.countrypedia.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.nick.countrypedia.Notify;
import com.example.nick.countrypedia.R;
import com.example.nick.countrypedia.StateManager;

public class MainActivity extends AppCompatActivity implements Notify{

    StateManager mStateManager;

    RecyclerView mRecyclerView;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStateManager = StateManager.getInstance();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = ((ProgressBar) findViewById(R.id.progressBar));

        mStateManager.updateCountryList(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

    }

    @Override
    public void sendNotify() {
        mRecyclerView.setAdapter(new CountryListAdapter(mStateManager.getCountriesList()));
    }
}
