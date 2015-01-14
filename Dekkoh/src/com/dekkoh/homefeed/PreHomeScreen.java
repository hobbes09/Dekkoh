package com.dekkoh.homefeed;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.dekkoh.R;
import com.dekkoh.application.BaseActivity;
import com.dekkoh.datamodel.Question;
import com.dekkoh.service.APIProcessor;

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
		
		new FetchQuestionTask().execute();  
	}
	
	public class FetchQuestionTask extends AsyncTask<Void, Void, List<Question>>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialogHandler.showCustomProgressDialog(activity);
		}
		
		@Override
		protected List<Question> doInBackground(Void... params) {
			try {
				return APIProcessor.getQuestions(activity, 0, 20, 0, 0, null, null, 0, null, true, null, null, null);			
				} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
			
		}

		@Override
		protected void onPostExecute(List<Question> qlist) {
			if(qlist!=null){
				progressDialogHandler.dismissCustomProgressDialog(activity);
				QuestionContentManager.getInstance().setQuestionList(qlist);
				Intent intent = new Intent();
				intent.setClass(activity, HomeScreen.class);
				startActivity(intent);
			}
		}
	}


}
