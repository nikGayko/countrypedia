package com.example.nick.countrypedia.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nick.countrypedia.R;
import com.example.nick.countrypedia.model.ImageLoader.ImageLoader;
import com.example.nick.countrypedia.model.restprovider.Provider;
import com.example.nick.countrypedia.view.item.Country;
import com.example.nick.countrypedia.view.item.Group;
import com.example.nick.countrypedia.view.item.ListItem;

import java.util.ArrayList;
import java.util.HashMap;


class CountryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ListItem> mCountriesList;
    private View.OnClickListener mClickListener;

    private ImageLoader mImageLoader;

    CountryListAdapter(ArrayList<ListItem> countries, View.OnClickListener clickListener, Context context) {
        mClickListener = clickListener;
        mCountriesList = countries;

        mImageLoader = new ImageLoader(15);
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
                bodyView.setOnClickListener(mClickListener);
                return new BodyViewHolder(bodyView);

            case ListItem.TYPE_HEADER:
                View headView = inflater.inflate(R.layout.view_head_country, viewGroup, false);
                return new HeaderViewHolder(headView);
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ListItem.TYPE_HEADER: {
                HeaderViewHolder headView = (HeaderViewHolder) holder;
                headView.mGroupName.setText(((Group) mCountriesList.get(position)).getGroupName());
                break;
            }
            case ListItem.TYPE_BODY: {
                Country country = (Country) mCountriesList.get(position);
                final BodyViewHolder bodyView = (BodyViewHolder) holder;
                bodyView.mCapital.setText(country.getCapital());
                bodyView.mCountry.setText(country.getName());
                bodyView.mView.setTag(country.getName());
                Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        bodyView.mFlag.setImageBitmap(((Bitmap) msg.obj));
                        return false;
                    }
                });
                mImageLoader.loadImage(country.getFlag(), handler);
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

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {

        super.onDetachedFromRecyclerView(recyclerView);
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView mGroupName;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mGroupName = ((TextView) itemView.findViewById(R.id.groupName));
        }
    }

    private static class BodyViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView mCountry;
        TextView mCapital;
        ImageView mFlag;

        BodyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mCountry = ((TextView) itemView.findViewById(R.id.countryLabel));
            mCapital = ((TextView) itemView.findViewById(R.id.capitalLabel));
            mFlag = ((ImageView) itemView.findViewById(R.id.flag));
        }
    }

}