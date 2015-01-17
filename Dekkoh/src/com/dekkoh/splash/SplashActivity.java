package com.dekkoh.splash;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dekkoh.R;
import com.dekkoh.application.BaseActivity;
import com.dekkoh.application.DekkohApplication;
import com.dekkoh.datamodel.DekkohUser;
import com.dekkoh.googleplusloginhandler.GooglePlusLoginController;
import com.dekkoh.gpstracker.GPSTracker;
import com.dekkoh.homefeed.PreHomeScreen;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.util.Constants.SharedPreferenceConstants;
import com.dekkoh.util.Log;
import com.dekkoh.util.SharedPreferenceManager;
import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

public class SplashActivity extends BaseActivity {

	private TextView connectWith;
	private ImageView appLogo, googleLoginButton, appName;
	private ImageView facebookLoginButton;
	private RelativeLayout loginOptionsHolderLayout;
	private Context mContext;
	private DekkohApplication dekkohApplication;

	private ProgressDialog progress;

	private boolean activityRunning = true;
	private boolean userLoggedIn = false;
	
	

	// Google login helper
	GooglePlusLoginController googleLoginController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity_layout);
		
		

		mContext = getApplicationContext();

		dekkohApplication = (DekkohApplication) getApplication();

		// Progress Dialog
		progress = new ProgressDialog(this);
		progress.setMessage("Loading...");
		progress.setIndeterminate(true);
		//

		// Start GPS
		startGPSTracker();

		
		
		
	
		// Instantiate
		appName = (ImageView) findViewById(R.id.splash_activity_layout_app_name);
		connectWith = (TextView) findViewById(R.id.splash_activity_layout_connect_with_text);
		appLogo = (ImageView) findViewById(R.id.splash_activity_layout_logo_image);
		facebookLoginButton = (ImageView) findViewById(R.id.splash_activity_layout_facebook_login_button);
		googleLoginButton = (ImageView) findViewById(R.id.splash_activity_layout_google_login_button);
		loginOptionsHolderLayout = (RelativeLayout) findViewById(R.id.splash_activity_layout_login_options_holder);

		// Font Style for TextViews
		Typeface type_light = Typeface.createFromAsset(mContext.getAssets(),
				"fonts/source_sans_pro_light.ttf");
		connectWith.setTypeface(type_light);

		// Animations
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// Make other views Visible and translate with scaling of logo
				// to position

				SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager
						.getInstance(SplashActivity.this);
				if (sharedPreferenceManager
						.getBoolean(SharedPreferenceConstants.DEKKOH_USER_HAVE_INTERESTS) == true
						&& sharedPreferenceManager
								.getString(SharedPreferenceConstants.DEKKOH_USER_ID) != null
						&& sharedPreferenceManager.getString(
								SharedPreferenceConstants.DEKKOH_USER_ID)
								.compareTo("") != 0) {
					userLoggedIn = true;
					if (updateDekkohUserObject() == true) {
						sendtoHomeScreen();
					}
				} else if (sharedPreferenceManager
						.getBoolean(SharedPreferenceConstants.DEKKOH_USER_HAVE_INTERESTS) == false
						&& sharedPreferenceManager
								.getString(SharedPreferenceConstants.DEKKOH_USER_ID) != null
						&& sharedPreferenceManager.getString(
								SharedPreferenceConstants.DEKKOH_USER_ID)
								.compareTo("") != 0) {
					userLoggedIn = true;
					if (updateDekkohUserObject() == true) {
						sendtoInterestsScreen();
					}

				} else {
					AnimationSet animSet = new AnimationSet(true);
					TranslateAnimation anim_move = new TranslateAnimation(0, 0,
							0, -dpToPx(200));
					anim_move.setDuration(1000);
					animSet.addAnimation(anim_move);
					ScaleAnimation anim_scale = new ScaleAnimation(1f, 0.75f,
							1f, 0.75f, 50f, 50f);
					anim_scale.setDuration(1000);
					animSet.addAnimation(anim_scale);
					animSet.setFillAfter(true);
					appLogo.startAnimation(animSet);
					Animation animFadein = AnimationUtils.loadAnimation(
							mContext, R.anim.fade_in_anim);
					appName.setVisibility(View.VISIBLE);
					loginOptionsHolderLayout.setVisibility(View.VISIBLE);
					appName.setAnimation(animFadein);
					loginOptionsHolderLayout.setAnimation(animFadein);

				}

			}

		}, 2000);

		// Login Buttons Click Handling
		//Facebook Login Button
		final ArrayList<String> permissions=new ArrayList<String>();
		facebookLoginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 //setting facebook permission
			    permissions.add("email");
		      //permissions.add("user_location");
		        permissions.add("user_friends");
		        progress.show();
		        
		        final JSONObject jb=new JSONObject();
		        
		        Session.openActiveSession(SplashActivity.this, true, permissions,new Session.StatusCallback() {

					@SuppressWarnings("deprecation")
					@Override
					public void call(final Session session, SessionState state, Exception exception) {

						if(session.isOpened()){		

							Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

								@Override
								public void onCompleted(GraphUser user, Response response) {

									
								//	Toast.makeText(getApplicationContext(), "hello dev", Toast.LENGTH_SHORT).show();
									if(user != null){
								//		Toast.makeText(getApplicationContext(), "hello "+user.getName(), Toast.LENGTH_SHORT).show();
										 
										String emaila = user.asMap().get("email").toString();
										String userId = user.getId();
										String name1   = user.getName();

									
										try {

											jb.put("provider","Facebook");
											jb.put("user_id",userId.toString());
											String token=session.getAccessToken().toString();
											jb.put("token",token);
										    
											Log.d("f b login ",jb.toString());

											//clear seesion details and close
											session.closeAndClearTokenInformation();
											new LoginTheUser(SplashActivity.this, token, user
																.getId()).execute();
										
										} catch (JSONException e) {
									//		Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
											e.printStackTrace();
										}
									}
									else{
										Toast toast =Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT);
										TextView v2 = (TextView) toast.getView().findViewById(android.R.id.message);
										progress.cancel();
										toast.show();
									}
								}
							});
						}else{
						//	Toast.makeText(getApplicationContext(), "Unable to open Facebook Session", Toast.LENGTH_LONG).show();
						}
					}
				});
		        
			}
		});
		
		
		
		
		
		// Google Plus Login
		googleLoginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
					// Make the call to GoogleApiClient

					try {
						// Check if Goolge Plus App is installed or not
						getPackageManager().getApplicationInfo(
								"com.google.android.apps.plus", 0);
						googleLoginController = new GooglePlusLoginController(
								SplashActivity.this);
						googleLoginController.connect();
						Log.e("google login", "button clicked");
					} catch (PackageManager.NameNotFoundException e) {
						Toast.makeText(
								getApplicationContext(),
								"You have no Google Plus App Installled on your Device. Please try Facebook login.",
								Toast.LENGTH_LONG).show();
					}

				} else {
					Toast.makeText(
							getApplicationContext(),
							"Google Plus Login available for Android Versions grater than GINGERBREAD. Please try Facebook login.",
							Toast.LENGTH_LONG).show();
				}

				// TODO:Go to onConnected method in GooglePlusLoginController
				// class to get the user info and forward him to his home screen
			}
		});

	}

	// Session Callback for facebook
	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {

				Log.d("FacebookSampleActivity", "Facebook session opened");
			} else if (state.isClosed()) {

				Log.d("FacebookSampleActivity", "Facebook session closed");
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy() {
		
		if (googleLoginController != null) {
			googleLoginController.disconnect();
		}
		activityRunning = false;
		super.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && data.getStringExtra("error") != null) {
			Log.e("Facebook Login Error", data.getStringExtra("error"));
		} else {
			if (googleLoginController != null && resultCode != RESULT_CANCELED
					&& requestCode == googleLoginController.getRcSignIn()) {
				googleLoginController.mIntentInProgress = false;

				if (!googleLoginController.isconnecting()) {
					googleLoginController.connect();
				}
			} else if (googleLoginController != null
					&& resultCode != RESULT_CANCELED
					&& requestCode == googleLoginController
							.getREQ_SIGN_IN_REQUIRED()) {
				googleLoginController.requestForAccessToken();
			} else {
				Session.getActiveSession().onActivityResult(this, requestCode,
			            resultCode, data);
			}

		}

	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Logs 'install' and 'app activate' App Events on Facebook - use if
		// needed along with AppEventsLogger.deactivateApp(this); in onPause()
		// to track exact time of app usage
		// AppEventsLogger.activateApp(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		// Close App Events logging on facebook
		// AppEventsLogger.deactivateApp(this);
	}

	@Override
	protected void onStop() {

		super.onStop();
	}

	public int dpToPx(int dp) {
		DisplayMetrics displayMetrics = mContext.getResources()
				.getDisplayMetrics();
		int px = Math.round(dp
				* (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}

	// Intializing GPS Tracker
	private void startGPSTracker() {
		GPSTracker gpsTracker = new GPSTracker(getApplicationContext(),
				dekkohApplication, 5, 1 * 60 * 1000);// Meters : 5 ; Time :
														// 1*60*1000 - 1min
		if (gpsTracker.canGetLocation()) {
			dekkohApplication.updateLocationOfUser(gpsTracker.getLocation());
			// Acquire a reference to the system Location Manager
			LocationManager locationManager = (LocationManager) this
					.getSystemService(Context.LOCATION_SERVICE);
			// Define a listener that responds to location updates
			LocationListener locationListener = new LocationListener() {
				public void onLocationChanged(Location location) {
					dekkohApplication.updateLocationOfUser(location);
				}

				public void onStatusChanged(String provider, int status,
						Bundle extras) {
				}

				public void onProviderEnabled(String provider) {
				}

				public void onProviderDisabled(String provider) {
				}
			};
			if (gpsTracker.isGPSEnabled) {
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, 5, 1 * 60 * 1000,
						locationListener);
			}
			if (gpsTracker.isNetworkEnabled) {
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, 0, 0,
						locationListener);
			}

		} else {
			showSettingsAlert();
		}
	}

	private void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("GPS is settings");

		// Setting Dialog Message
		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						mContext.startActivity(intent);
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	public void sendtoHomeScreen() {
		// TODO Auto-generated method stub
		cancelProgress();
		Intent intent = new Intent(SplashActivity.this, PreHomeScreen.class);
		startActivity(intent);
		finish();
	}

	public void sendtoInterestsScreen() {
		// TODO Auto-generated method stub
		cancelProgress();
		Intent intent = new Intent(SplashActivity.this, PreHomeScreen.class);
		startActivity(intent);
		finish();
	}

	public boolean updateDekkohUserObject() {
		DekkohUser dekkohUser = new DekkohUser();
		SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager
				.getInstance(this);
		dekkohUser.setEmail(sharedPreferenceManager
				.getString(SharedPreferenceConstants.DEKKOH_USER_EMAIL));
		dekkohUser.setDekkohUserID(String.valueOf(sharedPreferenceManager
				.getString(SharedPreferenceConstants.DEKKOH_USER_ID)));
		dekkohUser.setName(sharedPreferenceManager
				.getString(SharedPreferenceConstants.DEKKOH_USER_NAME));
		dekkohUser.setProfilePic(sharedPreferenceManager
				.getString(SharedPreferenceConstants.DEKKOH_USER_PROFILEPIC));
		dekkohApplication.setDekkohUser(dekkohUser);
		return true;
	}

	public void showProgress() {
		if (activityRunning == true) {
			progress.show();
		}
	}

	public void cancelProgress() {
		if (activityRunning == true) {

			progress.cancel();
		}
	}
}

class LoginTheUser extends AsyncTask<Void, Void, Void> {
	SplashActivity activity;
	String token;
	String userId = "";

	public LoginTheUser(SplashActivity activity, String token, String userId) {

		this.activity = activity;
		this.token = token;
		this.userId = userId;

	}

	@Override
	protected Void doInBackground(Void... params) {

		try {
			DekkohUser dekkohUser = APIProcessor.loginUserWithFacebook(
					activity, userId, token, null);
			if (dekkohUser != null) {

				if (dekkohUser.getInterestIds().size() == 0) {
					activity.sendtoInterestsScreen();
				} else {
					activity.sendtoHomeScreen();
				}

			} else {
				// Toast.makeText(getApplicationContext(),
				// "Unable to Login ... Please Try Again.",
				// Toast.LENGTH_LONG).show();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Toast.makeText(getApplicationContext(),
			// "Unable to Login ... Please Try Again.\n"+e.getLocalizedMessage(),
			// Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

		return null;
		// TODO Auto-generated method stub

	}

	protected void onPostExecute(Void result) {

	}

}
