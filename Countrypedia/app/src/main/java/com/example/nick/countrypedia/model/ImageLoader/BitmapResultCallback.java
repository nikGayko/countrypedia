package com.example.nick.countrypedia.model.ImageLoader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class BitmapResultCallback implements OnResultCallback<Bitmap> {

    private String value;
    private final WeakReference<ImageView> mImageViewReference;

    public BitmapResultCallback(String value, ImageView imageView) {
        this.value = value;
        mImageViewReference = new WeakReference<ImageView>(imageView);
        imageView.setTag(value);
    }

    @Override
    public void onSuccess(Bitmap bitmap) {
        ImageView imageView = this.mImageViewReference.get();
        if (imageView != null) {
            Object tag = imageView.getTag();
            if (tag != null && tag.equals(value)) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
