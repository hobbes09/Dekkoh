package com.dekkoh.custom.adapter;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dekkoh.R;
import com.dekkoh.datamodel.Question;
import com.dekkoh.util.CommonUtils;

public class MyQuestionsAdapter extends ArrayAdapter<Question> {
	private List<Question> questionsList;
	private LayoutInflater layoutInflater;

	public MyQuestionsAdapter(Activity activity, int textViewResourceId,
			List<Question> questionsList) {
		super(activity, textViewResourceId, questionsList);
		this.questionsList = questionsList;
		layoutInflater = activity.getLayoutInflater();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Question question = questionsList.get(position);
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.my_question_list_item, null);
		}
		TextView locationTextView = (TextView) convertView
				.findViewById(R.id.locationTextView);
		TextView createdTextView = (TextView) convertView
				.findViewById(R.id.createdTextView);
		TextView questionTextView = (TextView) convertView
				.findViewById(R.id.questionTextView);
		TextView followersTextView = (TextView) convertView
				.findViewById(R.id.followsTextView);
		TextView answersTextView = (TextView) convertView
				.findViewById(R.id.answersTextView);
		locationTextView.setText(question.getLocation());
		questionTextView.setText(question.getQuestion());
		createdTextView.setText(CommonUtils.getDateDifference(
				question.getDate(), new Date()));
		if (question.getFollowCount() > 0) {
			if (question.getFollowCount() > 1)
				followersTextView.setText(question.getFollowCount()
						+ " followers");
			else
				followersTextView.setText(question.getFollowCount()
						+ " follower");
		} else {
			followersTextView.setVisibility(View.GONE);
		}
		if (question.getAnswerCount() > 0) {
			if (question.getAnswerCount() > 1)
				answersTextView.setText(question.getAnswerCount() + " answers");
			else
				answersTextView.setText(question.getAnswerCount() + " answer");
		} else {
			answersTextView.setVisibility(View.GONE);
		}
		return convertView;
	}

}
