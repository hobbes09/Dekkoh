package com.dekkoh.following;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dekkoh.R;

public class Following extends Fragment {
	
	public Following(){}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.following_fragment, container, false);
              
          
        return rootView;
    }

}


