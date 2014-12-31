package com.dekkoh.homefeed;

import com.dekkoh.R;
import com.dekkoh.util.RoundedImageView;
import com.dekkoh.util.SquareLinearLayout;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionFragment extends Fragment{
	
	private float xDown, xUp, yDown, yUp;
	private int SWIPE_ACTION = 0;
	private TextView tvLocation;
	private TextView tvUsername;
	private TextView tvQuestion;
	private ImageView ivHomeImage;
	private SquareLinearLayout sllProfilePic;
	
		
	private static int currentIndex = 0;  //store and keep updating in shared preference
	
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
                				Fragment nextQuestionFragment = new QuestionFragment();
                				nextQuestionFragment.setArguments(QuestionContentManager.setNextQuestion());
                				FragmentTransaction transactionNext = HomeScreen.supportFragmentManager.beginTransaction();
                				transactionNext.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                				transactionNext.replace(R.id.contentHomeActivity, nextQuestionFragment);
                			    transactionNext.commit();
                				break;
                	case FragmentTransitionManager.SWIPE_UP:
		                		Fragment previousQuestionFragment = new QuestionFragment();
		                		previousQuestionFragment.setArguments(QuestionContentManager.setPreviousQuestion());
		        				FragmentTransaction transactionPrevious = HomeScreen.supportFragmentManager.beginTransaction();
		        				transactionPrevious.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down);
		        				transactionPrevious.replace(R.id.contentHomeActivity, previousQuestionFragment);
		        			    transactionPrevious.commit();
                				break;
                	case FragmentTransitionManager.SWIPE_LEFT:
                				Toast.makeText(getActivity(), "Swipe left Detected", Toast.LENGTH_SHORT).show();
                				break;
                	case FragmentTransitionManager.SWIPE_RIGHT:
                				Toast.makeText(getActivity(), "Swipe right Detected", Toast.LENGTH_SHORT).show();
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
//		ivHomeImage.setImageBitmap(getArguments().get);
		RoundedImageView.setCircledLinearLayoutBackground(sllProfilePic, R.drawable.test, getResources());		
	}

	private void initialize(View root) {
		tvLocation = (TextView)root.findViewById(R.id.tvLocation);
		tvUsername = (TextView)root.findViewById(R.id.tvUsername);
		tvQuestion = (TextView)root.findViewById(R.id.tvQuestion);
		ivHomeImage = (ImageView)root.findViewById(R.id.ivHomeImage);
		sllProfilePic = (SquareLinearLayout)root.findViewById(R.id.sllProfilePic);
	}

	
	
 
	

}
