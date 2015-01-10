package com.dekkoh.myactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dekkoh.R;
import com.dekkoh.application.BaseFragment;


public class MyQuestionsFragment extends BaseFragment {
	
	public MyQuestionsFragment(){}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.myactivity_layout, container, false);
          
        return rootView;
    }

}
