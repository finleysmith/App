package com.bordengrammar.bordengrammarapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment implements OnClickListener {

    Button btn;
    Button btn1;
    Button btn2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        btn = (Button) rootView.findViewById(R.id.news);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
            	Intent websiteBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://website.bordengrammar.kent.sch.uk/index.php?option=com_content&view=category&layout=blog&id=36&Itemid=193"));
				startActivity(websiteBrowserIntent);	                

            }
        });
        btn1 = (Button) rootView.findViewById(R.id.canteen);
        btn1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
            	Intent websiteBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.squidcard.com/independentcatering.html"));
				startActivity(websiteBrowserIntent);	                

            }
        });
        btn2 = (Button) rootView.findViewById(R.id.moodle);
        btn2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
            	Intent websiteBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://vle.bordengrammar.kent.sch.uk"));
				startActivity(websiteBrowserIntent);	                

            }
        });
        return rootView;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}