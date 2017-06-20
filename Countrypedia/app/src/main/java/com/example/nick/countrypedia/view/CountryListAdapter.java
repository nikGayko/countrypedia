package com.example.nick.countrypedia.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nick.countrypedia.R;
import com.example.nick.countrypedia.view.item.Country;
import com.example.nick.countrypedia.view.item.Group;
import com.example.nick.countrypedia.view.item.ListItem;

import java.util.ArrayList;


class CountryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ListItem> mCountriesList;

    CountryListAdapter(ArrayList<ListItem> countries) {

        mCountriesList = countries;
    }

    public void setData(ArrayList<ListItem> countriesList) {
        mCountriesList = countriesList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case ListItem.TYPE_BODY:
                View bodyView = inflater.inflate(R.layout.view_country, viewGroup, false);
                return new BodyViewHolder(bodyView);

            case ListItem.TYPE_HEADER:
                View headView = inflater.inflate(R.layout.view_head_country, viewGroup, false);
                return new HeaderViewHolder(headView);
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ListItem.TYPE_HEADER: {
                HeaderViewHolder headView = (HeaderViewHolder) holder;
                headView.mGroupName.setText(((Group) mCountriesList.get(position)).getGroupName());
                break;
            }
            case ListItem.TYPE_BODY: {
                Country country = (Country) mCountriesList.get(position);
                BodyViewHolder bodyView = (BodyViewHolder) holder;
                bodyView.mCapital.setText(country.getCapital());
                bodyView.mCountry.setText(country.getName());
                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public int getItemCount() {
        return mCountriesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mCountriesList.get(position).getType();
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView mGroupName;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mGroupName = ((TextView) itemView.findViewById(R.id.groupName));
        }
    }

    private static class BodyViewHolder extends RecyclerView.ViewHolder {

        TextView mCountry;
        TextView mCapital;

        BodyViewHolder(View itemView) {
            super(itemView);
            mCountry = ((TextView) itemView.findViewById(R.id.countryLabel));
            mCapital = ((TextView) itemView.findViewById(R.id.capitalLabel));
        }
    }

}