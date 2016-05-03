package com.anxin.changbaishan.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.view.base.SwipeBackActivity;
import com.anxin.changbaishan.widget.swipebacklayout.SwipeBackLayout;
import com.anxin.changbaishan.widget.writepad.DialogListener;
import com.anxin.changbaishan.widget.writepad.WritePadDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AutographActivity extends SwipeBackActivity {

    @Bind(R.id.img_sign)
    ImageView mImgSign;
    @Bind(R.id.btn_sign)
    Button mBtnSign;

    private Bitmap mSignBitmap;
    private String mSignPath;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autograph);
        ButterKnife.bind(this);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

    }

    @OnClick(R.id.btn_sign)
    public void onClick() {
        WritePadDialog dialog = new WritePadDialog(AutographActivity.this, new DialogListener() {
            @Override
            public void refreshActivity(Bitmap bitmap) {
                mSignBitmap = bitmap;
                mSignPath = createFile();
                mImgSign.setImageBitmap(bitmap);
                mImgSign.setVisibility(View.VISIBLE);
            }
        });

        dialog.show();
    }

    private String createFile() {
        ByteArrayOutputStream outputStream = null;
        String path = null;

        try {
            String dir = Environment.getExternalStorageDirectory().getPath() + "/AnxinCrashTest/";
            path = dir + System.currentTimeMillis() + ".jpg";
            Log.i("Path", path);
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return "";
            }

            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            outputStream = new ByteArrayOutputStream();
            mSignBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            if (imageBytes != null) {
                File imgFile = new File(path);
                new FileOutputStream(imgFile).write(imageBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return path;
    }
}
