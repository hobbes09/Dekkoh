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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionFragment extends Fragment{
	
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
		
		root.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Toast.makeText(getActivity(), "QuestionFragment touch detected", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
		});

		
		initialize(root);
		setValues();
		
        return root;		
	}

	private void setValues() {
//		tvLocation.setText(getArguments().getString("LOCATION"));
//		tvUsername.setText(getArguments().getString("USERNAME"));
//		tvQuestion.setText(getArguments().getString("QUESTION"));
//		ivHomeImage.setImageBitmap(getArguments().get);
//		root.setBackgroundColor(getArguments().getInt(COLOR));
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
