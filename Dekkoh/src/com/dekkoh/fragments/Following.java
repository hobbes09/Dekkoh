
package com.dekkoh.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dekkoh.BaseFragment;
import com.dekkoh.R;

public class Following extends BaseFragment {

    public Following() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.following_fragment, container, false);

        return rootView;
    }

}
