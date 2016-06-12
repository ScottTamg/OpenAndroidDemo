package com.anxin.changbaishan.view.account.address;

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
import com.anxin.changbaishan.entity.BaseEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.widget.RecycleViewDivider;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.adapter.AddressItemAdapter;
import com.anxin.changbaishan.view.base.SwipeBackActivity;
import com.anxin.changbaishan.widget.CustomAlertDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShippingAddressActivity extends SwipeBackActivity
        implements AddressItemAdapter.OnAddressListInteractionListener {

    @Bind(R.id.rl_add_address)
    RelativeLayout mRlAddAddress;
    @Bind(R.id.list)
    RecyclerView mList;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private AddressItemAdapter mAdapter;
    private List<AddressEntity.DataBean.ListBean> mListAddress;
    private AddressEntity.DataBean.ListBean mSelectedItme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        ButterKnife.bind(this);

        init();

//        getAddressList();
    }

    @Override
    protected void onResume() {
        startProgressDialog();
        getAddressList();
        super.onResume();
    }

    private void init() {
        setTitleName("收货地址");
        setBack();

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

    private void showData() {
        if (null != mListAddress && !mListAddress.isEmpty()) {
            if (null == mAdapter) {
                mAdapter = new AddressItemAdapter(mActivity, mListAddress, (AddressItemAdapter.OnAddressListInteractionListener) mActivity);
                mList.setAdapter(mAdapter);
            } else {
                mAdapter.setList(mListAddress);
                mAdapter.notifyDataSetChanged();
            }
        }

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

    private void deleteAddress() {
        VolleyRequest.deleteAddress(mSelectedItme.getID(), this.getClass().getSimpleName() + "delete",
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            BaseEntity entity = loadDataUtil.getJsonData(response, BaseEntity.class);
                            if (0 == entity.getErrorNo()) {

                                mListAddress.remove(mSelectedItme);

                                showData();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                deleteAddress();
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

    private void setDefaultAddress() {
        VolleyRequest.setDefaultAddress(mSelectedItme.getID(),
                this.getClass().getSimpleName() + "default", new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            BaseEntity entity = loadDataUtil.getJsonData(response, BaseEntity.class);
                            if (0 == entity.getErrorNo()) {

                                for (AddressEntity.DataBean.ListBean item : mListAddress) {
                                    if (mSelectedItme.getID() == item.getID()) {
                                        item.setState(1);
                                    } else {
                                        item.setState(0);
                                    }
                                }
                                showData();
                                /*for (int i = 0; i < mListAddress.size(); i++) {
                                    if (mSelectedItme.getID() == mListAddress.get(i).getID()) {
                                        mListAddress.get(i).setState(1);
                                    } else {
                                        mListAddress.get(i).setState(0);
                                    }
                                }*/

                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                setDefaultAddress();
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

    @Override
    public void onEditItemInteraction(AddressEntity.DataBean.ListBean itme) {
        showShortToast("Edit: " + itme.getUserName());
        // TODO: 2016/5/16
        Bundle bundle = new Bundle();
        bundle.putParcelable("Address", itme);
        startAnimActivity(EditAddressActivity.class, bundle);
    }

    @Override
    public void onDeleteItemInteraction(AddressEntity.DataBean.ListBean itme) {
        showShortToast("Delete: " + itme.getUserName());
        mSelectedItme = itme;

        CustomAlertDialog.InterfaceConfirm confirm = new CustomAlertDialog.InterfaceConfirm() {
            @Override
            public void confirm() {
                deleteAddress();
            }
        };
        CustomAlertDialog.InterfaceCancel cancel = new CustomAlertDialog.InterfaceCancel() {
            @Override
            public void cancel() {}
        };
        CustomAlertDialog dialog = new CustomAlertDialog(mActivity, "取消", "删除", "确认删除"
                , confirm, cancel);
//        CustomAlertDialog dialog = new CustomAlertDialog(mActivity, "ok", "ok");
    }

    @Override
    public void onDefaultItemInteraction(AddressEntity.DataBean.ListBean itme) {
        showShortToast("Default: " + itme.getUserName());
        mSelectedItme = itme;
        setDefaultAddress();
    }


}
