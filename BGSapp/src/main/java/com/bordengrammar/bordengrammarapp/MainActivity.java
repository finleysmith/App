// Build info:
//	Borden Grammar School App
//	Build 1 rev 43
//	(C) Borden Grammar School 2014
//
//  Made By Finley Smith (epicfinley@gmail.com) using Android Studio
// WEBSITE website.bordengrammar.kent.sch.uk
/*
 * Copyright (C) 2011-2014 Borden Grammar School, school@bordengrammar.kent.sch.uk
 *
 * This file is part of BGS APP)
 *
 * BGS APP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This source code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this source code; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
 * If you are reading this, then thank you! For reading ma code!
 *
 * Sorry about the lack of code
 *
 *
 *
 */

//viewPager.setCurrentItem(X);   -={x=0 will make home tab x=1 will make parents x=2 will make students

package com.bordengrammar.bordengrammarapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.bordengrammar.bordengrammarapp.adapter.TabsPagerAdapter;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private final String TAG = "MainActivity";
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Home", "Parents", "Students" }; //Create an array of tabs

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		final String PREFS_NAME = "MyPrefsFile";
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0); //Starts settings file
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		boolean tabletSize = getResources().getBoolean(R.bool.isTablet); //Get the tablet from values
		
		if (tabletSize) { //if statment for the tablet
			Log.i(TAG, "IS TABLET");
			if (settings.getBoolean("knowtablet", true)) {
				new AlertDialog.Builder(this)
			    .setTitle(getString(R.string.tablet))
			    .setMessage("You are using a tablet, the app was designed for phones so there may be more whitespace")
			    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            dialog.cancel();		            
			        }
			     })		
			     .setNegativeButton("Why?", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(),"The app was designed for tablet as the majority of users are on phone", Toast.LENGTH_LONG).show();
					}
				})
			     .show();
				settings.edit().putBoolean("knowtablet", false).commit();
			}else{
				Log.i(TAG, "They have already seen that it is tablet, so not showing message");
			}
						
		} else {
			Log.i(TAG, "IS NOT TABLET"); //log not tablet
		}

		

		if (settings.getBoolean("my_first_time", true)) { //work if it is a first time is true
		    //the app is being launched for first time, do something        
		    Log.d("Comments", "First time");
		    // first time task
		    new AlertDialog.Builder(this)
		    .setTitle("Welcome!")
		    .setMessage("To navigate, swipe left and right or use the tabs")
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            dialog.cancel();
		            Toast.makeText(getApplicationContext(),"The welcome message will no longer show", Toast.LENGTH_SHORT).show();
		        }
		     })		    
		     .show();

		    // record the fact that the amp has been started at least once
		    settings.edit().putBoolean("my_first_time", false).commit(); //set it to false
		}
		
		
		//Initilization
		
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(true); //just expermenting with turning this to true IF BROKEN TURN TO FALSE
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override

			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
        //other oncreate methods
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}

    /*
    This is the calss for the options on the top left
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){ //add the facebook and website browser and menu with about
		switch (item.getItemId())
		{
            case android.R.id.home:
                viewPager.setCurrentItem(0);
                Toast.makeText(getApplicationContext(), "Borden Grammar School App (C) 2014", Toast.LENGTH_SHORT).show(); //WHY YOU GO TO FACEBBOK
            case R.id.facebook:
				Log.i(TAG, "Facebook clicked");
				Intent faceBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/BordenGrammarSchool"));  //Create intent variable
				startActivity(faceBrowserIntent); //Start that intent
			return true;
			case R.id.website:
				Log.i(TAG, "Website Clicked");
				Intent websiteBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://website.bordengrammar.kent.sch.uk/")); //Same as above
				startActivity(websiteBrowserIntent);	
			return true;
			case R.id.action_settings:
				Log.i(TAG, "About Clicked"); //About thing
                AlertDialog show = new AlertDialog.Builder(this) //Declare a new dialog variable
                        .setTitle("About") //Add title
                        .setMessage("(C) Borden Grammar School 2014. Borden Grammar School: website.bordengrammar.kent.sch.uk") //Add content
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() { //set postive button to close
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                // Toast.makeText(getApplicationContext(), "About box closed", Toast.LENGTH_SHORT).show(); ------------ This was just to much
                            }
                        })
                        .show();
            default:
				return super.onOptionsItemSelected(item);
		}
	}

    /*
    * Random other code used for the tab stuff that i had to put to solve bugs
     */

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
        Log.i(TAG, "User seems to be stupid, selecting already used tab!");
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Log.i(TAG, "onTabSelected");
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

}


