package com.anxin.changbaishan.view.account.address;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.AddressEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.widget.RecycleViewDivider;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.adapter.AddressSelectItemAdapter;
import com.anxin.changbaishan.view.base.SwipeBackActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectAddressActivity extends SwipeBackActivity
        implements AddressSelectItemAdapter.OnAddressListInteractionListener{

    @Bind(R.id.rl_add_address)
    RelativeLayout mRlAddAddress;
    @Bind(R.id.list)
    RecyclerView mList;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private AddressSelectItemAdapter mAdapter;
    private List<AddressEntity.DataBean.ListBean> mListAddress;
    private AddressEntity.DataBean.ListBean mSelectedItme;
    private int mSelectOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setTitleName("收货地址");
        setBack();

        mSelectOrderId = getIntent().getIntExtra("OrderId", 0);

        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAddressList();
            }
        });

        mList.setLayoutManager(new LinearLayoutManager(mActivity));
        mList.setItemAnimator(new DefaultItemAnimator());
        mList.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.HORIZONTAL
                , 2, mActivity.getResources().getColor(R.color.divider_gray)));
    }

    @Override
    protected void onResume() {
        startProgressDialog();
        getAddressList();
        super.onResume();
    }

    @OnClick(R.id.rl_add_address)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_add_address:
                startAnimActivity(AddAddressActivity.class);
                break;
        }
    }

    private void getAddressList() {
        VolleyRequest.getAddressList(this.getClass().getSimpleName(), new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            AddressEntity entity = loadDataUtil.getJsonData(response, AddressEntity.class);
                            if (0 == entity.getErrorNo()) {
                                mListAddress = entity.getData().getList();
                                showData();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                getAddressList();
                            } else if (-52 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.ATOKEN, "");
                                startAnimActivity(RegisterActivity.class);
                            } else {
                                showShortToast(entity.getMessage());
                            }
                        } else {
                            showShortToast(error);
                        }
                        mSwipeRefresh.setRefreshing(false);
                        stopProgressDialog();
                    }
                });
    }

    private void showData() {
        if (null != mListAddress && !mListAddress.isEmpty()) {
            if (null == mAdapter) {
                mAdapter = new AddressSelectItemAdapter(mActivity, mListAddress, mSelectOrderId,
                        (AddressSelectItemAdapter.OnAddressListInteractionListener) mActivity);
                mList.setAdapter(mAdapter);
            } else {
                mAdapter.setSelectOrderId(mSelectOrderId);
                mAdapter.setList(mListAddress);
                mAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onEditItemInteraction(AddressEntity.DataBean.ListBean itme) {
        showShortToast("Edit: " + itme.getUserName());
        // TODO: 2016/5/16
        Bundle bundle = new Bundle();
        bundle.putParcelable("Address", itme);
        startAnimActivity(EditAddressActivity.class, bundle);
    }

    @Override
    public void onDefaultItemInteraction(AddressEntity.DataBean.ListBean itme) {
        showShortToast("Default: " + itme.getUserName());
        mSelectedItme = itme;
        Intent intent = new Intent();
        intent.putExtra("SelectAddress", itme);
        setResult(2, intent);
        scrollToFinishActivity();
    }
}
