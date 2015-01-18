
package com.dekkoh.myactivity;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.dekkoh.R;
import com.dekkoh.application.BaseFragmentActivity;
import com.dekkoh.util.Log;

public class MyActivity extends BaseFragmentActivity {

    private AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int selectedTab = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myactivity_layout);
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(fragmentManager);
        showTabs();
        // Set up the ViewPager, attaching the adapter and setting up a listener
        // for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager
                .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between different app sections, select
                        // the corresponding tab.
                        // We can also use ActionBar.Tab#select() to do this if
                        // we have a reference to the
                        // Tab.
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter.
            // Also specify this Activity object, which implements the
            // TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(actionBar.newTab()
                    .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                    .setTabListener(tabListener));
        }
    }

    ActionBar.TabListener tabListener = new TabListener() {
        @Override
        public void onTabUnselected(ActionBar.Tab tab,
                FragmentTransaction fragmentTransaction) {
            Log.e(TAG, " onTabUnselected : " + tab.getPosition());

        }

        @Override
        public void onTabSelected(ActionBar.Tab tab,
                FragmentTransaction fragmentTransaction) {
            // When the given tab is selected, switch to the corresponding page
            // in
            // the ViewPager.
            Log.e(TAG, " onTabSelected : " + tab.getPosition());
            if (selectedTab != tab.getPosition()) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            selectedTab = tab.getPosition();
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab,
                FragmentTransaction fragmentTransaction) {
            Log.e(TAG, " onTabReselected : " + tab.getPosition());

        }
    };

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        private String[] fragmentTitle = new String[3];

        public AppSectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            fragmentTitle[0] = "My Questions";
            fragmentTitle[1] = "My Answers";
            fragmentTitle[2] = "Following";
        }

        @Override
        public Fragment getItem(int tabChoice) {
            Log.e(TAG, " getItem : " + tabChoice);
            switch (tabChoice) {
                case 1:
                    return new MyAnswersFragment();
                case 2:
                    return new MyFollowingFragment();
                default:
                    return new MyQuestionsFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle[position];
        }
    }
}
