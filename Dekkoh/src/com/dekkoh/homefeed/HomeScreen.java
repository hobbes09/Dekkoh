package com.dekkoh.homefeed;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dekkoh.R;
import com.dekkoh.application.ApplicationState;
import com.dekkoh.application.BaseFragmentActivity;
import com.dekkoh.following.Following;
import com.dekkoh.messages.Messages;
import com.dekkoh.myactivity.MyActivity;
import com.dekkoh.myprofile.MyProfile;
import com.dekkoh.settings.Settings;
import com.dekkoh.slidingmenu.NavDrawerItem;
import com.dekkoh.slidingmenu.NavDrawerListAdapter;
import com.dekkoh.util.FileManager;

public class HomeScreen extends BaseFragmentActivity implements OnClickListener,
		Runnable {
	private ImageButton ibPost;
	private ImageButton ibMap;
	private TextView tvTitle;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	public static Context homeScreenContext;

	public static Thread threadQuestionUpdater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		customizeActionBar(); // MUST BE PLACED BEFORE setContentView()
		setContentView(R.layout.activity_homefeed);
		initialize(savedInstanceState);
	}

	private void initialize(Bundle savedInstanceState) {
		ibMap = (ImageButton) findViewById(R.id.ibMap);
		ibPost = (ImageButton) findViewById(R.id.ibPost);
		tvTitle = (TextView) findViewById(R.id.tvTitle);

		ibMap.setOnClickListener(this);
		ibPost.setOnClickListener(this);

		homeScreenContext = HomeScreen.this;

		navigationDrawerInitialisation(savedInstanceState);

		threadQuestionUpdater = new Thread("Question Updater");
		threadQuestionUpdater.start();
	}

	private void customizeActionBar() {
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00000000")));
		ActionBar actionBar = getActionBar();
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View mCustomView = layoutInflater.inflate(R.layout.custom_actionbar,
				null);
		actionBar.setCustomView(mCustomView);
		actionBar.setDisplayShowCustomEnabled(true);
	}

	private void navigationDrawerInitialisation(Bundle savedInstanceState) {
		mTitle = mDrawerTitle = getTitle();
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();
		// adding nav drawer items to array
		// Empty for app title
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// My Profile
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// My Activity
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Following
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		// Messages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// Settings
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(-1); // Display HomeFeed here
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(false);
		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 1:
			fragment = new MyProfile();
			break;
		case 2:
			Intent intent = new Intent(this, MyActivity.class);
			startActivity(intent);
			break;
		case 3:
			fragment = new Following();
			break;
		case 4:
			fragment = new Messages();
			break;
		case 5:
			fragment = new Settings();
			break;
		default:
			break;
		}

		if (fragment != null) {
			fragmentManager.beginTransaction()
					.replace(R.id.contentHomeActivity, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			try {
				threadQuestionUpdater.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if (QuestionContentManager.updateSuccessful) {
				fragment = new QuestionFragment();
				try {
					fragment.setArguments(QuestionContentManager
							.getNextQuestionContent());
				} catch (Exception e) {
					e.printStackTrace();
				}
				FragmentTransaction transaction = getSupportFragmentManager()
						.beginTransaction();
				transaction.setCustomAnimations(
						R.animator.frag_slide_in_from_bottom, 0);
				transaction.replace(R.id.contentHomeActivity, fragment);
				transaction.commit();
			} else {
				// Display dialog with
				// "Unable to access server. Check your internet connection"
				// message
			}
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.ibMap:
			Toast.makeText(getApplicationContext(), "Map Clicked",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.ibPost:
			Toast.makeText(getApplicationContext(), "Post Clicked",
					Toast.LENGTH_SHORT).show();
			break;
		}

	}

	@Override
	protected void onDestroy() {
		try {
			if (FileManager.getInstance().isObjectFileExistsInExternalStorage(
					(Activity) HomeScreen.homeScreenContext,
					ApplicationState.getQuestionsfile())) {
				FileManager.getInstance().deleteObjectFileFromExternalStorage(
						(Activity) HomeScreen.homeScreenContext,
						ApplicationState.getQuestionsfile());
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void run() {
		try {
			QuestionContentManager.fetchQuestionsFromBackend();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
