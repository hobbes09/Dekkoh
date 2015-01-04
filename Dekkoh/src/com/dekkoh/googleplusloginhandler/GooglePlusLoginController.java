package com.dekkoh.googleplusloginhandler;

import android.app.Activity;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.widget.Toast;

import com.dekkoh.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class GooglePlusLoginController implements ConnectionCallbacks, OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 0;
    
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    public boolean mIntentInProgress;

    private Activity activity;

    public GooglePlusLoginController(Activity activity) {
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
			    String userToken = currentPerson.getId();
			    String personName = currentPerson.getDisplayName();
			    String personPhotoUrl = currentPerson.getImage().getUrl();
			    String personGooglePlusProfile = currentPerson.getUrl();
			    String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
			    Toast.makeText(activity.getApplicationContext(), email+" "+personName, Toast.LENGTH_LONG).show();
			    Log.e("google login", email+" "+personName);  
		  }
	}

	

	@Override
	public void onConnectionSuspended(int arg0) {
		
		 mGoogleApiClient.connect();
	}


	public int getRcSignIn() {
		return RC_SIGN_IN;
	}


}