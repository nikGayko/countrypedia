package com.example.nick.countrypedia.model.restprovider;

import android.graphics.Bitmap;
import android.graphics.Picture;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGAndroidRenderer;
import com.caverock.androidsvg.SVGImageView;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class ImageLoader extends AsyncTask<String, Void, SVG> {

    @Override
    protected SVG doInBackground(String... params) {
        URL url;
        Bitmap bmp;
        try {
            url = new URL(params[0]);
            InputStream is = url.openConnection().getInputStream();
            return SVG.getFromInputStream(is);
        } catch (IOException | SVGParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
