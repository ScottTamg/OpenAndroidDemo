package com.anxin.changbaishan.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.anxin.changbaishan.view.AccountFragment;
import com.anxin.changbaishan.view.HomeFragment;
import com.anxin.changbaishan.view.ShoppingCartFragment;

/**
 * Created by Txw on 2016/4/5.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int HOMEFRAGMENT = 0;
    private static final int SHOPPINGCARTFRAGMENT = 1;
    private static final int ACCOUNTFRAGMENT = 2;

    private HomeFragment mHomeFragment;
    private ShoppingCartFragment mShoppingCartFragment;
    private AccountFragment mAccountFragment;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case HOMEFRAGMENT:
                return mHomeFragment = (null == mHomeFragment) ? HomeFragment.newInstance("HomeFragment", position + "")
                        : mHomeFragment;
            case SHOPPINGCARTFRAGMENT:
                return mShoppingCartFragment = (null == mShoppingCartFragment) ? ShoppingCartFragment.newInstance(1)
                        : mShoppingCartFragment;
            case ACCOUNTFRAGMENT:
                return mAccountFragment = (null == mAccountFragment) ? AccountFragment.newInstance("AccountFragment", position + "")
                        : mAccountFragment;
            default:
                return mHomeFragment = (null == mHomeFragment) ? HomeFragment.newInstance("HomeFragment", position + "")
                        : mHomeFragment;
        }

    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case HOMEFRAGMENT:
                return "SECTION 1";
            case SHOPPINGCARTFRAGMENT:
                return "SECTION 2";
            case ACCOUNTFRAGMENT:
                return "SECTION 3";
        }
        return null;
    }

    public HomeFragment getHomeFragment() {
        return mHomeFragment;
    }

    public ShoppingCartFragment getShoppingCartFragment() {
        return mShoppingCartFragment;
    }

    public AccountFragment getAccountFragment() {
        return mAccountFragment;
    }
}
