package com.dekkoh.myprofile;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dekkoh.R;
import com.dekkoh.application.BaseFragment;
import com.dekkoh.custom.view.CircularImageView;
import com.dekkoh.custom.view.UserProfileChart;

public class MyProfile extends BaseFragment {
	private UserProfileChart userProfileChart;
	private CircularImageView userCircularImageView;
	private TextView userConnectionsCountTextView;
	private TextView userKarmaPointsCountTextView;
	private TextView userQuestionsCountTextView;
	private TextView userAnswerssCountTextView;
	private TextView userLikesCountTextView;
	private TextView userThanksCountTextView;
	private TextView interestNameTextView;
	private TextView interestQuestionsCountTextView;
	private TextView interestAnswersCountTextView;
	private TextView interestLikesCountTextView;
	private TextView interestThanksCountTextView;

	public MyProfile() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.myprofile_fragment,
				container, false);
		userProfileChart = (UserProfileChart) rootView
				.findViewById(R.id.userProfileChart);
		userCircularImageView = (CircularImageView) rootView
				.findViewById(R.id.userCircularImageView);
		userConnectionsCountTextView = (TextView) rootView
				.findViewById(R.id.userConnectionsCountTextView);
		userKarmaPointsCountTextView = (TextView) rootView
				.findViewById(R.id.userKarmaPointsCountTextView);
		userQuestionsCountTextView = (TextView) rootView
				.findViewById(R.id.userQuestionsCountTextView);
		userAnswerssCountTextView = (TextView) rootView
				.findViewById(R.id.userAnswerssCountTextView);
		userLikesCountTextView = (TextView) rootView
				.findViewById(R.id.userLikesCountTextView);
		userThanksCountTextView = (TextView) rootView
				.findViewById(R.id.userThanksCountTextView);
		interestNameTextView = (TextView) rootView
				.findViewById(R.id.interestNameTextView);
		interestQuestionsCountTextView = (TextView) rootView
				.findViewById(R.id.interestQuestionsCountTextView);
		interestAnswersCountTextView = (TextView) rootView
				.findViewById(R.id.interestAnswersCountTextView);
		interestLikesCountTextView = (TextView) rootView
				.findViewById(R.id.interestLikesCountTextView);
		interestThanksCountTextView = (TextView) rootView
				.findViewById(R.id.interestThanksCountTextView);
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showTabs();
		Resources res = getResources();

		userProfileChart.addItem(2,
				res.getColor(R.color.entertainment_and_event));
		userProfileChart.addItem(3, res.getColor(R.color.art_and_culture));
		userProfileChart.addItem(1, res.getColor(R.color.pub_and_nightlife));
		userProfileChart.addItem(3, res.getColor(R.color.food_and_resturant));
		userProfileChart.addItem(2,
				res.getColor(R.color.shopping_and_lifestyle));
		userProfileChart.addItem(5,
				res.getColor(R.color.event_and_entertainment));
		userProfileChart.addItem(4, res.getColor(R.color.miscellaneous));
	}
}
