package com.dekkoh.myactivity;

import com.dekkoh.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MyActivity extends Fragment {
	
	public MyActivity(){}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.myactivity_fragment, container, false);
          
        return rootView;
    }

}
