package com.dekkoh.homefeed;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.dekkoh.custom.view.RoundedImageView;
import com.dekkoh.custom.view.SquareLinearLayout;

public class QuestionFragment extends Fragment{
	
	private float xDown, xUp, yDown, yUp;
	private int SWIPE_ACTION = 0;
	private TextView tvLocation;
	private TextView tvUsername;
	private TextView tvQuestion;
	private ImageView ivHomeImage;
	private static SquareLinearLayout sllProfilePic;
	private static Resources resources;
		
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
                				QuestionFragment nextQuestionFragment = new QuestionFragment();
								try {
									nextQuestionFragment.setArguments(QuestionContentManager.getNextQuestionContent());
								} catch (Exception e) {
									e.printStackTrace();
								}
                				FragmentTransaction transactionNext = HomeScreen.supportFragmentManager.beginTransaction();
                				transactionNext.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                				transactionNext.replace(R.id.contentHomeActivity, nextQuestionFragment);
                			    transactionNext.commit();
                				break;
                	case FragmentTransitionManager.SWIPE_UP:
                				if(ApplicationState.getHomefeedQuestion_CurrentIndex()!=0){
	                				QuestionFragment previousQuestionFragment = new QuestionFragment();
									try {
										previousQuestionFragment.setArguments(QuestionContentManager.getPreviousQuestionContent());
									} catch (Exception e) {
										e.printStackTrace();
									}
			        				FragmentTransaction transactionPrevious = HomeScreen.supportFragmentManager.beginTransaction();
			        				transactionPrevious.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down);
			        				transactionPrevious.replace(R.id.contentHomeActivity, previousQuestionFragment);
			        			    transactionPrevious.commit();
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
//		ivHomeImage.setImageBitmap(getArguments().get);
//		RoundedImageView.setCircledLinearLayoutBackground(sllProfilePic, R.drawable.test, getResources());		
		new RoundedImagePainter().execute(Integer.toString(R.drawable.test));
	}

	private void initialize(View root) {
		tvLocation = (TextView)root.findViewById(R.id.tvLocation);
		tvUsername = (TextView)root.findViewById(R.id.tvUsername);
		tvQuestion = (TextView)root.findViewById(R.id.tvQuestion);
		ivHomeImage = (ImageView)root.findViewById(R.id.ivHomeImage);
		sllProfilePic = (SquareLinearLayout)root.findViewById(R.id.sllProfilePic);
		resources = getResources();
	}

	
	public class RoundedImagePainter extends AsyncTask<String, String, BitmapDrawable>{

		@Override
		protected BitmapDrawable doInBackground(String... params) {
			int drawableObject = Integer.parseInt(params[0]);
			Bitmap bMap = BitmapFactory.decodeResource(resources, drawableObject);
			bMap = circledimage(bMap);
			BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bMap);
			return bitmapDrawable;
		}

		@Override
		protected void onPostExecute(BitmapDrawable bitmapDrawable) {
			//super.onPostExecute(bitmapDrawable);
			sllProfilePic.setBackgroundDrawable(bitmapDrawable);
		}

		@Override
		protected void onPreExecute() {
			sllProfilePic.setBackgroundColor(Color.BLACK);
		}
		
		public Bitmap circledimage(final Bitmap source) {
	        final Paint paint = new Paint();
	        paint.setAntiAlias(true);
	        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
	 
	        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Config.ARGB_8888);
	        Canvas canvas = new Canvas(output);
	        float radius = (source.getHeight()>source.getWidth())? source.getWidth() : source.getHeight();
	        canvas.drawRoundRect(new RectF(0, 0, source.getWidth(), source.getHeight()), radius, radius, paint);
	 
	        if (source != output) {
	            source.recycle();
	        }
	 
	        return output;
	    }

	
	}
 
	

}
