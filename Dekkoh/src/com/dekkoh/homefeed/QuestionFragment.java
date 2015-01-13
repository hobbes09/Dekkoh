package com.dekkoh.homefeed;

import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
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
import com.dekkoh.custom.view.SquareLinearLayout;
import com.dekkoh.datamodel.Question;

public class QuestionFragment extends BaseFragment{
	
	private float xDown, xUp, yDown, yUp;
	private int SWIPE_ACTION = 0;
	private TextView tvLocation;
	private TextView tvUsername;
	private TextView tvQuestion;
	private ImageView ivHomeImage;
	private static SquareLinearLayout sllProfilePic;
	
	private Question question;
	
		
	QuestionFragment(){	
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.question_fragment, null);

//		if(ApplicationState.getHomefeedQuestion_Offset() - ApplicationState.getHomefeedQuestion_CurrentIndex() <= 1){
//			new FetchQuestionTask().execute();
//		}
//		
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
                				QuestionFragment nextQuestionFragment = new QuestionFragment();
                				nextQuestionFragment.setArguments(QuestionContentManager.getNextQuestionBundle(activity));
                				FragmentTransaction transactionNext = HomeScreen.supportFragmentManager.beginTransaction();
                				transactionNext.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                				transactionNext.replace(R.id.contentHomeActivity, nextQuestionFragment);
                			    transactionNext.commit();
                			    //nextQuestionFragment.setValues();
                				break;
                	case FragmentTransitionManager.SWIPE_UP:
                				if(ApplicationState.getHomefeedQuestion_CurrentIndex()!=0){
	                				QuestionFragment previousQuestionFragment = new QuestionFragment();
	                				previousQuestionFragment.setArguments(QuestionContentManager.getNextQuestionBundle(activity));
			        				FragmentTransaction transactionPrevious = HomeScreen.supportFragmentManager.beginTransaction();
			        				transactionPrevious.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down);
			        				transactionPrevious.replace(R.id.contentHomeActivity, previousQuestionFragment);
			        			    transactionPrevious.commit();
			        			    //previousQuestionFragment.setValues();
                				}
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
	}
	
	private void setUIValues(String question, String location, String username){
		tvLocation.setText(location);
		tvUsername.setText(username);
		tvQuestion.setText(question);
	}

	private void initialize(View root) {
		tvLocation = (TextView)root.findViewById(R.id.tvLocation);
		tvUsername = (TextView)root.findViewById(R.id.tvUsername);
		tvQuestion = (TextView)root.findViewById(R.id.tvQuestion);
		ivHomeImage = (ImageView)root.findViewById(R.id.ivHomeImage);
		sllProfilePic = (SquareLinearLayout)root.findViewById(R.id.sllProfilePic);
	}
	
	
	public class FetchQuestionTask extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialogHandler.showCustomProgressDialog(activity);
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				QuestionContentManager.fetchQuestionsFromBackend(activity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(QuestionContentManager.updateSuccessful)
				return true;
			else
				return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(result){
				progressDialogHandler.dismissCustomProgressDialog(activity);
			}
		}

	}
	

}
