package com.example.nick.countrypedia.model.restprovider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import java.io.IOException;
import java.net.URL;


public class ImageLoader extends AsyncTask<String, Void, PictureDrawable> {

    @Override
    protected PictureDrawable doInBackground(String... params) {
        URL url;
        Bitmap bmp;
        try {
            url = new URL(params[0]);
            SVG svg = SVGParser.getSVGFromInputStream(url.openConnection().getInputStream());
            return svg.createPictureDrawable();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Connection Error");
        }
    }
}
