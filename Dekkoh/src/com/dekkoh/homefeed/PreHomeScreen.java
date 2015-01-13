package com.dekkoh.homefeed;

import java.util.List;

import android.os.Bundle;
import android.widget.TextView;

import com.dekkoh.R;
import com.dekkoh.application.BaseActivity;
import com.dekkoh.datamodel.Question;

public class PreHomeScreen extends BaseActivity{
	
	TextView tv;
	List<Question> qlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prehomescreen_activity);
		tv = (TextView)findViewById(R.id.tv);
		tv.setText("Patience is a virtue.");
		
	}

}
