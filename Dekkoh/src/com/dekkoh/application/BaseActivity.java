package com.dekkoh.application;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import com.dekkoh.util.AlertDialogHandler;
import com.dekkoh.util.Log;
import com.dekkoh.util.ProgressDialogHandler;

public class BaseActivity extends Activity {
	protected static String TAG = "BaseActivity";
	protected Activity activity;
	protected ActionBar actionBar;
	protected FragmentManager fragmentManager;
	protected ProgressDialogHandler progressDialogHandler;
	protected AlertDialogHandler alertDialogHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = this.getClass().getSimpleName();
		Log.d(TAG, "onCreate");
		activity = this;
		actionBar = getActionBar();
		actionBar.hide();
		fragmentManager = getFragmentManager();
		progressDialogHandler = ProgressDialogHandler.getInstance();
		alertDialogHandler = AlertDialogHandler.getInstance();
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
