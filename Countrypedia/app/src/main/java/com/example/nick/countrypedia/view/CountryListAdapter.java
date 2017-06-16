package com.example.nick.countrypedia.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nick.countrypedia.Country;
import com.example.nick.countrypedia.R;

import java.util.ArrayList;


class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder> {

    private ArrayList<Country> mCountriesList;

    CountryListAdapter(ArrayList<Country> countries) {

        mCountriesList = countries;
    }

    public void setData(ArrayList<Country> countriesList) {
        mCountriesList = countriesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_country, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Country country = mCountriesList.get(position);
        holder.mCountry.setText(country.getName());
        holder.mCapital.setText(country.getCapital());
    }

    @Override
    public int getItemCount() {
        return mCountriesList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mCountry;
        TextView mCapital;

        ViewHolder(View itemView) {
            super(itemView);
            mCountry = ((TextView) itemView.findViewById(R.id.countryLabel));
            mCapital = ((TextView) itemView.findViewById(R.id.capitalLabel));
        }
    }

}