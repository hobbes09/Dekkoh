package com.dekkoh.myactivity;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dekkoh.R;
import com.dekkoh.application.BaseFragment;
import com.dekkoh.custom.adapter.MyQuestionsAdapter;
import com.dekkoh.datamodel.Question;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.util.Log;

public class MyQuestionsFragment extends BaseFragment {
	private ListView myQuestionsListView;

	public MyQuestionsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.my_questions_fragment, container,
				false);
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showTabs();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		myQuestionsListView = (ListView) rootView
				.findViewById(R.id.myActivityListView);
		new GetQuestionListTask().execute();
	}

	private class GetQuestionListTask extends
			AsyncTask<Void, Void, List<Question>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialogHandler.showCustomProgressDialog(activity);
		}

		@Override
		protected List<Question> doInBackground(Void... params) {
			try {
				// return APIProcessor.getUserQuestionList(activity, 0, 20, 0,
				// 0,
				// null);
				return APIProcessor.getQuestions(activity, 0, 50, 0, 0, null,
						null, 0, null, true, null, null, null);
			} catch (Exception e) {
				Log.e(TAG, e);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Question> result) {
			super.onPostExecute(result);
			progressDialogHandler.dismissCustomProgressDialog(activity);
			if (result != null) {
				myQuestionsListView.setAdapter(new MyQuestionsAdapter(activity,
						R.layout.my_question_list_item, result));
			}
		}

	}
}
