package com.dekkoh.homefeed;

import com.dekkoh.R;
import com.dekkoh.custom.view.CircularImageView;
import com.dekkoh.custom.view.SquareLinearLayout;
import com.dekkoh.datamodel.Question;
import com.dekkoh.util.CommonUtils;
import com.kavyasoni.gallery.ui.helper.ImageFetcher;
import com.kavyasoni.gallery.ui.helper.RemoteImageFetcher;
import com.kavyasoni.gallery.ui.helper.ImageCache.ImageCacheParams;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuestionCardView {
	
	private int windowWidth, windowHeight;
	private int screenCenterX, screenCenterY;
	private int x_current, y_current, x_initial, y_initial;
	private int initPosX = 0, initPosY = 0;
	private RelativeLayout parentView;
	private Context context;
	
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
	private LinearLayout questionFragmentLayout;

	private Question question;
	private int countLike = 0;
	private int countFollow = 0;

	private static final String IMAGE_CACHE_DIR = ".gallery/cache";
	private ImageFetcher profileImageFetcher;
	private ImageFetcher questionImageFetcher;
	
	private static QuestionCardView questionCardView = new QuestionCardView();
	
	private QuestionCardView(){
	}
	
	public static QuestionCardView getInstance(){
		return questionCardView;
	}
	
	public static void setWindowWidth(int windowWidth){
		getInstance().windowWidth = windowWidth;
	}
	
	public static void setWindowHeight(int windowHeight){
		getInstance().windowHeight = windowHeight;
	}
	
	public static void setScreenCenterX(int screenCenterX){
		getInstance().screenCenterX = screenCenterX;
	}
	
	public static void setScreenCenterY(int screenCenterY){
		getInstance().screenCenterY = screenCenterY;
	}
	
	public static void setInitPosX(int initPosX){
		getInstance().initPosX = initPosX;
	}
	
	public static void setInitPosY(int initPosY){
		getInstance().initPosY = initPosY;
	}
	
	public static void setX_initial(int x_initial){
		getInstance().x_initial = x_initial;
	}
	
	public static void setY_initial(int y_initial){
		getInstance().y_initial = y_initial;
	}
	
	public static void setParentView(RelativeLayout parentView){
		getInstance().parentView = parentView;
	}
	
	public static void setContext(Context context){
		getInstance().context = context;
	}
	
	public static void createQuestionCard(int index){
		LayoutInflater inflate = (LayoutInflater) getInstance().context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View view = inflate.inflate(R.layout.question_fragment, null);
		
		getInstance().initialize(view);
		
		if(getInstance().question != null)
			getInstance().setValues();
		
		view.setX(getInstance().initPosX);
		view.setY(getInstance().initPosY);
		
		getInstance().initializeInteractionListeners(view, index);
	}

	private void initialize(View root) {
		getInstance().tvLocation = (TextView) root.findViewById(R.id.tvLocation);
		getInstance().tvUsername = (TextView) root.findViewById(R.id.tvUsername);
		getInstance().tvQuestion = (TextView) root.findViewById(R.id.tvQuestion);
		getInstance().tvNumAnswers = (TextView) root.findViewById(R.id.tvNumAnswers);
		getInstance().ivHomeImage = (ImageView) root.findViewById(R.id.ivHomeImage);
		getInstance().ivProfilePic = (CircularImageView) root.findViewById(R.id.ivProfilePic);
		getInstance().sllBack = (SquareLinearLayout) root.findViewById(R.id.sllBack);
		getInstance().sllShare = (SquareLinearLayout) root.findViewById(R.id.sllShare);
		getInstance().sllLike = (SquareLinearLayout) root.findViewById(R.id.sllLike);
		getInstance().sllFollow = (SquareLinearLayout) root.findViewById(R.id.sllFollow);
		getInstance().questionFragmentLayout = (LinearLayout)root.findViewById(R.id.questionFragmentLayout);

		ImageCacheParams cacheParams = new ImageCacheParams(getInstance().context,
				IMAGE_CACHE_DIR);
		// Set memory cache to 25% of app memory
		cacheParams.setMemCacheSizePercent(0.25f);
		getInstance().profileImageFetcher = new RemoteImageFetcher(getInstance().context, 100);
		getInstance().profileImageFetcher.setLoadingImage(R.drawable.loding_album);
		getInstance().profileImageFetcher.addImageCache(HomeScreen.supportFragmentManager, cacheParams);
		getInstance().questionImageFetcher = new RemoteImageFetcher(getInstance().context, 500);
		getInstance().questionImageFetcher.setLoadingImage(R.drawable.loding_album);
		getInstance().questionImageFetcher.addImageCache(HomeScreen.supportFragmentManager, cacheParams);	
		
		getInstance().question = QuestionContentManager.getNextQuestion(); 
	}
	
	private void setValues() {
		tvLocation.setText(getInstance().question.getLocation());
		tvUsername.setText(getInstance().question.getUserName());
		tvQuestion.setText(getInstance().question.getQuestion());
		tvNumAnswers.setText(Integer.toString(getInstance().question.getAnswerCount()));
		if (CommonUtils.isValidURL(getInstance().question.getImage() + "")) {
			questionImageFetcher.loadImage(getInstance().question.getImage(),
					ivHomeImage);
		} else {
			ivHomeImage.setImageResource(R.drawable.no_image_available);
		}
		if (CommonUtils.isValidURL(getInstance().question.getUserImage() + "")) {
			profileImageFetcher.loadImage(getInstance().question.getUserImage(),
					ivProfilePic);
		} else {
			ivProfilePic.setImageResource(R.drawable.ic_noprofilepic);
		}
	}
	
	private void initializeInteractionListeners(final View view, int index){
		
		questionFragmentLayout.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				getInstance().x_current = (int) event.getRawX();
				getInstance().y_current = (int) event.getRawY();
				
				switch(event.getAction()){
					case MotionEvent.ACTION_DOWN:
						getInstance().x_initial = (int) event.getX();
						getInstance().y_initial = (int) event.getY();
						break;
					case MotionEvent.ACTION_MOVE:
						getInstance().x_current = (int) event.getRawX();
						getInstance().y_current = (int) event.getRawY();
						if(getInstance().x_current - getInstance().x_initial > 0){
							view.setX(getInstance().x_current - getInstance().x_initial);
							//m_view.setY(y_curr - y);
							view.setRotation((getInstance().x_initial - getInstance().x_current)*0.001f * 60f);
						}
						break;
					case MotionEvent.ACTION_UP:
						getInstance().x_current = (int) event.getRawX();
						getInstance().y_current = (int) event.getRawY();
						if((getInstance().x_current - getInstance().x_initial) > getInstance().screenCenterX/2){
							parentView.removeView(view);
						}
						break;
					default:
						break;
				}
				
				return true;
			}
			
		});
		
		getInstance().parentView.addView(view, index);
		Animation expansion = createExpansion(view);
		expansion.setDuration(500);
		expansion.setInterpolator(new BounceInterpolator());
		view.startAnimation(expansion);
		
	}
	
	private Animation createExpansion(View m_view) {
		return new CustomScaleAnimation(m_view, 0.f, 1f, 0.1f, 1f,
	            Animation.RELATIVE_TO_PARENT, 0.5f,
	            Animation.RELATIVE_TO_PARENT, 0.5f);

	}
	

}
