package com.anxin.changbaishan.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.anxin.changbaishan.R;

/**
 * Created by Txw on 2016/4/7.
 */
public class CustomAlertDialog {

    private AlertDialog dialog;

    /**
     * 只有一个按钮，不需要回调
     *
     * @param context
     * @param btnText
     * @param message
     */
    public CustomAlertDialog(Context context, String btnText, String message) {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.alertdialog_onebutton);
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        tv_message.setText(message);
        TextView tv_button = (TextView) window.findViewById(R.id.tv_button);
        tv_button.setText(btnText);
        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 只有一个按钮，需要回调
     *
     * @param context
     * @param btnText
     * @param message
     * @param confirm
     */
    public CustomAlertDialog(Context context, String btnText, String message, final InterfaceConfirm confirm) {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.alertdialog_onebutton);
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        tv_message.setText(message);
        TextView tv_button = (TextView) window.findViewById(R.id.tv_button);
        tv_button.setText(btnText);
        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (confirm != null) {
                    confirm.confirm();
                }
            }
        });
    }

    /**
     * 两个按钮，都有回调
     *
     * @param context
     * @param btnLeft
     * @param btnRight
     * @param message
     * @param confirm
     * @param cancel
     */
    public CustomAlertDialog(Context context, String btnLeft, String btnRight, String message, final InterfaceConfirm confirm, final InterfaceCancel cancel) {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.alertdialog_doublebutton);
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        tv_message.setText(message);
        TextView tv_button_left = (TextView) window.findViewById(R.id.tv_button_left);
        tv_button_left.setText(btnLeft);
        tv_button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (cancel != null) {
                    cancel.cancel();
                }
            }
        });
        TextView tv_button_right = (TextView) window.findViewById(R.id.tv_button_right);
        tv_button_right.setText(btnRight);
        tv_button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (confirm != null) {
                    confirm.confirm();
                }
            }
        });
    }

    public interface InterfaceConfirm {
        public void confirm();
    }

    public interface InterfaceCancel {
        public void cancel();
    }
}
