package com.bordengrammar.bordengrammarapp;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HomeFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

	    View myInflatedView = inflater.inflate(R.layout.fragment_home, container,false);




	    TextView t;
	    assert myInflatedView != null;
	    t = (TextView) myInflatedView.findViewById(R.id.textView3);
	    t.setText('"' + readPrefs("twitter") + '"');


	    return myInflatedView;
    }
	public String readPrefs(String key) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		return sp.getString(key, "An error occured whilst fetching twitter feed");
	}











}
