package com.example.jdemo;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class ShoeBindingAdapter {

    @BindingAdapter("imageFromUrl")
    public static void bindImageFromUrl(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(imageView.getContext())
                    .asBitmap()
                    .load(imageUrl)
                    .placeholder(R.drawable.glide_placeholder)
                    .centerCrop()
                    .into(imageView);
        }
    }

    @BindingAdapter("imageTransFromUrl")
    public static void bindImageTransFromUrl(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(imageView);
        }
    }
}
