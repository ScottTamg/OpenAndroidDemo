package com.anxin.changbaishan.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.view.adapter.SectionsPagerAdapter;
import com.anxin.changbaishan.view.base.BaseActivity;
import com.anxin.changbaishan.view.base.KJActivityStack;
import com.anxin.changbaishan.view.dummy.DummyContent;

public class MainTabActivity extends BaseActivity implements ShoppingCartFragment.OnListFragmentInteractionListener {
    private RadioGroup rgs;
    private ViewPager mViewPager;
    private long initTime = 0;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_tab);
        initView();
    }

    private void initView() {
        rgs = (RadioGroup) findViewById(R.id.tabs_rg);

        mViewPager = (ViewPager) findViewById(R.id.tab_content);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rgs.check(rgs.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rgs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rgs.getChildCount(); i++) {
                    if (rgs.getChildAt(i).getId() == checkedId) {
                        mViewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });

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
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Log.e("MainTabActivity", item.id);
    }
}
