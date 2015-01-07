package com.dekkoh.interests;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.dekkoh.R;
import com.dekkoh.application.ApplicationState;
import com.dekkoh.datamodel.Question;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.util.Log;

public class InterestScreen extends Activity {

	private static TextView tv;
	Question q1, q2;
	List<Question> newQuestionList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// User Redirected here if
		// sharedPreferenceManager.getBoolean(SharedPreferenceConstants.DEKKOH_USER_HAVE_INTERESTS)==false
		// so set it to true once user enters and saves interests
		setContentView(R.layout.interests_activity_layout);
		tv = (TextView) findViewById(R.id.tv);
		new TestTask().execute();
	}

	class TestTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				newQuestionList = APIProcessor.getQuestions(
						InterestScreen.this,
						ApplicationState.getHomefeedQuestion_Offset(),
						ApplicationState.getHomefeedQuestion_Limit(), 0, 0,
						null, null, 0, null, false, null, null, null);
				q1 = newQuestionList.get(0);
				q2 = newQuestionList.get(1);
			} catch (Exception e) {
				Log.e(e);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (q1 != null && q2 != null)
				tv.setText(q1.getQuestion() + "\n" + q2.getQuestion());
		}

	}
}
