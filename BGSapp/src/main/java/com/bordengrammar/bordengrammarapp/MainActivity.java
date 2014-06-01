// Copyright (C) 2014.  Finley Smith
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
// Also add information on how to contact you by electronic and paper mail.

// Build info:
//	Borden Grammar School App
//	Build 1 rev 43
//	(C) Borden Grammar School 2014
//
//  Made By Finley Smith (epicfinley@gmail.com) using Android Studio
// WEBSITE website.bordengrammar.kent.sch.uk

package com.bordengrammar.bordengrammarapp;

//android imports

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bordengrammar.bordengrammarapp.adapter.TabsPagerAdapter;
import com.bordengrammar.bordengrammarapp.utils.ut;
import com.parse.ParseAnalytics;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.suredigit.inappfeedback.FeedbackDialog;
import com.suredigit.inappfeedback.FeedbackSettings;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /* Variable/Object Declaration */


	public String TAG = "MainActivity"; //Used for logging (Usage log.i(TAG, "Message")
	private ViewPager viewPager; //For the viewpager used to render the swyping
	private ActionBar actionBar; //Action bar
	private FeedbackDialog feedBack; //Feedback
	private String[] tabs = {"Home", "Parents", "Prospectus"}; //Array so we can use a for loop to define action bar tabs


	//onCreate Method - Majority of code


	@SuppressLint("AppCompatMethod")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.bordenpurple);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		viewPager = (ViewPager) findViewById(R.id.pager);
		TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);
		actionBar = getActionBar();
		assert actionBar != null;
		actionBar.setHomeButtonEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);

			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});


		FeedbackSettings feedbackSettings = new FeedbackSettings();
		feedbackSettings.setCancelButtonText("Cancel");
		feedbackSettings.setSendButtonText("Send");
		feedbackSettings.setText("Send feedback to improve the app");
		feedbackSettings.setTitle("Feedback");
		feedbackSettings.setToast("Thanks for your feedback");
		feedBack = new FeedbackDialog(this, "AF-186C1F794D93-1A", feedbackSettings);



		if (readPrefs("twitter").equals("error")) {
			//crouton
			Toast.makeText(this, "Error retreving tweets", Toast.LENGTH_LONG).show();
		}
		ParseAnalytics.trackAppOpened(getIntent());


	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	private void setTabsMaxWidth() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int screenWidth = displaymetrics.widthPixels;
		final ActionBar actionBar = getActionBar();
		final View tabView = actionBar.getTabAt(0).getCustomView();
		final View tabContainerView = (View) tabView.getParent();
		final int tabPadding = tabContainerView.getPaddingLeft() + tabContainerView.getPaddingRight();
		final int tabs = actionBar.getTabCount();
		for(int i=0 ; i < tabs ; i++){
			View tab = actionBar.getTabAt(i).getCustomView();
			TextView text1 = (TextView) tab.findViewById(R.id.text1);
			text1.setMaxWidth(screenWidth/tabs-tabPadding-1);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				viewPager.setCurrentItem(0);
				ut.logIt("Used home button");
				return true;
			case R.id.facebook:
				ut.logIt("Facebook button clicked");
				Intent faceBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/BordenGrammarSchool"));
				startActivity(faceBrowserIntent);
				return true;
			case R.id.website:
				Log.i(TAG, "Website Clicked");
				Intent websiteBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://website.bordengrammar.kent.sch.uk/"));
				startActivity(websiteBrowserIntent);
				return true;
			case R.id.action_settings:
				ut.logIt("clicked about");
				Intent i = new Intent(MainActivity.this, AboutActivity.class);
				startActivity(i);
				return true;
			case R.id.action_feedback:
				ut.logIt("feedback");
				feedBack.show();
				return true;
			case R.id.action_realsettings:
				ut.logIt("settings");
				Intent s = new Intent(MainActivity.this, SettingsActivity.class);
				startActivity(s);
				return true;
			case R.id.action_privacy:
				Intent ss = new Intent(MainActivity.this, PrivacyActivity.class);
				startActivity(ss);
				return true;
			case R.id.action_change:
				ChangeLogDialog dia = new ChangeLogDialog(this);
				dia.show();
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

		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	public void twitactiv(View view) {
		Intent y = new Intent(MainActivity.this, TwitterActivity.class);
		startActivity(y);
	}

	public String readPrefs(String key) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		return sp.getString(key, "error");

	}


}


