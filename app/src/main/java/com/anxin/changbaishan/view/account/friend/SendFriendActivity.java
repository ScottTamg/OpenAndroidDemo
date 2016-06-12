package com.anxin.changbaishan.view.account.friend;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.MyProductsListEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.base.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendFriendActivity extends SwipeBackActivity {

    @Bind(R.id.btn_send_friend)
    Button mBtnSendFriend;
    @Bind(R.id.tv_water_count)
    TextView mTvWaterCount;
    @Bind(R.id.tv_gifts_received)
    TextView mTvGiftsReceived;
    @Bind(R.id.btn_right)
    Button mBtnRight;

    private MyProductsListEntity.DataBean mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_friend);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setBack();
        setTitleName("好礼送朋友");
        setRight();
        mBtnRight.setText("送出的礼物");

        getMyProductsList();
    }

    @OnClick({R.id.btn_right, R.id.btn_send_friend, R.id.tv_gifts_received})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_right:
                startAnimActivity(SendFriendOrderActivity.class);
                break;
            case R.id.btn_send_friend:
                Bundle bundle = new Bundle();
                bundle.putParcelable("MyProductsListEntity", mData);
                startAnimActivity(CreatFriendOrderActivity.class, bundle);
                break;
            case R.id.tv_gifts_received:
                startAnimActivity(GiveToMeOrderActivity.class);
                break;
        }
    }

    private void getMyProductsList() {
        VolleyRequest.getMyProductsList(0, this.getClass().getSimpleName(),
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            MyProductsListEntity entity =
                                    loadDataUtil.getJsonData(response, MyProductsListEntity.class);
                            if (0 == entity.getErrorNo()) {
                                mData = entity.getData();
                                String waterCount = String.valueOf(entity.getData().getYWaterCount());
                                String str = "剩余" + waterCount + "箱";
                                SpannableString ssd = new SpannableString(str);
                                ssd.setSpan(new RelativeSizeSpan(2.0f), 2, waterCount.length() + 2,
                                        Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                                ssd.setSpan(new StyleSpan(Typeface.BOLD), 2, waterCount.length() + 2,
                                        Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                                ssd.setSpan(new ForegroundColorSpan(Color.BLUE), 2, waterCount.length() + 2,
//                                        Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                                mTvWaterCount.setText(ssd);

                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                getMyProductsList();
                            } else if (-52 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.ATOKEN, "");
                                startAnimActivity(RegisterActivity.class);
                            } else {
                                showShortToast(entity.getMessage());
                            }
                        } else {
                            showShortToast(error);
                        }
                    }
                });
    }
}
