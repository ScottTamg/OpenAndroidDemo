package com.anxin.changbaishan.widget.writepad;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.anxin.changbaishan.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Txw on 2016/4/14.
 */
public class WritePadDialog extends Dialog {
    private static final int BACKGROUND_COLOR = Color.WHITE;
    private static final int BRUSH_COLOR = Color.BLACK;
    @Bind(R.id.tablet_view)
    FrameLayout mTabletView;
    @Bind(R.id.tablet_ok)
    Button mTabletOk;
    @Bind(R.id.tablet_clear)
    Button mTabletClear;
    @Bind(R.id.tablet_cancel)
    Button mTabletCancel;

    private Context mContext;
    private WindowManager.LayoutParams mParams;
    private DialogListener mListener;
    private SignatureView mView;

    public WritePadDialog(Context context, DialogListener dialogListener) {
        super(context);
        this.mContext = context;
        this.mListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.layout_write_pad);
        ButterKnife.bind(this);

        //获取屏幕的宽高
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        mParams = getWindow().getAttributes();
        mParams.height = (int) (metrics.heightPixels * 0.9);
        mParams.width = (int) (metrics.widthPixels);
        getWindow().setAttributes(mParams);

        mView = new SignatureView(mContext, null);
        mTabletView.setVisibility(View.VISIBLE);
        mTabletView.addView(mView);
        mView.requestFocus();
    }

    @OnClick({R.id.tablet_ok, R.id.tablet_clear, R.id.tablet_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tablet_ok:
                mListener.refreshActivity(mView.getBitmap());
                cancel();
                break;
            case R.id.tablet_clear:
                mView.clear();
                break;
            case R.id.tablet_cancel:
                cancel();
                break;
        }
    }

}
