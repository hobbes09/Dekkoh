package com.dekkoh.googleplusloginhandler;

import java.io.IOException;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.dekkoh.datamodel.DekkohUser;
import com.dekkoh.interests.InterestScreen;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.splash.SplashActivity;
import com.dekkoh.util.Log;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class GooglePlusLoginController implements ConnectionCallbacks, OnConnectionFailedListener {

	private static final String TAG = "RetrieveAccessToken";
    private static final int REQ_SIGN_IN_REQUIRED = 55664;
    private static final int RC_SIGN_IN = 0;
    
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    public boolean mIntentInProgress;

    private SplashActivity activity;
    
    //user Email
    public String email="";
    public String userId="";

    public GooglePlusLoginController(SplashActivity activity) {
        this.activity = activity;


        Log.e("google login", "Class Started");
        
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API)
        .addScope(Plus.SCOPE_PLUS_LOGIN)
        .build();
       
    }

    
    public void connect(){
    	mGoogleApiClient.connect();
    }
    
    public void disconnect(){
    	if (mGoogleApiClient.isConnected()) {
    	      mGoogleApiClient.disconnect();
    	    }
    }
    
    public boolean isconnecting(){
    	return mGoogleApiClient.isConnecting();
    }
    
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		
		Log.e("google login", result.toString());
		if (!mIntentInProgress && result.hasResolution()) {
		    try {
		      mIntentInProgress = true;
		      result.startResolutionForResult(activity, getRcSignIn());
		    //  activity.startIntentSenderForResult(result.getResolution().getIntentSender(),
		     //     RC_SIGN_IN, null, 0, 0, 0);
		    } catch (SendIntentException e) {
		      // The intent was canceled before it was sent.  Return to the default
		      // state and attempt to connect to get an updated ConnectionResult.
		    	Log.e("google login", e);
		      mIntentInProgress = false;
		      mGoogleApiClient.connect();
		    }
		  }
	}

	@Override
	public void onConnected(Bundle arg0) {
		
		Log.e("google login", "Connected");
		  if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
			    
			    Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
			     userId = currentPerson.getId();
			    //String personName = currentPerson.getDisplayName();
			   // String personPhotoUrl = currentPerson.getImage().getUrl();
			   // String personGooglePlusProfile = currentPerson.getUrl();
			    email = Plus.AccountApi.getAccountName(mGoogleApiClient);
			    new RetrieveTokenTask(activity,userId).execute(email);
			   // Toast.makeText(activity.getApplicationContext(), email+" "+personName, Toast.LENGTH_LONG).show();
			   // Log.e("google login", email+" "+personName);  
		  }
	}

	

	@Override
	public void onConnectionSuspended(int arg0) {
		
		 mGoogleApiClient.connect();
	}


	public int getRcSignIn() {
		return RC_SIGN_IN;
	}
	
	public int getREQ_SIGN_IN_REQUIRED(){
		return REQ_SIGN_IN_REQUIRED;
	}
	
	public void requestForAccessToken(){
		 new RetrieveTokenTask(activity,userId).execute(email);
	}

	
	private class RetrieveTokenTask extends AsyncTask<String, Void, String> {
		 
		SplashActivity activity;
		String email="",userId="";
		
		RetrieveTokenTask(SplashActivity activity,String userId){
			this.activity=activity;
			this.userId=userId;
		}
		
        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            email=accountName;
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(activity.getApplicationContext(), accountName, scopes);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } catch (UserRecoverableAuthException e) {
                activity.startActivityForResult(e.getIntent(), REQ_SIGN_IN_REQUIRED);
            } catch (GoogleAuthException e) {
                Log.e(TAG, e.getMessage());
            }
            try {
            	DekkohUser dekkohUser=APIProcessor.loginUserWithGoogle(activity, userId,token, email);
						if(dekkohUser!=null){
							
							Intent intent=new Intent(activity,InterestScreen.class);
							activity.startActivity(intent);
							activity.finish();
							
						}else{
							
							//Toast.makeText(activity.getApplicationContext(), "Unable to Login ... Please Try Again.", Toast.LENGTH_LONG).show();
							
								}
							}catch (Exception e) {
						// TODO Auto-generated catch block
					//	Toast.makeText(activity.getApplicationContext(), "Unable to Login ... Please Try Again.\n"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}	
            return token;
        }
 
        @Override
        protected void onPostExecute(final String token) {
            super.onPostExecute(token);
           
        }
    }

}