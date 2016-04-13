package com.anxin.changbaishan.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anxin.changbaishan.R;

/**
 * Created by Txw on 2016/4/8.
 */
public class ToastView {
    private Context mContext;
    private View mLayout;
    private int mGravity;

    public ToastView(Context context, int gravity, boolean isImg, String text1, String text2) {
        this.mContext = context;
        this.mGravity = gravity;
        LayoutInflater inflater = LayoutInflater.from(context);
        mLayout = inflater.inflate(R.layout.toast, null);
        TextView textView1 = (TextView) mLayout.findViewById(R.id.textview1);
        TextView textView2 = (TextView) mLayout.findViewById(R.id.textview2);
        ImageView imageView = (ImageView) mLayout.findViewById(R.id.gantanhao_img);
        textView1.setText(text1);
        if (text2 == null) {
            textView2.setVisibility(View.GONE);
        } else {
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(text2);
        }
        if (!isImg) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    public void show() {
        Toast toast = new Toast(mContext.getApplicationContext());
        toast.setGravity(mGravity, 0, 120);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(mLayout);
        toast.show();
    }

    public void showLong() {
        Toast toast = new Toast(mContext.getApplicationContext());
        toast.setGravity(mGravity, 0, 120);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(mLayout);
        toast.show();
    }

}
