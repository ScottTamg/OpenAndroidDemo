package com.anxin.changbaishan.view;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.view.adapter.SectionsPagerAdapter;
import com.anxin.changbaishan.view.base.BaseActivity;
import com.anxin.changbaishan.view.base.KJActivityStack;
import com.anxin.changbaishan.view.dummy.DummyContent;
import com.anxin.changbaishan.widget.dragindicator.DragIndicatorView;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainTabActivity extends BaseActivity implements ShoppingCartFragment.OnListFragmentInteractionListener, HomeFragment.OnListHomeFragmentInteractionListener {
    @Bind(R.id.tab_content)
    ViewPager mViewPager;
    @Bind(R.id.tabs_rg)
    RadioGroup mTabsRg;
    @Bind(R.id.indicator)
    DragIndicatorView mIndicator;

    private long initTime = 0;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Map<String, DummyContent.DummyItem> mShoppingCartData = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_tab);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - initTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                initTime = System.currentTimeMillis();
            } else {
                KJActivityStack.create().AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onListHomeFragmentInteraction(DummyContent.DummyItem item) {
        showShortToast(String.valueOf(item.id));
        addShoppingCartData(item);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        showShortToast(String.valueOf(item.id));
    }

    @Override
    public void onDeleteItemInteraction(DummyContent.DummyItem item) {
        removeShoppingCartData(item);
    }

    @Override
    public void onCheckedItemInteraction(DummyContent.DummyItem item) {
        if (null != item) {
            if (mShoppingCartData.containsKey(item.id)) {
                mShoppingCartData.put(item.id, item);
                mSectionsPagerAdapter.getShoppingCartFragment().checkedOptions();
            }
        }
    }

    @Override
    public void onNumberChangedInteraction(DummyContent.DummyItem item) {
        if (null != item) {
            if (mShoppingCartData.containsKey(item.id)) {
                mShoppingCartData.put(item.id, item);
                mSectionsPagerAdapter.getShoppingCartFragment().checkedOptions();
            }
        }
    }

    private void initView() {

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("mViewPager", "position = " + position);
                mTabsRg.check(mTabsRg.getChildAt(position).getId());
                if (mSectionsPagerAdapter.getItem(position) instanceof ShoppingCartFragment) {
                    ((ShoppingCartFragment)mSectionsPagerAdapter.getItem(position)).refreshCartData();
                    Log.e("mViewPager", "refreshCartData" + mShoppingCartData.size());
                } else {
//                    mSectionsPagerAdapter.getShoppingCartFragment().syncCartData();
                    Log.e("mViewPager", "syncCartData" + mShoppingCartData.size());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mTabsRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < mTabsRg.getChildCount(); i++) {
                    if (mTabsRg.getChildAt(i).getId() == checkedId) {
                        mViewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });
        setIndicatorNum(0);
    }

    public Map<String, DummyContent.DummyItem> getShoppingCartData() {
        return mShoppingCartData;
    }

    public void setShoppingCartData(Map<String, DummyContent.DummyItem> itemMap) {
        if (!itemMap.isEmpty()) {
            mShoppingCartData = itemMap;
            setIndicatorNum(countNum());
        }
    }

    public void addShoppingCartData(DummyContent.DummyItem item) {
        if (null != item) {
            if (mShoppingCartData.containsKey(item.id)) {
                DummyContent.DummyItem newItem = mShoppingCartData.get(item.id);
                newItem.number++;
                mShoppingCartData.put(item.id, newItem);
            } else {
                mShoppingCartData.put(item.id, item);
            }
            setIndicatorNum(countNum());
        }
    }

    public void addAllShoppingCartData(Map<String, DummyContent.DummyItem> itemMap) {
        if (!itemMap.isEmpty()) {
            mShoppingCartData.putAll(itemMap);
            setIndicatorNum(countNum());
        }
    }

    public void removeShoppingCartData(DummyContent.DummyItem item) {
        if (null != item) {
            if (mShoppingCartData.containsKey(item.id)) {
                mShoppingCartData.remove(item.id);
                setIndicatorNum(countNum());
                mSectionsPagerAdapter.getShoppingCartFragment().checkedOptions();
            }
        }
    }

    public void removeListShoppingCartData(List<DummyContent.DummyItem> list) {
        if (!list.isEmpty()) {
            for (DummyContent.DummyItem item : list) {
                if (mShoppingCartData.containsKey(item.id)) {
                    mShoppingCartData.remove(item.id);
                }
            }
            setIndicatorNum(countNum());
            mSectionsPagerAdapter.getShoppingCartFragment().checkedOptions();
        }
    }

    public void checkedAllShoppingCartData(boolean isChecked) {
        if (!mShoppingCartData.isEmpty()) {
            for (DummyContent.DummyItem item : mShoppingCartData.values()) {
                item.isChecked = isChecked;
            }
        }
    }

    private int countNum(){
        int count = 0;
        if (!mShoppingCartData.isEmpty()) {
            for (DummyContent.DummyItem item : mShoppingCartData.values()) {
                count += item.number;
            }
        }
        return count;
    }

    public void setIndicatorNum(int value) {
        mIndicator.setTouch(false);
        if (value <= 0) {
            mIndicator.setVisibility(View.GONE);
        } else if (value > 99) {
            mIndicator.setVisibility(View.VISIBLE);
            mIndicator.setText("99+");
        } else {
            mIndicator.setVisibility(View.VISIBLE);
            mIndicator.setText(String.valueOf(value));
        }
    }
}
