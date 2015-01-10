package com.dekkoh.application;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.dekkoh.util.Log;

public class BaseFragmentActivity extends FragmentActivity {
	protected static String TAG = "BaseActivity";
	protected Activity activity;
	protected ActionBar actionBar;
	protected FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = this.getClass().getSimpleName();
		Log.d(TAG, "onCreate");
		activity = this;
		actionBar = getActionBar();
		actionBar.hide();
		fragmentManager = getSupportFragmentManager();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");

	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");

	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");

	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");

	}

	protected void showActionBar() {
		actionBar.show();

	}

	protected void showTabs() {
		actionBar.show();
		actionBar.setDisplayShowHomeEnabled(false); // hides action bar icon
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	}

}
