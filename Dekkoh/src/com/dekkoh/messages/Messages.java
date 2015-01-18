
package com.dekkoh.messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dekkoh.R;
import com.dekkoh.application.BaseFragment;

public class Messages extends BaseFragment {

    public Messages() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.messages_fragment, container, false);

        return rootView;
    }

}
