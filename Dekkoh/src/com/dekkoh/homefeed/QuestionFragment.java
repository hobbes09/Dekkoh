package com.dekkoh.homefeed;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dekkoh.R;
import com.dekkoh.application.ApplicationState;
import com.dekkoh.application.BaseFragment;
import com.dekkoh.custom.view.CircularImageView;
import com.dekkoh.custom.view.SquareLinearLayout;
import com.dekkoh.datamodel.Question;
import com.dekkoh.util.CommonUtils;
import com.kavyasoni.gallery.ui.helper.ImageCache.ImageCacheParams;
import com.kavyasoni.gallery.ui.helper.ImageFetcher;
import com.kavyasoni.gallery.ui.helper.RemoteImageFetcher;

public class QuestionFragment extends BaseFragment{
	
	private float xDown, xUp, yDown, yUp;
	private int SWIPE_ACTION = 0;
	private TextView tvLocation;
	private TextView tvUsername;
	private TextView tvQuestion;
	private TextView tvNumAnswers;
	private ImageView ivHomeImage;
	private CircularImageView ivProfilePic;
	private SquareLinearLayout sllBack; 
	private SquareLinearLayout sllShare;
	private SquareLinearLayout sllLike;
	private SquareLinearLayout sllFollow;
	
	private Question question;
	private int countLike = 0;
	private int countFollow = 0;
	
	private static final String IMAGE_CACHE_DIR = ".gallery/cache";
	private ImageFetcher profileImageFetcher;
	private ImageFetcher questionImageFetcher;
	
		
	QuestionFragment(){	
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.question_fragment, null);
	
		initializeTouchListeners(root);
		initialize(root);
		setValues();
        return root;		
	}

	private void initializeTouchListeners(View root) {

		root.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    xDown = event.getX();
                    yDown = event.getY();                    
                }
                
                if(event.getAction() == MotionEvent.ACTION_UP){
                	xUp = event.getX();
                	yUp = event.getY();  
                	
                	SWIPE_ACTION = FragmentTransitionManager.detectSwipe(xDown, xUp, yDown, yUp);
                	switch(SWIPE_ACTION){
                	case FragmentTransitionManager.SWIPE_DOWN:
                				
                	case FragmentTransitionManager.SWIPE_UP:
                				
                				break;
                	case FragmentTransitionManager.SWIPE_RIGHT: 
                				if(ApplicationState.getHomefeedQuestion_CurrentIndex()!=0){
	                				QuestionFragment nextQuestionFragment = new QuestionFragment();
	                				nextQuestionFragment.setArguments(QuestionContentManager.getNextQuestionBundle(activity));
			        				FragmentTransaction transactionPrevious = HomeScreen.supportFragmentManager.beginTransaction();
			        				transactionPrevious.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
			        				transactionPrevious.replace(R.id.contentHomeActivity, nextQuestionFragment);
			        			    transactionPrevious.commit();
                				}
                				break;
                	case FragmentTransitionManager.SWIPE_LEFT: 
                				QuestionFragment previousQuestionFragment = new QuestionFragment();
                				previousQuestionFragment.setArguments(QuestionContentManager.getPreviousQuestionBundle(activity));
                				FragmentTransaction transactionNext = HomeScreen.supportFragmentManager.beginTransaction();
                				transactionNext.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                				transactionNext.replace(R.id.contentHomeActivity, previousQuestionFragment);
                			    transactionNext.commit();
                				break;
                	case FragmentTransitionManager.SWIPE_NULL:
                				Toast.makeText(getActivity(), "Swipe null Detected", Toast.LENGTH_SHORT).show();
                				break;               	
                	}
                	
                	xDown = yDown = xUp = yUp = 0; 	
                	
                }              
                return true;
            }
		});
		
	}

	private void setValues() {
		tvLocation.setText(getArguments().getString("LOCATION"));
		tvUsername.setText(getArguments().getString("USERNAME"));
		tvQuestion.setText(getArguments().getString("QUESTION"));
		tvNumAnswers.setText(getArguments().getString("NUM_ANSWERS"));
		if (CommonUtils.isValidURL(getArguments().get("QUESTION_PIC") + "")) {
			questionImageFetcher.loadImage(getArguments().get("QUESTION_PIC"),
					ivHomeImage);
		} else {
			ivHomeImage.setImageResource(R.drawable.no_image_available);
		}
		if (CommonUtils.isValidURL(getArguments().get("PROFILE_PIC") + "")) {
			profileImageFetcher.loadImage(getArguments().get("PROFILE_PIC"),
					ivProfilePic);
		} else {
			ivProfilePic.setImageResource(R.drawable.ic_noprofilepic);
		}
	}


	private void initialize(View root) {
		tvLocation = (TextView)root.findViewById(R.id.tvLocation);
		tvUsername = (TextView)root.findViewById(R.id.tvUsername);
		tvQuestion = (TextView)root.findViewById(R.id.tvQuestion);
		tvNumAnswers = (TextView)root.findViewById(R.id.tvNumAnswers);
		ivHomeImage = (ImageView)root.findViewById(R.id.ivHomeImage);
		ivProfilePic = (CircularImageView)root.findViewById(R.id.ivProfilePic);
		sllBack = (SquareLinearLayout)root.findViewById(R.id.sllBack);
		sllShare = (SquareLinearLayout)root.findViewById(R.id.sllShare);
		sllLike = (SquareLinearLayout)root.findViewById(R.id.sllLike);
		sllFollow = (SquareLinearLayout)root.findViewById(R.id.sllFollow);
		
		ImageCacheParams cacheParams = new ImageCacheParams(activity, IMAGE_CACHE_DIR);
		// Set memory cache to 25% of app memory
		cacheParams.setMemCacheSizePercent(0.25f);
		profileImageFetcher = new RemoteImageFetcher(activity, 100);
		profileImageFetcher.setLoadingImage(R.drawable.loding_album);
		profileImageFetcher.addImageCache(HomeScreen.supportFragmentManager, cacheParams);
		questionImageFetcher = new RemoteImageFetcher(activity, 500);
		questionImageFetcher.setLoadingImage(R.drawable.loding_album);
		questionImageFetcher.addImageCache(HomeScreen.supportFragmentManager, cacheParams);
	}

}
