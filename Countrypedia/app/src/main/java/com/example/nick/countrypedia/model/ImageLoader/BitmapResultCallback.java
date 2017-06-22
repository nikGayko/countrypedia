package com.example.nick.countrypedia.model.ImageLoader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class BitmapResultCallback implements OnResultCallback<Bitmap> {

    private String value;
    private final WeakReference<ImageView> mImageViewReference;

    public BitmapResultCallback(String value, final ImageView imageView) {
        this.value = value;
        imageView.setTag(value);
        mImageViewReference = new WeakReference<>(imageView);
    }

    private final Object lock = new Object();

    @Override
    public void onSuccess(Bitmap bitmap) {
        synchronized (lock) {
            ImageView imageView = this.mImageViewReference.get();
            if (imageView != null) {
                Object tag = imageView.getTag();
                if (tag != null && tag.equals(value)) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
