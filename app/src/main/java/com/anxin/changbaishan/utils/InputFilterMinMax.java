package com.anxin.changbaishan.utils;

import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by Txw on 2016/4/29.
 */
public class InputFilterMinMax implements InputFilter{
    private int mMin;
    private int mMax;
    private InterfaceInputError mInputError;

    public InputFilterMinMax(int min, int max, InterfaceInputError inputError) {
        this.mMin = min;
        this.mMax = max;
        this.mInputError = inputError;
    }

    @Override
    public CharSequence filter(@NonNull CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (input < mMin) {
                mInputError.InputError(mMin);
            } else if (input > mMax) {
                mInputError.InputError(mMax);
            }
            return null;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }

    public interface InterfaceInputError {
        void InputError(int value);
    }
}
