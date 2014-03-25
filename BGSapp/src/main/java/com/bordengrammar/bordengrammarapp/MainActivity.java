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
 * All of the code is commented
 *
 *
 *
 *
 *
 */
package com.bordengrammar.bordengrammarapp;

//android imports
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bordengrammar.bordengrammarapp.adapter.TabsPagerAdapter;
import com.parse.ParseAnalytics;
import com.suredigit.inappfeedback.FeedbackDialog;
import com.suredigit.inappfeedback.FeedbackSettings;

import fr.nicolaspomepuy.discreetapprate.AppRate;
import fr.nicolaspomepuy.discreetapprate.RetryPolicy;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;





public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /* Variable/Object Declaration */


    public static String PACKAGE_NAME; //Used to for apprate to send it to approaite app in play store
    public String TAG = "MainActivity"; //Used for logging (Usage log.i(TAG, "Message")
    private ViewPager viewPager; //For the viewpager used to render the swyping
    private ActionBar actionBar; //Action bar
    private FeedbackDialog feedBack; //Feedback
    private String[] tabs = {"Home", "Parents", "Students"}; //Array so we can use a for loop to define action bar tabs


    //onCreate Method - Majority of code


    @Override
    protected void onCreate(Bundle savedInstanceState) {
	    SharedPreferences mainsettings = PreferenceManager.getDefaultSharedPreferences(this);
	    Boolean push = mainsettings.getBoolean("example_checkbox", false);
	    if(push == true) {
		    ParseAnalytics.trackAppOpened(getIntent());
	    } else {
		    Log.e(TAG , "Push notifcations disabled");
	    }
	    super.onCreate(savedInstanceState);//get the saved state
        final String PREFS_NAME = "MyPrefsFile"; //defining the settings file
        PACKAGE_NAME = getApplicationContext().getPackageName(); //fill the pacakage_name variable with the pacakage name
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0); //retrive the settings file
        setContentView(R.layout.activity_main);//make it use the layout

        if (settings.getBoolean("my_first_time", true)) { //if the settings my_first_time is true
            settings.edit().putBoolean("my_first_time", false).commit(); /* set it to false */
        } else {
            AppRate.with(this).text("Help Borden by rating the app!"); //Title
            AppRate.with(this).retryPolicy(RetryPolicy.EXPONENTIAL); //make it expodential
            AppRate.with(this).checkAndShow(); //create the dialog
            AppRate.with(this).listener(new AppRate.OnShowListener() { //create a listener to see what they click
                @Override
                public void onRateAppShowing() {
                    // abstract view blah blah blah I HATE RED BUGS
                }
                @Override
                public void onRateAppDismissed() {
                    //have to put this for no reason
                }
                @Override
                public void onRateAppClicked() {
                    // User has clicked the rate part
                    Uri uri = Uri.parse("market://details?id=" + PACKAGE_NAME); //create variable uri which is our app due to package_name
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri); //create the intent
                    try {
                        startActivity(goToMarket); //if is true go to the market
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + PACKAGE_NAME))); //if it fails go to the market anyway :)
                    }
                }
            });

        }


	    viewPager = (ViewPager) findViewById(R.id.pager); //create a viewpager for rendering the swyping
        actionBar = getActionBar(); //define the actionBar variable as actionbar
        TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager()); //use the our tab page adapter
        viewPager.setAdapter(mAdapter); //set our tabadapter to what we just set mAdapter to
        actionBar.setHomeButtonEnabled(true); //Make it so we can click on the logo on action bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); //set the action bar mode to tabbed
        for (String tab_name : tabs) { //for loop to add the tabs
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //This makes the viewpager display the right fragment(tab) when we swype or use the tabs
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

	    //feedback ***
         //defining our feedback with api key(go ahead try and use the api key, but i put security on it so HAH
	    FeedbackSettings feedbackSettings = new FeedbackSettings();
	    feedbackSettings.setCancelButtonText("Cancel");
	    feedbackSettings.setSendButtonText("Send");
	    feedbackSettings.setText("Send feedback to improve the app");
	    feedbackSettings.setTitle("Feedback");
	    feedbackSettings.setToast("We value your feedback");
	    feedBack = new FeedbackDialog(this, "AF-186C1F794D93-1A", feedbackSettings);


	    //now for the twitter BULLSHIT API V1.1 WHY THE FUCK DO I HAVE TO USE OAUTH OH MY GAWDDDDD
	    Twitter twitter = new TwitterFactory().getInstance();
	    try {
		    List<Status> statuses;
		    String user;
		    if (args.length == 1) {
			    user = args[0];
			    statuses = twitter.getUserTimeline(user);
		    } else {
			    user = twitter.verifyCredentials().getScreenName();
			    statuses = twitter.getUserTimeline();
		    }
		    System.out.println("Showing @" + user + "'s user timeline.");
		    for (Status status : statuses) {
			    System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
		    }
	    } catch (TwitterException te) {
		    te.printStackTrace();
		    System.out.println("Failed to get timeline: " + te.getMessage());
		    System.exit(-1);
	    }
    }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //get the menu items
        inflater.inflate(R.menu.main_activity_actions, menu); //inflate tgem into the menu
        return super.onCreateOptionsMenu(menu); //reurn that we created then and to go the next method
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //after the menu has been inflated
        switch (item.getItemId()) { //get the id fors the menus from our menu.xml
            case android.R.id.home: //if it is home
                viewPager.setCurrentItem(0); //set the tab to home
                logIt("Used home button"); //then logit
                return true; //break it so it does not go onto next case
            case R.id.facebook: //if it is facebook button
                logIt("Facebook button clicked"); //logit
                Intent faceBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/BordenGrammarSchool"));  //Create intent variable
                startActivity(faceBrowserIntent); //Start that intent
                return true;
            case R.id.website: //if they clicked they
                Log.i(TAG, "Website Clicked");
                Intent websiteBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://website.bordengrammar.kent.sch.uk/")); //Same as above
                startActivity(websiteBrowserIntent);
                return true;
            case R.id.action_settings: //action settings actually about, can't change it now
                logIt("clicked about");
	            Intent i = new Intent(MainActivity.this, AboutActivity.class);
	            startActivity(i);

	            return true;
            case R.id.action_feedback: //if they clciked send feedback
                logIt("feedback");
                feedBack.show(); //show the feedback that we declared
                return true;
	        case R.id.action_realsettings:
		        logIt("settings");
		        Intent s = new Intent(MainActivity.this, SettingsActivity.class);
		        startActivity(s);
		        return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onPause() {
        super.onPause();
        feedBack.dismiss();
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition()); //set the current to fragment in the viewpager to what is currently selected
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    public void logIt(String it) {
        Log.w(TAG, it);
    } // the function for the logit thing i use. Q:Why log all the time? A: It is usefull for debugging so you know what the user was doing at the time

}


