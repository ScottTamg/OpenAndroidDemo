package com.anxin.changbaishan.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Txw on 2016/5/12.
 */
public class ImageLoadUtil {

    public static void loadImage(Context context, String url, ImageView imageView) {
            Glide.with(context).load(url).into(imageView);
    }

    public static void loadImage(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).into(imageView);
    }

    public static void loadImage(FragmentActivity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).into(imageView);
    }

    public static void loadImage(Fragment fragment, String url, ImageView imageView) {
        Glide.with(fragment).load(url).into(imageView);
    }

    public static void loadImage(android.support.v4.app.Fragment fragment, String url, ImageView imageView) {
        Glide.with(fragment).load(url).into(imageView);
    }
}
