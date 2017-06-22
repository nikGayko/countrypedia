package com.example.nick.countrypedia.model.ImageLoader;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import com.caverock.androidsvg.SVG;

import java.util.concurrent.ExecutionException;

public class ImageLoader {

    LruCache<String, Bitmap> mLruCache;
    private final int MAX_SIZE = 20 * 1024 * 1024;

    private static ImageLoader singlton;

    private ImageLoader() {
        mLruCache = new LruCache<>(MAX_SIZE);
    }

    public static synchronized ImageLoader getLoader() {
        if(singlton == null) {
            singlton = new ImageLoader();
        }
        return singlton;
    }

    private final Object lock = new Object();

    public void drawBitmap(final String url, ImageView imageView) {
        synchronized (lock) {
            Bitmap bitmap = mLruCache.get(url);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                return;
            }
        }

        final Handler handler = new Handler();
        Thread thread = initThread(url, handler, new BitmapResultCallback(url, imageView) {
            @Override
            public void onSuccess(Bitmap bitmap) {
                synchronized (lock) {
                    if (bitmap != null) {
                        mLruCache.put(url, bitmap);
                    }
                }
                super.onSuccess(bitmap);
            }
        });

        thread.start();
    }

    private Thread initThread(final String url, final Handler handler, final OnResultCallback<Bitmap> callback) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                SVGLoader imageLoader = new SVGLoader();
                try {
                    imageLoader.execute(url);
                    SVG svg = imageLoader.get();
                    Drawable drawable = new PictureDrawable(svg.renderToPicture());
                    final Bitmap bitmap = convertToBitmap(drawable, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(bitmap);
                        }
                    });
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);

        return mutableBitmap;
    }
}
