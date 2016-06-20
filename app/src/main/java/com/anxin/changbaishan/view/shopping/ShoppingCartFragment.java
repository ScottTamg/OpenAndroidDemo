package com.anxin.changbaishan.view.shopping;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.CreateOrderEntity;
import com.anxin.changbaishan.entity.ProductEntity;
import com.anxin.changbaishan.entity.UploadProduct;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.MainTabActivity;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.adapter.ShoppingCartItemAdapter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ShoppingCartFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    @Bind(R.id.ll_left)
    LinearLayout mLlLeft;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.btn_right)
    Button mBtnRight;
    @Bind(R.id.list)
    RecyclerView mList;
    @Bind(R.id.checkbox_all)
    CheckBox mCheckboxAll;
    @Bind(R.id.tv_total)
    TextView mTvTotal;
    @Bind(R.id.btn_settlement)
    Button mBtnSettlement;
    @Bind(R.id.rl_total)
    RelativeLayout mRlTotal;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private int mColumnCount = 1;
    private String mUploadProducts;
    private OnListFragmentInteractionListener mListener;
    private MainTabActivity mActivity;
    private ShoppingCartItemAdapter mCartItemAdapter;
    private List<ProductEntity.DataBean.ListBean> mCheckedItems;
    private List<ProductEntity.DataBean.ListBean> mShoppingCartData = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingCartFragment() {
    }

    public static ShoppingCartFragment newInstance(int columnCount) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (MainTabActivity) activity;  //解决在“内存重启”之后调用GetActivity()方法报空指针异常
        if (activity instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnListHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppingcart, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshCartData();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Subscribe
    public void addShoppingCartData(ProductEntity.DataBean.ListBean data) {
//        mActivity.showShortToast(data.getName());
//        if (null != data) {
//            if (mShoppingCartData.size() > 0) {
//                boolean isNewItem = true;
//                for (ProductEntity.DataBean.ListBean item : mShoppingCartData) {
//                    if (item.getID().equals(data.getID())) {
//                        item.setCount(item.getCount() + 1);
//                        isNewItem = false;
//                        break;
//                    }
//                }
//                if (isNewItem) {
//                    mShoppingCartData.add(data);
//                }
//            } else {
//                mShoppingCartData.add(data);
//            }
//        }
    }

    @OnClick({R.id.ll_left, R.id.btn_right, R.id.btn_settlement, R.id.checkbox_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                break;
            case R.id.btn_right:
                mActivity.showShortToast("客服订水");
                break;
            case R.id.btn_settlement:
                if ("".equals( (String)mActivity.spUtil.get(SPUtil.ATOKEN, ""))) {
                    mActivity.startAnimActivity(RegisterActivity.class);
                } else {
                    mActivity.startProgressDialog();
                    createProductList();
                    creatMyOrders();
                }
                break;
            case R.id.checkbox_all:
                if (null != mCartItemAdapter && null != mCartItemAdapter.getDummyItems()
                        && !mCartItemAdapter.getDummyItems().isEmpty()) {
                    boolean flag = mCheckboxAll.isChecked();
                    mActivity.checkedAllShoppingCartData(flag);

                    for (ProductEntity.DataBean.ListBean item : mCartItemAdapter.getDummyItems()) {
                        item.setChecked(flag);
                    }
                    mCartItemAdapter.notifyDataSetChanged();
                    checkedOptions();
                } else {
                    mActivity.showShortToast("没有选择商品");
                }
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSwipeRefresh.setRefreshing(false);
            mActivity.showShortToast("mSwipeRefresh");
        }
    };

    private void init() {
        mLlLeft.setVisibility(View.GONE);
        mTvTitle.setText("购物车");

        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(1, 2000);
            }
        });

        //Set the adapter
        if (mColumnCount <= 1) {
            mList.setLayoutManager(new LinearLayoutManager(mActivity));
        } else {
            mList.setLayoutManager(new GridLayoutManager(mActivity, mColumnCount));
        }

        mList.setItemAnimator(new DefaultItemAnimator());
        /*mList.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.HORIZONTAL
                , 2, mActivity.getResources().getColor(R.color.divider_gray)));*/
    }

    public void checkedOptions() {
        if (null != mCartItemAdapter && null != mCartItemAdapter.getDummyItems()
                && mCartItemAdapter.getDummyItems().size() >= 0) {
            mCheckedItems = new ArrayList<>();
            boolean checkedAll = true;
            float count = 0;
            int total = 0;
            for (ProductEntity.DataBean.ListBean item : mCartItemAdapter.getDummyItems()) {
                if (item.getChecked()) {
                    mCheckedItems.add(item);
                    count += item.getCount() * Float.valueOf(item.getSellPrice());
                    total += item.getCount();
                } else {
                    checkedAll = false;
                }
            }
            mBtnSettlement.setText("去结算 (" + total + ")");
            mTvTotal.setText(count + "元");
            mCheckboxAll.setChecked(checkedAll);
        }
    }

    public void refreshCartData() {
        if (null != mActivity && null != mActivity.getShoppingCartData() && !mActivity.getShoppingCartData().isEmpty()) {
            List<ProductEntity.DataBean.ListBean> items = new ArrayList<>();
            Iterator<ProductEntity.DataBean.ListBean> iterator = mActivity.getShoppingCartData().values().iterator();
            while (iterator.hasNext()) {
                items.add(iterator.next());
            }

            mCartItemAdapter = new ShoppingCartItemAdapter(this, items, mListener);
            mList.setAdapter(mCartItemAdapter);
            mCartItemAdapter.notifyDataSetChanged();
            checkedOptions();
        }
    }

    public void syncCartData() {
        Map<String, ProductEntity.DataBean.ListBean> map = new ArrayMap<>();
        for (ProductEntity.DataBean.ListBean item : mCartItemAdapter.getDummyItems()) {
            if (!map.containsKey(item.getID())) {
                map.put(item.getID(), item);
            } else {
                 map.get(item.getID()).setCount(map.get(item.getID()).getCount() + item.getCount());
            }
        }
        mActivity.setShoppingCartData(map);
    }

    private void createProductList() {
        if (null != mCheckedItems && !mCheckedItems.isEmpty()) {
            List<UploadProduct> list = new ArrayList<>();
            for (ProductEntity.DataBean.ListBean item : mCheckedItems) {
                list.add(new UploadProduct(item.getID(), item.getCount()));
            }
            mUploadProducts = new Gson().toJson(list);
        } else {
            mActivity.showShortToast("没有选择商品");
        }
    }

    private void creatMyOrders() {
        VolleyRequest.creatMyOrders(mUploadProducts, this.getClass().getSimpleName(),
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            CreateOrderEntity entity = mActivity.loadDataUtil.getJsonData(response, CreateOrderEntity.class);
                            if (0 == entity.getErrorNo()) {
                                mActivity.removeListShoppingCartData(mCheckedItems);
                                for (ProductEntity.DataBean.ListBean item : mCheckedItems) {
                                    mCartItemAdapter.getDummyItems().remove(item);
                                }
                                mCartItemAdapter.notifyDataSetChanged();
                                checkedOptions();

                                Bundle bundle = new Bundle();
                                bundle.putString("orderId", entity.getData().getOrderId());
                                mActivity.startAnimActivity(MyOrderActivity.class, bundle);
                            } else if (-99 == entity.getErrorNo()) {
                                mActivity.spUtil.put(SPUtil.TOKEN, "");
                                creatMyOrders();
                            } else if (-52 == entity.getErrorNo()) {
                                mActivity.spUtil.put(SPUtil.ATOKEN, "");
                                mActivity.startAnimActivity(RegisterActivity.class);
                            } else {
                                mActivity.showShortToast(entity.getMessage());
                            }
                        } else {
                            mActivity.showShortToast(error);
                        }
                        mActivity.stopProgressDialog();
                    }
                });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ProductEntity.DataBean.ListBean item);

        void onDeleteItemInteraction(ProductEntity.DataBean.ListBean item);

        void onCheckedItemInteraction(ProductEntity.DataBean.ListBean item);

        void onNumberChangedInteraction(ProductEntity.DataBean.ListBean item);
    }

}
