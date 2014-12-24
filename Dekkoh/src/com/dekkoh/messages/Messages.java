package com.dekkoh.messages;

import com.dekkoh.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Messages extends Fragment {
	
	public Messages(){}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.messages_fragment, container, false);
          
        return rootView;
    }

}