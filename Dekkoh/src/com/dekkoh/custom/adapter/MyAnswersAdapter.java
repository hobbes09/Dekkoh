package com.dekkoh.custom.adapter;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dekkoh.R;
import com.dekkoh.custom.view.CircularImageView;
import com.dekkoh.datamodel.Question;
import com.kavyasoni.gallery.ui.helper.ImageCache.ImageCacheParams;
import com.kavyasoni.gallery.ui.helper.ImageFetcher;
import com.kavyasoni.gallery.ui.helper.RemoteImageFetcher;

public class MyAnswersAdapter extends ArrayAdapter<Question> {
	private List<Question> questionsList;
	private LayoutInflater layoutInflater;
	private ImageFetcher mImageFetcher;
	private static final String IMAGE_CACHE_DIR = ".gallery/cache";

	public MyAnswersAdapter(Activity activity, int textViewResourceId,
			List<Question> questionsList, FragmentManager fragmentManager) {
		super(activity, textViewResourceId, questionsList);
		this.questionsList = questionsList;
		layoutInflater = activity.getLayoutInflater();
		ImageCacheParams cacheParams = new ImageCacheParams(activity,
				IMAGE_CACHE_DIR);
		// Set memory cache to 25% of app memory
		cacheParams.setMemCacheSizePercent(0.25f);
		mImageFetcher = new RemoteImageFetcher(activity, 70);
		mImageFetcher.setLoadingImage(R.drawable.loding_album);
		mImageFetcher.addImageCache(fragmentManager, cacheParams);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Question question = questionsList.get(position);
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.my_answers_list_item, null);
		}
		TextView locationTextView = (TextView) convertView
				.findViewById(R.id.locationTextView);
		TextView questionTextView = (TextView) convertView
				.findViewById(R.id.questionTextView);

		locationTextView.setText(question.getLocation());
		questionTextView.setText(question.getQuestion());
		if (!TextUtils.isEmpty(question.getUserImage())) {
			CircularImageView circularImageView = (CircularImageView) convertView
					.findViewById(R.id.userCircularImageView);
			mImageFetcher.loadImage(question.getUserImage(), circularImageView);
		}

		return convertView;
	}

}
