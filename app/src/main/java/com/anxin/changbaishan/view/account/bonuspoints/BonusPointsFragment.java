package com.anxin.changbaishan.view.account.bonuspoints;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.BonusPointsEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.adapter.BonusPointsAdapter;
import com.anxin.changbaishan.view.adapter.RecyclerViewStateUtils;
import com.anxin.changbaishan.widget.LoadingFooter;
import com.anxin.changbaishan.widget.recyclerview.EndlessRecyclerOnScrollListener;
import com.anxin.changbaishan.widget.recyclerview.HeaderAndFooterRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/5/29.
 */
public class BonusPointsFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    @Bind(R.id.list)
    RecyclerView mList;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private int mParam;
    private static BonusPointsFragment sFragment;
    private BonusPointsActivity mActivity;
    private BonusPointsEntity.DataBean mData;
    private BonusPointsAdapter mAdapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    private int pageIndex = 1;
    private int pageSize = 10;

    public BonusPointsFragment() {}

    public static BonusPointsFragment newInstance(int sectionNumber) {
        BonusPointsFragment fragment = new BonusPointsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        sFragment = fragment;
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BonusPointsActivity)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getInt(ARG_SECTION_NUMBER, 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bonus_points, container, false);
        ButterKnife.bind(this, rootView);

        init();
        return rootView;
    }

    private void init() {
        getUserBonusPoints();

        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                getUserBonusPoints();
            }
        });

        mList.setLayoutManager(new LinearLayoutManager(mActivity));
        mList.setItemAnimator(new DefaultItemAnimator());
//        mList.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.HORIZONTAL
//                , 0, mActivity.getResources().getColor(R.color.bg_gray)));
        mList.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void getUserBonusPoints() {
        VolleyRequest.getUserBonusPoints(mParam, this.getClass().getSimpleName(),
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            BonusPointsEntity entity =
                                    mActivity.loadDataUtil.getJsonData(response, BonusPointsEntity.class);
                            if (0 == entity.getErrorNo()) {
                                mData = entity.getData();
                                mActivity.setTvBonusPoints(mData.getBonusPoints());
                                if (mAdapter == null) {
                                    mAdapter = new BonusPointsAdapter(sFragment, mData.getList());
                                    mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
                                    mList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
                                } else {
                                    mAdapter.addData(mData.getList());
                                }

                            } else if (-99 == entity.getErrorNo()) {
                                mActivity.spUtil.put(SPUtil.TOKEN, "");
                                getUserBonusPoints();
                            } else if (-52 == entity.getErrorNo()) {
                                mActivity.spUtil.put(SPUtil.ATOKEN, "");
                                mActivity.startAnimActivity(RegisterActivity.class);
                            } else {
                                mActivity.showShortToast(entity.getMessage());
                            }
                        } else {
                            mActivity.showShortToast(error);
                        }
                        mSwipeRefresh.setRefreshing(false);
                        RecyclerViewStateUtils.setFooterViewState(mList, LoadingFooter.State.Normal);
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

            if (mData.getTotalCount() > pageIndex * pageSize) {
                RecyclerViewStateUtils.setFooterViewState(mActivity, mList, pageSize, LoadingFooter.State.Loading, null);
                pageIndex++;
                getUserBonusPoints();
            } else {
                RecyclerViewStateUtils.setFooterViewState(mActivity, mList, pageSize, LoadingFooter.State.TheEnd, null);
            }
        }
    };

}
