package com.example.nick.countrypedia.model.restprovider;

import android.graphics.Bitmap;
import android.os.Handler;

import com.example.nick.countrypedia.view.item.Country;

public interface Notify {
    void sendNotify(Country country, Handler handler);
}
