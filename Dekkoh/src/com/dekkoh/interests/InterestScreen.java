package com.dekkoh.interests;

import android.app.Activity;
import android.os.Bundle;

import com.dekkoh.R;

public class InterestScreen extends Activity{
	  @Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);	
	  
			//User Redirected here if sharedPreferenceManager.getBoolean(SharedPreferenceConstants.DEKKOH_USER_HAVE_INTERESTS)==false so set it to true once user enters and saves interests
			setContentView(R.layout.interests_activity_layout);	
	  
	  }
}
