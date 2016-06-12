package com.anxin.changbaishan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.utils.InputFilterMinMax;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Txw on 2016/4/20.
 */
public class CustomNumberLayout extends LinearLayout {

    private final static int MINNUMBER = 1;
    private final static int MAXNUMBER = 99;

    @Bind(R.id.btn_plus)
    Button mBtnPlus;
    @Bind(R.id.editText)
    EditText mEditText;
    @Bind(R.id.btn_sub)
    Button mBtnSub;

    private int minNum = MINNUMBER;
    private int maxNum = MAXNUMBER;

    private int currentNum = minNum;
    private Context mContext;
    private TextWatcher mTextWatcher;
    private TextChangedListener mListener;

    public CustomNumberLayout(Context context) {
        this(context, null);
    }

    public CustomNumberLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_number, this);
        ButterKnife.bind(this, view);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomNumberLayout);
        minNum = a.getInt(R.styleable.CustomNumberLayout_min_num, MINNUMBER);
        maxNum = a.getInt(R.styleable.CustomNumberLayout_max_num, MAXNUMBER);
        currentNum = a.getInt(R.styleable.CustomNumberLayout_default_num, MINNUMBER);
        a.recycle();
        init();
    }

    private void init() {
        checkNumber();
        mEditText.setText(String.valueOf(currentNum));
        InputFilterMinMax.InterfaceInputError inputError = new InputFilterMinMax.InterfaceInputError() {
            @Override
            public void InputError(int value) {
                mEditText.setText(String.valueOf(value));
                currentNum = value;
            }
        };
        mEditText.setFilters(new InputFilter[] {new InputFilterMinMax(minNum, maxNum, inputError)});
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    currentNum = Integer.parseInt(s.toString());
                } else {
                    currentNum = minNum;
                }
//                mEditText.setSelection(s.length()  );
//                mEditText.removeTextChangedListener(this);
                checkNumber();
            }
        };
        mEditText.addTextChangedListener(mTextWatcher);
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
        mEditText.setText(String.valueOf(currentNum));
    }


    @OnClick({R.id.btn_plus, R.id.btn_sub})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_plus:
                currentNum++;
//                checkNumber();
                mEditText.setText(String.valueOf(currentNum));
                break;
            case R.id.btn_sub:
                currentNum--;
//                checkNumber();
                mEditText.setText(String.valueOf(currentNum));
                break;
        }
    }

    private void checkNumber() {
        if (currentNum == minNum) {
            mBtnSub.setEnabled(false);
            mBtnPlus.setEnabled(true);
        } else if (currentNum == maxNum) {
            mBtnSub.setEnabled(true);
            mBtnPlus.setEnabled(false);
        } else if (currentNum < minNum) {
            showToast(currentNum + "小于最小值" + minNum);
            currentNum = minNum;
            mBtnSub.setEnabled(false);
            mBtnPlus.setEnabled(true);
        } else if (currentNum > maxNum) {
            showToast(currentNum + "大于最大值" + maxNum);
            currentNum = maxNum;
            mBtnSub.setEnabled(true);
            mBtnPlus.setEnabled(false);
        } else {
            mBtnSub.setEnabled(true);
            mBtnPlus.setEnabled(true);
        }
        if (null != mListener) {
            mListener.onTextChangedInteraction(currentNum);
        }
//        mEditText.setText(String.valueOf(currentNum));
//        mEditText.addTextChangedListener(mTextWatcher);
    }

    public void setTextChangedListener(TextChangedListener listener) {
        this.mListener = listener;
    }

    private void showToast(String message) {
//        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public interface TextChangedListener {
        void onTextChangedInteraction(int value);
    }
}
