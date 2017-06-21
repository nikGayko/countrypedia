package com.example.nick.countrypedia.model.ImageLoader;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

class SVGLoader extends AsyncTask<String, Void, SVG> {

    @Override
    protected SVG doInBackground(String... params) {
        URL url;
        Bitmap bmp;
        try {
            url = new URL(params[0]);
            InputStream is = url.openConnection().getInputStream();
            return SVG.getFromInputStream(is);
        } catch (IOException | SVGParseException ignored) {
            ignored.printStackTrace();
            return null;
        }
    }
}
