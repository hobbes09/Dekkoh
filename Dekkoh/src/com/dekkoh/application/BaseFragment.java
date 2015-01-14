package com.dekkoh.application;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.dekkoh.custom.handler.AlertDialogHandler;
import com.dekkoh.custom.handler.ProgressDialogHandler;
import com.dekkoh.util.Log;

public class BaseFragment extends Fragment {
	protected static String TAG = "BaseFragment";
	protected Activity activity;
	protected ActionBar actionBar;
	protected FragmentManager fragmentManager;
	protected ProgressDialogHandler progressDialogHandler;
	protected AlertDialogHandler alertDialogHandler;
	protected View rootView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = this.getClass().getSimpleName();
		Log.d(TAG, "onCreate");
		activity = getActivity();
		actionBar = activity.getActionBar();
		fragmentManager = getFragmentManager();
		progressDialogHandler = ProgressDialogHandler.getInstance();
		alertDialogHandler = AlertDialogHandler.getInstance();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");

	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");

	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");

	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");

	}

	protected void showActionBar() {
		actionBar.show();
		actionBar.setDisplayShowHomeEnabled(true); // hides action bar icon
		actionBar.setDisplayShowTitleEnabled(true);
	}

	protected void showTabs() {
		actionBar.show();
		actionBar.setDisplayShowHomeEnabled(false); // hides action bar icon
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	}

}
