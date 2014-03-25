package com.bordengrammar.bordengrammarapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


public class HomeFragment extends Fragment implements OnClickListener {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        assert rootView != null;
	    //TextView textElement = (TextView) rootView.findViewById(R.id.textView3);
	    //textElement.setText("I love you");
	    return rootView;

    }

    @Override
    public void onClick(View v) {
        /* TODO Auto-generated method stub */

    }
}