
package com.dekkoh;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import com.dekkoh.custom.handler.AlertDialogHandler;
import com.dekkoh.custom.handler.ProgressDialogHandler;
import com.dekkoh.util.Log;
import com.dekkoh.util.SharedPreferenceManager;

public class BaseActivity extends Activity {
    protected static String TAG = "BaseActivity";
    protected Activity activity;
    protected ActionBar actionBar;
    protected FragmentManager fragmentManager;
    protected ProgressDialogHandler progressDialogHandler;
    protected AlertDialogHandler alertDialogHandler;
    protected SharedPreferenceManager sharedPreferenceManager;

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
        sharedPreferenceManager = SharedPreferenceManager.getInstance(activity);
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
