package com.anxin.changbaishan.view.account;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.MyInfoEntity;
import com.anxin.changbaishan.view.account.bonuspoints.BonusPointsActivity;
import com.anxin.changbaishan.view.account.deliver.DeliverWaterActivity;
import com.anxin.changbaishan.view.base.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountActivity extends SwipeBackActivity {

    @Bind(R.id.tv_water_count)
    TextView mTvWaterCount;
    @Bind(R.id.rl_water_count)
    RelativeLayout mRlWaterCount;
    @Bind(R.id.tv_points)
    TextView mTvPoints;
    @Bind(R.id.rl_points)
    RelativeLayout mRlPoints;
    @Bind(R.id.tv_cup_count)
    TextView mTvCupCount;
    @Bind(R.id.rl_cup_count)
    RelativeLayout mRlCupCount;

    private MyInfoEntity.DataBean mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setBack();
        setTitleName("账户");

        mData = getIntent().getExtras().getParcelable("MyInfo");

        if (null != mData) {
            mTvWaterCount.setText("(" + mData.getYWaterCount() + "箱)");
            mTvCupCount.setText("(" + mData.getYCupCount() + "个)");
            mTvPoints.setText("(" + mData.getYPoints() + "积分)");
        }
    }

    @OnClick({R.id.rl_water_count, R.id.rl_points, R.id.rl_cup_count})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_water_count:
                startAnimActivity(DeliverWaterActivity.class);
                break;
            case R.id.rl_points:
                startAnimActivity(BonusPointsActivity.class);
                break;
            case R.id.rl_cup_count:
                startAnimActivity(BonusPointsActivity.class);
                break;
        }
    }
}
