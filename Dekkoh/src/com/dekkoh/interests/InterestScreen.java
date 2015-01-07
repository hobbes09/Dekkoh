package com.dekkoh.interests;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.dekkoh.R;
import com.dekkoh.application.ApplicationState;
import com.dekkoh.datamodel.Question;
import com.dekkoh.homefeed.HomeScreen;
import com.dekkoh.homefeed.QuestionContentManager;
import com.dekkoh.service.APIProcessor;

public class InterestScreen extends Activity implements Runnable{
	
	private static TextView tv;
	Thread threadQuestionUpdater;
	Question q1, q2;
	List<Question> newQuestionList;
	
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
				q1 = newQuestionList.get(0);
				q2 = newQuestionList.get(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			tv.setText(q1.getQuestion()+"\n"+ q2.getQuestion());
			
	  }

	@Override
	public void run() {
		try {
			newQuestionList = APIProcessor.getQuestions(InterestScreen.this, ApplicationState.getHomefeedQuestion_Offset(), ApplicationState.getHomefeedQuestion_Limit(), 0, 0, null, null, 0, null, false, null, null, null);
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
