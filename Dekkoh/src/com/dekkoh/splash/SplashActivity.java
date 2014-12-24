package com.dekkoh.splash;

import java.io.IOException;
import java.util.Arrays;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
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
import com.dekkoh.application.DekkohApplication;
import com.dekkoh.gpstracker.GPSTracker;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.util.Log;
import com.facebook.FacebookException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;


public class SplashActivity extends Activity {

	
	private TextView connectWith;
	private ImageView appLogo,googleLoginButton,appName;
	private LoginButton facebookLoginButton;
	private RelativeLayout loginOptionsHolderLayout;
	private Context mContext;
	private UiLifecycleHelper uiHelper;
	private DekkohApplication dekkohApplication;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_layout);
        
        mContext=getApplicationContext();
        
        dekkohApplication = (DekkohApplication)getApplication();
        
        //Start GPS
        startGPSTracker();
        
        //UIIHelper for facebook
        uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);
        
        //Instantiate
        appName = (ImageView)findViewById(R.id.splash_activity_layout_app_name);
        connectWith = (TextView)findViewById(R.id.splash_activity_layout_connect_with_text);
        appLogo = (ImageView)findViewById(R.id.splash_activity_layout_logo_image);
        facebookLoginButton = (LoginButton)findViewById(R.id.splash_activity_layout_facebook_login_button);
        googleLoginButton = (ImageView)findViewById(R.id.splash_activity_layout_google_login_button);
        loginOptionsHolderLayout = (RelativeLayout)findViewById(R.id.splash_activity_layout_login_options_holder);
        
        
        //Font Style for TextViews
        Typeface type_light = Typeface.createFromAsset(mContext.getAssets(),"fonts/source_sans_pro_light.ttf"); 
         connectWith.setTypeface(type_light);
        
        //Animations
       Handler handler=new Handler();
       handler.postDelayed(new Runnable(){

		@Override
		public void run() {
			//Make other views Visible and translate with scaling of logo to position
			 AnimationSet animSet = new AnimationSet(true);
			TranslateAnimation anim_move = new TranslateAnimation(0, 0, 0, -dpToPx(200));
			anim_move.setDuration(1000);
			animSet.addAnimation(anim_move);
			ScaleAnimation anim_scale = new ScaleAnimation(1f, 0.75f, 1f, 0.75f,50f,50f);
			anim_scale.setDuration(1000);
			animSet.addAnimation(anim_scale);
			animSet.setFillAfter(true);
			appLogo.startAnimation(animSet);
			Animation animFadein = AnimationUtils.loadAnimation(mContext,
	                R.anim.fade_in_anim);
			appName.setVisibility(View.VISIBLE);
			loginOptionsHolderLayout.setVisibility(View.VISIBLE);
			appName.setAnimation(animFadein);
			loginOptionsHolderLayout.setAnimation(animFadein);
			
		}
    	   
       }, 2000);
       
        
       //Login Buttons Click Handling
       //Facebook
       facebookLoginButton.setBackgroundResource(R.drawable.facebook_login_button_image);
       facebookLoginButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
       facebookLoginButton.setCompoundDrawablePadding(0);
       facebookLoginButton.setPadding(0, 0, 0, 0);
       facebookLoginButton.setOnErrorListener(new OnErrorListener() {
    	   
    	   @Override
    	   public void onError(FacebookException error) {
    	    Log.i("error", "Error " + error.getMessage());
    	   }
    	  });
       facebookLoginButton.setReadPermissions(Arrays.asList("public_profile","email","user_friends"));
       facebookLoginButton.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {
				if (user != null) {
					Toast.makeText(mContext, "Name:"+user.getName()+"\n"+"Email:"+user.asMap().get("email").toString(), Toast.LENGTH_LONG).show();
				} else {
					
				}
			}
		});
      
       //Google Plus
       googleLoginButton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO integrate with Google Plus SDK
			
		}
	});
			
       new Samplecall().execute();
       
        
    }

    //Session Callback for facebook
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
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}
    @Override
    protected void onResume() {
      super.onResume();

      uiHelper.onResume();
      // Logs 'install' and 'app activate' App Events on Facebook - use if needed along with AppEventsLogger.deactivateApp(this); in onPause() to track exact time of app usage
     // AppEventsLogger.activateApp(this);
    }
    
    @Override
    protected void onPause() {
      super.onPause();
      uiHelper.onPause();
      
      //Close App Events logging on facebook
      //AppEventsLogger.deactivateApp(this);
    }
    
    
    
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
        return px;
    }
    
    //Intializing GPS Tracker
    private void startGPSTracker() {
		// TODO Auto-generated method stub
		GPSTracker gpsTracker=new GPSTracker(getApplicationContext(),dekkohApplication,5,1*60*1000);//Meters : 5 ; Time : 1*60*1000 - 1min
	    if(gpsTracker.canGetLocation()){
	    	dekkohApplication.updateLocationOfUser(gpsTracker.getLocation());
	    	// Acquire a reference to the system Location Manager 
			LocationManager locationManager = (LocationManager) this .getSystemService(Context.LOCATION_SERVICE); 
			// Define a listener that responds to location updates 
			LocationListener locationListener = new LocationListener() 
			{ 
				public void onLocationChanged(Location location) 
				{ 
					dekkohApplication.updateLocationOfUser(location);
				} 
				public void onStatusChanged(String provider, int status, Bundle extras) 
				{ } 
				public void onProviderEnabled(String provider) { } 
				public void onProviderDisabled(String provider) { } 
				}; 
				if(gpsTracker.isGPSEnabled){
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5,1*60*1000, locationListener); 
				}
				if(gpsTracker.isNetworkEnabled){
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener); 
				}
				
	    }else{
	    	showSettingsAlert();
	    }
	}
    
    
    
    private void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    
    class Samplecall extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				APIProcessor.getInteresetList();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.e(e);
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(e);
			}
			return false;
		}
    	
    }
    
}
