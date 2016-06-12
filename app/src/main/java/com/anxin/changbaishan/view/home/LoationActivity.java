package com.anxin.changbaishan.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.CityEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.adapter.CityAdapter;
import com.anxin.changbaishan.view.base.SwipeBackActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoationActivity extends SwipeBackActivity {

    @Bind(R.id.btn_done)
    Button mBtnDone;
    @Bind(R.id.spinner_city)
    Spinner mSpinnerCity;
    @Bind(R.id.spinner_area)
    Spinner mSpinnerArea;

    private List<CityEntity.DataBean.ListBean> mCityList;
    private List<CityEntity.DataBean.ListBean> mAreaList;
    private CityAdapter mCityAdapter;
    private CityAdapter mAreaAdapter;
    private CityEntity.DataBean.ListBean mCity;
    private CityEntity.DataBean.ListBean mArea;

    private boolean isAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loation);
        ButterKnife.bind(this);
        setTitleName("位置");
        setNoBack();


        getCitys();
        init();
    }

    @OnClick(R.id.btn_done)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_done:
                if (isAddress) {
                    Intent intent = new Intent();
                    intent.putExtra("CityId", mCity.getID());
                    intent.putExtra("CityName", mCity.getCityName());
                    intent.putExtra("AreaId", mArea.getID());
                    intent.putExtra("AreaName", mArea.getCityName());
                    this.setResult(1, intent);
                    scrollToFinishActivity();
                } else {
                    // TODO: 2016/5/6 保存位置
                    spUtil.put(SPUtil.LOATION_ID, mArea.getID());
                    spUtil.put(SPUtil.LOATION_NAME, mArea.getCityName());
                    showShortToast(mArea.getCityName());
                    scrollToFinishActivity();
                }
                break;
        }
    }

    private void init() {

        Intent intent = getIntent();
        isAddress = intent.getBooleanExtra("address", false);

        mSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCity = (CityEntity.DataBean.ListBean)mCityAdapter.getItem(position);
                getAreas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        mSpinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mArea = (CityEntity.DataBean.ListBean)mAreaAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void getCitys() {
        startProgressDialog();
        VolleyRequest.getCitys(this.getClass().getSimpleName(), new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String response, String error) {
                if (isSuccess) {
                    CityEntity entity = loadDataUtil.getJsonData(response, CityEntity.class);
                    if (0 == entity.getErrorNo()) {
                        mCityList = entity.getData().getList();
                        mSpinnerCity.setAdapter(mCityAdapter = new CityAdapter(mActivity, mCityList));
                    } else if (-99 == entity.getErrorNo()) {
                        spUtil.put(SPUtil.TOKEN, "");
                        getCitys();
                    } else {
                        showShortToast(entity.getMessage());
                    }
                } else {
                    showShortToast(error);
                }
                stopProgressDialog();
            }
        });
    }

    private void getAreas() {
        startProgressDialog();
        VolleyRequest.getAreas(mCity.getID(), this.getClass().getSimpleName() + "Areas", new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String response, String error) {
                if (isSuccess) {
                    CityEntity entity = loadDataUtil.getJsonData(response, CityEntity.class);
                    if (0 == entity.getErrorNo()) {
                        mAreaList = entity.getData().getList();
                        mSpinnerArea.setAdapter(mAreaAdapter = new CityAdapter(mActivity, mAreaList));
                    } else if (-99 == entity.getErrorNo()) {
                        spUtil.put(SPUtil.TOKEN, "");
                        getAreas();
                    } else {
                        showShortToast(entity.getMessage());
                    }
                } else {
                    showShortToast(error);
                }
                stopProgressDialog();
            }
        });
    }
}
