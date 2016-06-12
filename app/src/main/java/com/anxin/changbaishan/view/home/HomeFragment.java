package com.anxin.changbaishan.view.home;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.ProductEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.ImageLoadUtil;
import com.anxin.changbaishan.utils.PhoneUtils;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.MainTabActivity;
import com.anxin.changbaishan.view.adapter.HeadViewPagerAdapter;
import com.anxin.changbaishan.view.adapter.RCYProductItemAdapter;
import com.anxin.changbaishan.view.adapter.RecyclerViewStateUtils;
import com.anxin.changbaishan.view.dummy.DummyContent;
import com.anxin.changbaishan.widget.LoadingFooter;
import com.anxin.changbaishan.widget.RecycleViewDivider;
import com.anxin.changbaishan.widget.recyclerview.EndlessRecyclerOnScrollListener;
import com.anxin.changbaishan.widget.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.anxin.changbaishan.widget.recyclerview.HeaderSpanSizeLookup;
import com.anxin.changbaishan.widget.recyclerview.RecyclerViewUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnListHomeFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.tv_left)
    TextView mTvLeft;
    @Bind(R.id.ll_left)
    LinearLayout mLlLeft;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.btn_right)
    Button mBtnRight;
    @Bind(R.id.list)
    RecyclerView mList;
    @Bind(R.id.iv_left)
    ImageView mIvLeft;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int previousPosition;
    private boolean isStop;
    private Timer mTimer;
    private TimerTask mTask;
    private MainTabActivity mActivity;
    private List<ProductEntity.DataBean.ListBean> mProductList;
    private List<ProductEntity.DataBean.ListBean> mTrialProductList;

    private RecycleViewDivider mDivider;
    private OnListHomeFragmentInteractionListener mListener;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private ViewPager mViewpagerHead;
    private RelativeLayout headview;
    private RelativeLayout footview;

    private AnimationSet as;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (MainTabActivity) activity;  //解决在“内存重启”之后调用GetActivity()方法报空指针异常
        if (activity instanceof OnListHomeFragmentInteractionListener) {
            mListener = (OnListHomeFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        init();
        getProductList();
//        getTrialProductList();
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        stopTask();
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

    @OnClick({R.id.ll_left, R.id.btn_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
//                mActivity.showShortToast("设置地址");
                mActivity.startAnimActivity(LoationActivity.class);
                break;
            case R.id.btn_right:
//                mActivity.showShortToast("客服订水");
                PhoneUtils.callDial(mActivity, PhoneUtils.PHONE);
                break;
        }
    }

    private void init() {
        mLlLeft.setVisibility(View.VISIBLE);
        mIvLeft.setImageResource(R.drawable.location);
        mTvLeft.setText((String) mActivity.spUtil.get(SPUtil.LOATION_NAME, ""));
        mBtnRight.setVisibility(View.VISIBLE);

        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stopTask();
                getProductList();
//                getTrialProductList();
                mList.removeItemDecoration(mDivider);
            }
        });
    }

    private void initView() {
        mTvLeft.setText((String) mActivity.spUtil.get(SPUtil.LOATION_NAME, ""));

        RCYProductItemAdapter adapter = new RCYProductItemAdapter(mActivity, mProductList, mListener);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        if (null == mDivider) {
            mDivider = new RecycleViewDivider(mActivity, 3, 2, mActivity.getResources().getColor(R.color.divider_gray));
        }
        mList.addItemDecoration(mDivider);
        mList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        GridLayoutManager manager = new GridLayoutManager(mActivity, 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mList.getAdapter(), manager.getSpanCount()));
        mList.setLayoutManager(manager);

//        RecyclerViewUtils.setHeaderView(mList, new SampleHeader(mActivity));
//        RecyclerViewUtils.setFooterView(mList, new SampleFooter(mActivity));
//        mList.addOnScrollListener(mOnScrollListener);
    }

    private void getProductList() {
        VolleyRequest.getProductList(this.getClass().getSimpleName(), new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String response, String error) {
                if (isSuccess) {
                    ProductEntity entity = mActivity.loadDataUtil.getJsonData(response, ProductEntity.class);
                    if (0 == entity.getErrorNo()) {
                        mProductList = entity.getData().getList();
                        getTrialProductList();

                    } else if (-99 == entity.getErrorNo()) {
                        mActivity.spUtil.put(SPUtil.TOKEN, "");
                        getProductList();
                    } else {
                        mActivity.showShortToast(entity.getMessage());
                    }
                } else {
                    mActivity.showShortToast(error);
                }
            }
        });
    }

    private void getTrialProductList() {
        VolleyRequest.getTrialProductList(this.getClass().getSimpleName() + "Trial",
                new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String response, String error) {
                if (isSuccess) {
                    ProductEntity entity = mActivity.loadDataUtil.getJsonData(response, ProductEntity.class);
                    if (0 == entity.getErrorNo()) {
                        mTrialProductList = entity.getData().getList();
                        initView();

                        if (null != mTrialProductList && !mTrialProductList.isEmpty()) {
                            setFootview();
                            RecyclerViewUtils.setHeaderView(mList, footview);
                        }
                        setHeadView();
                        RecyclerViewUtils.setFooterView(mList, headview);
                    } else if (-99 == entity.getErrorNo()) {
                        mActivity.spUtil.put(SPUtil.TOKEN, "");
                        getProductList();
                    } else {
                        mActivity.showShortToast(entity.getMessage());
                    }
                } else {
                    mActivity.showShortToast(error);
                }
                mSwipeRefresh.setRefreshing(false);
                mActivity.stopProgressDialog();
            }
        });
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mList);
            if (state == LoadingFooter.State.Loading) {
                return;
            }

            if (10 < DummyContent.ITEMS.size()) {
                RecyclerViewStateUtils.setFooterViewState(mActivity, mList, 10, LoadingFooter.State.Loading, null);
            } else {
                RecyclerViewStateUtils.setFooterViewState(mActivity, mList, 10, LoadingFooter.State.TheEnd, null);
            }
        }
    };

    private void setFootview() {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        footview = (RelativeLayout) inflater.inflate(R.layout.layout_product_top_item, null);
        TextView tvTopTitle = (TextView) footview.findViewById(R.id.tv_title);
        TextView tvTopContent = (TextView) footview.findViewById(R.id.tv_count);
        TextView tvTopstandard = (TextView) footview.findViewById(R.id.tv_standard);
        final ImageView imgTopicon = (ImageView) footview.findViewById(R.id.img_icon);
        TextView tvTopMoney = (TextView) footview.findViewById(R.id.tv_money);
        Button btnTopAdd = (Button) footview.findViewById(R.id.img_add);

        final ProductEntity.DataBean.ListBean topItem = mTrialProductList.get(0);
        tvTopTitle.setText(Html.fromHtml(topItem.getName() + "<font color='#999999'>"
                + topItem.getSummary() + "</font>"));
//        tvTopContent.setText(topItem.getSummary());
        tvTopstandard.setText("规格：" + topItem.getStandard());
        tvTopMoney.setText("￥：" + topItem.getSellPrice());
        ImageLoadUtil.loadImage(mActivity, topItem.getPhoto(), imgTopicon);

        btnTopAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                imgTopicon.getLocationInWindow(location);
                Drawable drawable = imgTopicon.getDrawable();
                mActivity.onListHomeFragmentInteraction(topItem, drawable, location);
            }
        });
    }

    private void setHeadView() {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        headview = (RelativeLayout) inflater.inflate(R.layout.layout_home_head, null);
        mViewpagerHead = (ViewPager) headview.findViewById(R.id.viewpager_head);
        final LinearLayout mLlPointGroup = (LinearLayout) headview.findViewById(R.id.ll_point_group);
        final HeadViewPagerAdapter adapter = new HeadViewPagerAdapter(mActivity, mLlPointGroup, DummyContent.ITEMS);
        mViewpagerHead.setAdapter(adapter);
        mViewpagerHead.setOffscreenPageLimit(1);
        mViewpagerHead.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (adapter.mImageViewList != null && adapter.mImageViewList.size() != 0) {
                    int newPosition = position % adapter.mImageViewList.size();
                    if (mLlPointGroup.getChildCount() != 0) {
                        mLlPointGroup.getChildAt(previousPosition).setEnabled(false);
                        mLlPointGroup.getChildAt(newPosition).setEnabled(true);
                        previousPosition = newPosition;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (isStop) {
                            startTask();
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        if (!isStop) {
                            stopTask();
                        }
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        break;
                }
            }
        });

        previousPosition = 0;
        if (mLlPointGroup.getChildCount() != 0) {
            mLlPointGroup.getChildAt(previousPosition).setEnabled(true);
        }

        startTask();
    }

    /**
     * 开启定时任务
     */
    private void startTask() {
        isStop = false;
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        };
        mTimer.schedule(mTask, 5 * 1000, 5 * 1000);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int index = mViewpagerHead.getCurrentItem();
            if (index >= mViewpagerHead.getChildCount()) {
                mViewpagerHead.setCurrentItem(0);
            } else {
                mViewpagerHead.setCurrentItem(index + 1);
            }
        }
    };

    /**
     * 停止定时任务
     */
    private void stopTask() {
        isStop = true;
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
//            mListener.onListHomeFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListHomeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListHomeFragmentInteraction(ProductEntity.DataBean.ListBean item, Drawable drawable, int[] startLocation);
    }
}
