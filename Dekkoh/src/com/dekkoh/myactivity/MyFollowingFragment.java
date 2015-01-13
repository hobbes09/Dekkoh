package com.dekkoh.myactivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dekkoh.R;
import com.dekkoh.application.BaseFragment;
import com.dekkoh.custom.view.UserProfileChart;

public class MyFollowingFragment extends BaseFragment {

	public MyFollowingFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.myprofile_fragment,
				container, false);
		UserProfileChart assignmentPieChartView = (UserProfileChart) rootView
				.findViewById(R.id.Pie);
		Resources res = getResources();
		assignmentPieChartView.addItem(2,
				res.getColor(R.color.entertainment_and_event));
		assignmentPieChartView
				.addItem(3, res.getColor(R.color.art_and_culture));
		assignmentPieChartView.addItem(1,
				res.getColor(R.color.pub_and_nightlife));
		assignmentPieChartView.addItem(3,
				res.getColor(R.color.food_and_resturant));
		assignmentPieChartView.addItem(2,
				res.getColor(R.color.shopping_and_lifestyle));
		assignmentPieChartView.addItem(5,
				res.getColor(R.color.event_and_entertainment));
		assignmentPieChartView.addItem(4, res.getColor(R.color.miscellaneous));
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showTabs();
	}
}
