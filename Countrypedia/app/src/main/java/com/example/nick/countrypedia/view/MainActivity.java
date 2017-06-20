package com.example.nick.countrypedia.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.nick.countrypedia.view.item.Country;
import com.example.nick.countrypedia.Notify;
import com.example.nick.countrypedia.R;
import com.example.nick.countrypedia.model.SettingsManager;
import com.example.nick.countrypedia.model.StateManager;
import com.example.nick.countrypedia.view.item.ListItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Notify {

    StateManager mStateManager;

    RecyclerView mRecyclerView;
    CountryListAdapter mCountryListAdapter;
    ProgressBar mProgressBar;
    EditText mSearchField;

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String searchQuery = s.toString();
            if(!searchQuery.equals("")) {
                searchQuery = searchQuery.substring(0, 1).toUpperCase() + searchQuery.substring(1);
                ArrayList<Country> search = mStateManager.search(mStateManager.getCountriesList(), searchQuery);
                mRecyclerView.setAdapter(new CountryListAdapter(new ArrayList<ListItem>(search)));
            } else {
                mRecyclerView.setAdapter(mCountryListAdapter);
            }
        }
    };

//    private void updateRecyclerData(ArrayList<Country> countries) {
//        mSearchListAdapter.setData();
//        mSearchListAdapter.notifyDataSetChanged();
//        mRecyclerView.scrollToPosition(0);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SettingsManager.getInstance().initialization(this);

        mStateManager = StateManager.getInstance();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = ((ProgressBar) findViewById(R.id.progressBar));
        mSearchField = ((EditText) findViewById(R.id.searchField));

        mStateManager.updateCountryList(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mSearchField.addTextChangedListener(mTextWatcher);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void sendNotify(ArrayList<ListItem> listItems) {
        showList();
        mCountryListAdapter = new CountryListAdapter(listItems);
        mRecyclerView.setAdapter(mCountryListAdapter);
    }

    public void showList() {
        mSearchField.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
