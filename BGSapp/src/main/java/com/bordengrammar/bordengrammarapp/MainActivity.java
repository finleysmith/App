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
import android.content.ActivityNotFoundException;
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
import com.suredigit.inappfeedback.FeedbackDialog;
import fr.nicolaspomepuy.discreetapprate.AppRate;
import fr.nicolaspomepuy.discreetapprate.RetryPolicy;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
    public static String PACKAGE_NAME;
	private ViewPager viewPager;
	private final String TAG = "MainActivity";
    private ActionBar actionBar;
    private FeedbackDialog feedBack;
	private String[] tabs = { "Home", "Parents", "Students" }; //Create an array of tabs
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		final String PREFS_NAME = "MyPrefsFile";
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0); //Starts settings file
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        //noinspection ConstantConditions
        PACKAGE_NAME = getApplicationContext().getPackageName();
		boolean tabletSize = getResources().getBoolean(R.bool.isTablet); //Get the tablet from values
		if (tabletSize) { //if statment for the tablet
			Log.i(TAG, "IS TABLET");
			}
		if (settings.getBoolean("my_first_time", true)) {
		    Log.d("Comments", "First time");
		    // first time task
		    new AlertDialog.Builder(this)
		    .setTitle("Welcome!")
		    .setMessage("To navigate, swipe left and right or use the tabs")
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            dialog.cancel();
		            Toast.makeText(getApplicationContext(),"Use the send feedback to report bugs or ask questions, in the menu.", Toast.LENGTH_SHORT).show();
		        }
		     })		    
		     .show();

		    // record the fact that the amp has been started at least once
		    settings.edit().putBoolean("my_first_time", false).commit(); //set it to false
		} else {
            logIt("Has done before, going to ask to rate");
            AppRate.with(this).text("Help Borden by rating the app!");
            AppRate.with(this).delay(5000);
            AppRate.with(this).retryPolicy(RetryPolicy.EXPONENTIAL);
            AppRate.with(this).checkAndShow();
            AppRate.with(this).listener(new AppRate.OnShowListener() {
                @Override
                public void onRateAppShowing() {
                    // View is shown
                }

                @Override
                public void onRateAppDismissed() {
                    // User has dismissed it
                }

                @Override
                public void onRateAppClicked() {
                    // User has clicked the rate part
                    Uri uri = Uri.parse("market://details?id=" + PACKAGE_NAME);
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + PACKAGE_NAME)));
                    }
                }
            });

        }
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
        TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(true); //just expermenting with turning this to true IF BROKEN TURN TO FALSE
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        logIt("actionbarinit");
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
        feedBack = new FeedbackDialog(this, "AF-186C1F794D93-1A");
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
                logIt("Used home button");
                break;
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
                new AlertDialog.Builder(this) //Declare a new dialog variable
                        .setTitle("About") //Add title
                        .setMessage("(C) Borden Grammar School 2014.") //Add content
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() { //set postive button to close
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                // Toast.makeText(getApplicationContext(), "About box closed", Toast.LENGTH_SHORT).show(); ------------ This was just to much
                            }
                        })
                        .show();
            case R.id.action_feedback:
                logIt("feedback");
                feedBack.show();
            return true;
            default:
				return super.onOptionsItemSelected(item);
		}
        return false;
    }

    /*
    * Random other code used for the tab stuff that i had to put to solve bugs
     */
    public void onPause() {
        super.onPause();
        feedBack.dismiss();
    }

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

    public void logIt(String it) {
        Log.i(TAG, it);
    }

}


