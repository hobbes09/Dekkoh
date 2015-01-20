
package com.dekkoh.custom.adapter;

import java.util.Date;
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
import com.dekkoh.util.CommonUtils;
import com.kavyasoni.gallery.ui.helper.ImageCache.ImageCacheParams;
import com.kavyasoni.gallery.ui.helper.ImageFetcher;
import com.kavyasoni.gallery.ui.helper.RemoteImageFetcher;

public class MyAnswersAdapter extends ArrayAdapter<Question> {
    private List<Question> questionsList;
    private LayoutInflater layoutInflater;
    private ImageFetcher mImageFetcher;
    private static final String IMAGE_CACHE_DIR = ".dekkoh/cache";

    public MyAnswersAdapter(Activity activity, int textViewResourceId,
            List<Question> questionsList, FragmentManager fragmentManager) {
        super(activity, textViewResourceId, questionsList);
        this.questionsList = questionsList;
        layoutInflater = activity.getLayoutInflater();
        ImageCacheParams cacheParams = new ImageCacheParams(activity,
                IMAGE_CACHE_DIR);
        // Set memory cache to 25% of app memory
        cacheParams.setMemCacheSizePercent(0.25f);
        mImageFetcher = new RemoteImageFetcher(activity, 100);
        mImageFetcher.setLoadingImage(R.drawable.loding_album);
        mImageFetcher.addImageCache(fragmentManager, cacheParams);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Question question = questionsList.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.my_answers_list_item,
                    null);
        }
        TextView locationTextView = (TextView) convertView
                .findViewById(R.id.locationTextView);
        TextView questionTextView = (TextView) convertView
                .findViewById(R.id.questionTextView);
        TextView followersTextView = (TextView) convertView
                .findViewById(R.id.followsTextView);
        TextView answersTextView = (TextView) convertView
                .findViewById(R.id.answersTextView);
        TextView userNameTextView = (TextView) convertView
                .findViewById(R.id.userNameTextView);
        TextView createdTextView = (TextView) convertView
                .findViewById(R.id.createdTextView);
        createdTextView.setText(CommonUtils.getDateDifference(
                question.getDate(), new Date()));
        userNameTextView.setText(CommonUtils.getFirstNameFromFullName(question
                .getUserName()));
        if (TextUtils.isEmpty(question.getLocation())) {
            locationTextView.setVisibility(View.GONE);
        }
        locationTextView.setText(question.getLocation());
        questionTextView.setText(question.getQuestion());
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
        CircularImageView circularImageView = (CircularImageView) convertView
                .findViewById(R.id.userCircularImageView);
        if (CommonUtils.isValidURL(question.getUserImage())) {
            mImageFetcher.loadImage(question.getUserImage(), circularImageView);
        } else {
            circularImageView.setImageResource(R.drawable.ic_noprofilepic);
        }
        return convertView;
    }
}
