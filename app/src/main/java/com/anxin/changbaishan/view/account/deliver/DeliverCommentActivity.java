package com.anxin.changbaishan.view.account.deliver;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.BaseEntity;
import com.anxin.changbaishan.entity.DeliveryCommentEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.ImageLoadUtil;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.base.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeliverCommentActivity extends SwipeBackActivity {

    @Bind(R.id.iv_icon)
    ImageView mIvIcon;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.rbar_average)
    RatingBar mRbarAverage;
    @Bind(R.id.tv_average)
    TextView mTvAverage;
    @Bind(R.id.tv_order_num)
    TextView mTvOrderNum;
    @Bind(R.id.rbar_comment)
    RatingBar mRbarComment;
    @Bind(R.id.tv_comment)
    EditText mTvComment;
    @Bind(R.id.btn_done)
    Button mBtnDone;

    private int mOrderId;
    private DeliveryCommentEntity.DataBean mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_comment);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setBack();
        setTitleName("配送评价");

        mOrderId = getIntent().getExtras().getInt("OrderId", 0);
        if (0 != mOrderId) {
            getDeliveryCommentInfo();
        }
    }

    @OnClick(R.id.btn_done)
    public void onClick() {
        addComment();
    }

    private void getDeliveryCommentInfo() {
        VolleyRequest.getDeliveryCommentInfo(mOrderId, this.getClass().getSimpleName(),
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            DeliveryCommentEntity entity =
                                    loadDataUtil.getJsonData(response, DeliveryCommentEntity.class);
                            if (0 == entity.getErrorNo()) {
                                mData = entity.getData();
                                showData();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                getDeliveryCommentInfo();
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

    private void showData() {
        ImageLoadUtil.loadImage(mActivity, mData.getPhoto(), mIvIcon);
        mTvName.setText(mData.getName());
        mTvTime.setText(mData.getAddtime());
        mRbarAverage.setRating(Float.valueOf(mData.getStarCount()));
        mTvAverage.setText(String.valueOf(mData.getStarCount()));
        mTvOrderNum.setText(String.valueOf(mData.getTotalCount()));
    }

    private void addComment() {
        VolleyRequest.addComment(0, mTvComment.getText().toString(), mData.getStars(), mOrderId,
                this.getClass().getSimpleName() + "Comment", new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            BaseEntity entity =
                                    loadDataUtil.getJsonData(response, BaseEntity.class);
                            if (0 == entity.getErrorNo()) {
                                showShortToast("评论成功");
                                scrollToFinishActivity();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                addComment();
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
