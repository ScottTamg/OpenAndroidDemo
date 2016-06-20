package com.anxin.changbaishan.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.ProductEntity;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.adapter.SectionsPagerAdapter;
import com.anxin.changbaishan.view.base.BaseActivity;
import com.anxin.changbaishan.view.base.KJActivityStack;
import com.anxin.changbaishan.view.home.HomeFragment;
import com.anxin.changbaishan.view.shopping.ShoppingCartFragment;
import com.anxin.changbaishan.widget.dragindicator.DragIndicatorView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainTabActivity extends BaseActivity
        implements ShoppingCartFragment.OnListFragmentInteractionListener,
        HomeFragment.OnListHomeFragmentInteractionListener {
    @Bind(R.id.tab_content)
    ViewPager mViewPager;
    @Bind(R.id.tabs_rg)
    RadioGroup mTabsRg;
    @Bind(R.id.indicator)
    DragIndicatorView mIndicator;
    @Bind(R.id.tab_rb_shoppingcart)
    RadioButton mTabRbShoppingcart;

    private long initTime = 0;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Map<String, ProductEntity.DataBean.ListBean> mShoppingCartData = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_tab);
        ButterKnife.bind(this);

        if (null != savedInstanceState && !savedInstanceState.isEmpty()) {
            String jsonStr = savedInstanceState.getString("Data");
        }
        if (mShoppingCartData.isEmpty()) {
            String jsonStr = (String) spUtil.get(SPUtil.CARTCACHE, "");
            List<ProductEntity.DataBean.ListBean> list =
                    loadDataUtil.getJsonListData(jsonStr, ProductEntity.DataBean.ListBean.class);
            if (list != null && !list.isEmpty()) {
                for (ProductEntity.DataBean.ListBean item : list) {
                    mShoppingCartData.put(item.getID(), item);
                }
            }
            setIndicatorNum(countNum());
        }

        initView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!mShoppingCartData.isEmpty()) {
            Log.e("Time", " Start Time: " + System.currentTimeMillis());

            List<ProductEntity.DataBean.ListBean> items = new ArrayList<>();
            Iterator<ProductEntity.DataBean.ListBean> iterator = mShoppingCartData.values().iterator();
            while (iterator.hasNext()) {
                items.add(iterator.next());
            }

            String jsonStr = new Gson().toJson(items);
            Log.e("Time", " End Time: " + System.currentTimeMillis());
            Log.e("Time", " Json: " + jsonStr);
            spUtil.put(SPUtil.CARTCACHE, jsonStr);
            outState.putString("Data", jsonStr);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - initTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                initTime = System.currentTimeMillis();
            } else {
                Log.e("Time", " Start Time: " + System.currentTimeMillis());

                List<ProductEntity.DataBean.ListBean> items = new ArrayList<>();
                Iterator<ProductEntity.DataBean.ListBean> iterator = mShoppingCartData.values().iterator();
                while (iterator.hasNext()) {
                    items.add(iterator.next());
                }
                String jsonStr = new Gson().toJson(items);
                Log.e("Time", " End Time: " + System.currentTimeMillis());
                Log.e("Time", " Json: " + jsonStr);
                spUtil.put(SPUtil.CARTCACHE, jsonStr);
                KJActivityStack.create().AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onListHomeFragmentInteraction(ProductEntity.DataBean.ListBean item,
                                              Drawable drawable, int[] startLocation) {
//        showShortToast(String.valueOf(item.getID()));
        doAnim(drawable, startLocation);
        addShoppingCartData(item);
    }

    @Override
    public void onListFragmentInteraction(ProductEntity.DataBean.ListBean item) {
//        showShortToast(String.valueOf(item.getID()));
    }

    @Override
    public void onDeleteItemInteraction(ProductEntity.DataBean.ListBean item) {
        removeShoppingCartData(item);
    }

    @Override
    public void onCheckedItemInteraction(ProductEntity.DataBean.ListBean item) {
        if (null != item) {
            if (mShoppingCartData.containsKey(item.getID())) {
                mShoppingCartData.put(item.getID(), item);
                mSectionsPagerAdapter.getShoppingCartFragment().checkedOptions();
            }
        }
    }

    @Override
    public void onNumberChangedInteraction(ProductEntity.DataBean.ListBean item) {
        if (null != item) {
            if (mShoppingCartData.containsKey(item.getID())) {
                mShoppingCartData.put(item.getID(), item);
                mSectionsPagerAdapter.getShoppingCartFragment().checkedOptions();
                setIndicatorNum(countNum());
            }
        }
    }

    private void initView() {
//        String channel = spUtil.getChannel(mActivity);
//        showShortToast(channel);

//        String mani = spUtil.getManiChannel(mActivity);
//        showShortToast("Mani" + mani);

//        deviceHeight = DeviceUtil.getDeviceHeight(this);
//        deviceWidth = DeviceUtil.getDeviceWidth(this);
//
//        endX = (mTabRbShoppingcart.getTop() + mTabRbShoppingcart.getBottom()) / 2;
//        endY = (mTabRbShoppingcart.getLeft() + mTabRbShoppingcart.getRight()) / 2;
        animationViewGroup = createAnimLayout();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTabsRg.check(mTabsRg.getChildAt(position).getId());
                if (mSectionsPagerAdapter.getItem(position) instanceof ShoppingCartFragment) {
                    ((ShoppingCartFragment) mSectionsPagerAdapter.getItem(position)).refreshCartData();
                } else {
//                    mSectionsPagerAdapter.getShoppingCartFragment().syncCartData();
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
//        setIndicatorNum(0);
    }

    public Map<String, ProductEntity.DataBean.ListBean> getShoppingCartData() {
        return mShoppingCartData;
    }

    public void setShoppingCartData(Map<String, ProductEntity.DataBean.ListBean> itemMap) {
        if (!itemMap.isEmpty()) {
            mShoppingCartData = itemMap;
            setIndicatorNum(countNum());
        }
    }

    public void addShoppingCartData(ProductEntity.DataBean.ListBean item) {
        if (null != item) {
            if (mShoppingCartData.containsKey(item.getID())) {
                ProductEntity.DataBean.ListBean newItem = mShoppingCartData.get(item.getID());
                newItem.setCount(newItem.getCount() + 1);
                mShoppingCartData.put(item.getID(), newItem);
            } else {
                mShoppingCartData.put(item.getID(), item);
            }
            setIndicatorNum(countNum());
        }
    }

    public void addAllShoppingCartData(Map<String, ProductEntity.DataBean.ListBean> itemMap) {
        if (!itemMap.isEmpty()) {
            mShoppingCartData.putAll(itemMap);
            setIndicatorNum(countNum());
        }
    }

    public void removeShoppingCartData(ProductEntity.DataBean.ListBean item) {
        if (null != item) {
            if (mShoppingCartData.containsKey(item.getID())) {
                item.setCount(1);
                mShoppingCartData.remove(item.getID());
                setIndicatorNum(countNum());
                mSectionsPagerAdapter.getShoppingCartFragment().checkedOptions();
            }
        }
    }

    public void removeListShoppingCartData(List<ProductEntity.DataBean.ListBean> list) {
        if (!list.isEmpty()) {
            for (ProductEntity.DataBean.ListBean item : list) {
                if (mShoppingCartData.containsKey(item.getID())) {
                    item.setCount(1);
                    mShoppingCartData.remove(item.getID());
                }
            }
            setIndicatorNum(countNum());
            mSectionsPagerAdapter.getShoppingCartFragment().checkedOptions();
        }
    }

    public void checkedAllShoppingCartData(boolean isChecked) {
        if (!mShoppingCartData.isEmpty()) {
            for (ProductEntity.DataBean.ListBean item : mShoppingCartData.values()) {
                item.setChecked(isChecked);
            }
        }
    }

    private int countNum() {
        int count = 0;
        if (!mShoppingCartData.isEmpty()) {
            for (ProductEntity.DataBean.ListBean item : mShoppingCartData.values()) {
                count += item.getCount();
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

    //动画时间
    private final int AnimationDuration = 1000;
    //正在执行的动画数量
    private int number = 0;
    //是否完成清理
    private boolean isClean = false;
    private FrameLayout animationViewGroup;
    private Handler cleahHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //用来清除动画后留下的垃圾
                    try {
                        animationViewGroup.removeAllViews();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    isClean = false;
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 创建动画层
     *
     * @return
     */
    private FrameLayout createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        FrameLayout animLayout = new FrameLayout(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * 将要执行的动画View 添加到动画层
     *
     * @param viewGroup 动画运行层 这是framelayout
     * @param view      要运行动画的view
     * @param location  动画的起始位置
     * @return
     */
    private View addViewToAnimLayout(ViewGroup viewGroup, View view, int[] location) {
        int x = location[0];
        int y = location[1];
        viewGroup.addView(view);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(dip2px(this, 90), dip2px(this, 90));
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(lp);

        return view;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void doAnim(Drawable drawable, int[] startLocation) {
        if (!isClean) {
            setAnim(drawable, startLocation);
        } else {
            try {
                animationViewGroup.removeAllViews();
                isClean = false;
                setAnim(drawable, startLocation);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isClean = true;
            }
        }
    }

    private void setAnim(Drawable drawable, int[] startLocation) {
        final ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(drawable);
        final View view = addViewToAnimLayout(animationViewGroup, imageView, startLocation);
        view.setAlpha(0.6f);

        int[] endLocation = new int[2];
        mTabRbShoppingcart.getLocationInWindow(endLocation);
//
//        endX = (mTabRbShoppingcart.getTop() + mTabRbShoppingcart.getBottom()) / 2;
//        endY = (mTabRbShoppingcart.getLeft() + mTabRbShoppingcart.getRight()) / 2;
//        + (mTabRbShoppingcart.getBottom() - mTabRbShoppingcart.getTop()) / 2
//        + (mTabRbShoppingcart.getRight() - mTabRbShoppingcart.getLeft()) / 2

        int endX = endLocation[0] + dip2px(this, 20) - startLocation[0];
        int endY = endLocation[1] - dip2px(this, 25) - startLocation[1];

        AnimationSet as = new AnimationSet(true);
        as.setFillAfter(true);
        as.setDuration(AnimationDuration);
        ScaleAnimation sa = new ScaleAnimation(1F, 0.2F, 1F, 0.2F
                , Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        sa.setDuration(200);
        as.addAnimation(sa);
        AlphaAnimation aa = new AlphaAnimation(1, 0);
        aa.setDuration(AnimationDuration);
        as.addAnimation(aa);
        TranslateAnimation ta = new TranslateAnimation(0, endX, 0, endY);
        ta.setDuration(AnimationDuration);
        ta.setInterpolator(new LinearInterpolator());
        as.addAnimation(ta);

        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                number++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                number--;
                if (0 == number) {
                    isClean = true;
                    cleahHandler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.startAnimation(as);
    }

    @Override
    public void onLowMemory() {
        isClean = true;
        try {
            animationViewGroup.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isClean = false;
        super.onLowMemory();
    }
}
