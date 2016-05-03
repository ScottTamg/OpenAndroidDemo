package com.anxin.changbaishan.view;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.view.adapter.HeadViewPagerAdapter;
import com.anxin.changbaishan.view.adapter.RCYProductItemAdapter;
import com.anxin.changbaishan.view.adapter.RecyclerViewStateUtils;
import com.anxin.changbaishan.view.dummy.DummyContent;
import com.anxin.changbaishan.widget.LoadingFooter;
import com.anxin.changbaishan.widget.recyclerview.EndlessRecyclerOnScrollListener;
import com.anxin.changbaishan.widget.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.anxin.changbaishan.widget.recyclerview.HeaderSpanSizeLookup;
import com.anxin.changbaishan.widget.recyclerview.RecyclerViewUtils;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int previousPosition;
    private boolean isStop;
    private Timer mTimer;
    private TimerTask mTask;
    private ViewPager mViewpagerHead;
    private RelativeLayout headview;
    private RelativeLayout footview;

    private OnListHomeFragmentInteractionListener mListener;
    private MainTabActivity mActivity;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;

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

        RCYProductItemAdapter adapter = new RCYProductItemAdapter(DummyContent.ITEMS, mListener);
        mList.addItemDecoration(new RecycleViewDivider(mActivity, 3, 2, Color.parseColor("#cccccc")));

        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        mList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        GridLayoutManager manager = new GridLayoutManager(mActivity, 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mList.getAdapter(), manager.getSpanCount()));
        mList.setLayoutManager(manager);

        setHeadView();
        setFootview();
        RecyclerViewUtils.setHeaderView(mList, footview);
        RecyclerViewUtils.setFooterView(mList, headview);
//        RecyclerViewUtils.setHeaderView(mList, new SampleHeader(mActivity));
//        RecyclerViewUtils.setFooterView(mList, new SampleFooter(mActivity));
//        mList.addOnScrollListener(mOnScrollListener);
        return view;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (MainTabActivity) context;  //解决在“内存重启”之后调用GetActivity()方法报空指针异常
        if (context instanceof OnListHomeFragmentInteractionListener) {
            mListener = (OnListHomeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
                mActivity.showShortToast("设置地址");
                break;
            case R.id.btn_right:
                mActivity.showShortToast("客服订水");
                break;
        }
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
    public interface OnListHomeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListHomeFragmentInteraction(DummyContent.DummyItem item);
    }
}
