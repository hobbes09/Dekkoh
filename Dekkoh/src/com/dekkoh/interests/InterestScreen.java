package com.dekkoh.interests;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.dekkoh.R;
import com.dekkoh.datamodel.Question;
import com.dekkoh.homefeed.QuestionContentManager;

public class InterestScreen extends Activity implements Runnable{
	
	private static TextView tv;
	Thread threadQuestionUpdater;
	Question q1, q2;
	
	  @Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);	
	  
			//User Redirected here if sharedPreferenceManager.getBoolean(SharedPreferenceConstants.DEKKOH_USER_HAVE_INTERESTS)==false so set it to true once user enters and saves interests
			setContentView(R.layout.interests_activity_layout);	
			
			tv = (TextView)findViewById(R.id.tv);
			
			threadQuestionUpdater = new Thread("Question Updater");
			threadQuestionUpdater.start();
			if(threadQuestionUpdater!=null){
				try {
					threadQuestionUpdater.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			try {
				q1 = QuestionContentManager.getQuestionFromExternalStorage();
				q2 = QuestionContentManager.getQuestionFromExternalStorage();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			tv.setText(q1.getQuestion()+"\n"+ q2.getQuestion());
			
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
