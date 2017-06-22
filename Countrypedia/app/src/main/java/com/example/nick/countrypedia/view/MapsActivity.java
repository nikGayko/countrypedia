package com.example.nick.countrypedia.view;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nick.countrypedia.NotifyObject;
import com.example.nick.countrypedia.R;
import com.example.nick.countrypedia.model.ImageLoader.ImageLoader;
import com.example.nick.countrypedia.model.restprovider.Field;
import com.example.nick.countrypedia.model.restprovider.Provider;
import com.example.nick.countrypedia.view.item.Country;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, NotifyObject {

    CardView mCardView;
    TextView mCountry;
    TextView mCapital;
    ImageView mFlag;

    View.OnClickListener mItemClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), CountryActivity.class);
            intent.putExtra("id", ((TextView) v.findViewById(R.id.countryLabel)).getText().toString());
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mCardView = ((CardView) findViewById(R.id.card_view));
        mCountry = ((TextView) findViewById(R.id.countryLabel));
        mCapital = ((TextView) findViewById(R.id.capitalLabel));
        mFlag = ((ImageView) findViewById(R.id.flag));

        mCardView.setVisibility(View.INVISIBLE);
        mCardView.setOnClickListener(mItemClicked);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private GoogleMap mMap;
    Geocoder mGeocoder;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mGeocoder = new Geocoder(this, Locale.getDefault());

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                addMarker(latLng);
            }
        });

    }

    Marker mMarker;

    private void addMarker(LatLng latlng) {
        if (mMarker != null) {
            mMarker.remove();
        }

        Address address = null;
        try {
            List<Address> fromLocation = mGeocoder.getFromLocation(latlng.latitude, latlng.longitude, 1);
            address = fromLocation.get(0);
        } catch (IOException | IndexOutOfBoundsException e) {
            e.printStackTrace();
            setCardVisibility(View.INVISIBLE);
        }

        if (address != null) {
            final Handler handler = new Handler();

            final Address finalAddress = address;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Country country = new Provider()
                            .getCountry(finalAddress.getCountryCode(),
                                    Field.FLAG, Field.CAPITAL, Field.NAME, Field.REGION);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            sendNotify(country);
                        }
                    });

                }
            }).start();
        }

        mMarker = mMap.addMarker(new MarkerOptions().position(latlng).title(""));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
    }

    private void showCard(Country country) {
        setCardVisibility(View.VISIBLE);

        mCountry.setText(country.getName());
        mCapital.setText(country.getCapital());
        mFlag.setImageBitmap(null);
        ImageLoader.getLoader().drawBitmap(country.getFlag(), mFlag);
    }

    @Override
    public void sendNotify(Country country) {
        if (country != null) {
            showCard(country);
        } else {
            setCardVisibility(View.INVISIBLE);
        }
    }

    private void setCardVisibility(int param) {
        switch (param) {
            case View.VISIBLE:
                if (mCardView.getVisibility() != View.VISIBLE) {
                    mCardView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.card_anim_up));
                }
                mCardView.setVisibility(View.VISIBLE);
                break;
            case View.INVISIBLE:
                if (mCardView.getVisibility() != View.INVISIBLE) {
                    mCardView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.card_anim_down));
                }
                mCardView.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
