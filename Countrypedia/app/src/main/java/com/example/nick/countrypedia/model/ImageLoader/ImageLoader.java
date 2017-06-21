package com.example.nick.countrypedia.model.ImageLoader;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Handler;

import com.caverock.androidsvg.SVG;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class ImageLoader {

    LinkedList<Thread> mThreadList;
    private final int CAPACITY;

    public ImageLoader(int capacity) {
        CAPACITY = capacity;
        mThreadList = new LinkedList<>();
    }

    public void loadImage(final String url, final Handler handler) {
        if (mThreadList.size() == CAPACITY) {
            Thread slow = mThreadList.getLast();
            boolean alive = slow.isAlive();
            slow.interrupt();
            mThreadList.removeLast();
        }
        Thread thread = initThread(url, handler);
        mThreadList.addFirst(thread);
        thread.start();
    }

    private Thread initThread(final String url, final Handler handler) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                SVGLoader imageLoader = new SVGLoader();
                try {
                    imageLoader.execute(url);
                    SVG svg = imageLoader.get();
                    Drawable drawable = new PictureDrawable(svg.renderToPicture());
                    Bitmap bitmap = convertToBitmap(drawable, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                    if (Thread.currentThread().isInterrupted())
                        return;

                    handler.obtainMessage(1, bitmap).sendToTarget();

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
