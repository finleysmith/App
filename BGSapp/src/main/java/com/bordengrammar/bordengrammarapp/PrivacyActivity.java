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

package com.bordengrammar.bordengrammarapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.suredigit.inappfeedback.FeedbackDialog;
import com.suredigit.inappfeedback.FeedbackSettings;


public class PrivacyActivity extends Activity {
	public String TAG = "MainActivity";
	private FeedbackDialog feedBack;
	private ActionBar actionBar;
	public static String PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
	    FeedbackSettings feedbackSettings = new FeedbackSettings();
	    feedbackSettings.setCancelButtonText("Cancel");
	    feedbackSettings.setSendButtonText("Send");
	    feedbackSettings.setText("Send feedback to improve the app");
	    feedbackSettings.setTitle("Feedback");
	    feedbackSettings.setToast("We value your feedback");
	    feedBack = new FeedbackDialog(this, "AF-186C1F794D93-1A", feedbackSettings);
	    actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    PACKAGE_NAME = getApplicationContext().getPackageName();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) { //after the menu has been inflated
		switch (item.getItemId()) { //get the id fors the menus from our menu.xml
			case android.R.id.home: //if it is home
				Intent l = new Intent(PrivacyActivity.this, MainActivity.class);
				startActivity(l);
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
				Intent i = new Intent(PrivacyActivity.this, AboutActivity.class);
				startActivity(i);

				return true;
			case R.id.action_feedback: //if they clciked send feedback
				logIt("feedback");
				feedBack.show(); //show the feedback that we declared
				return true;
			case R.id.action_privacy:

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}

	public void logIt(String it) {
		Log.i(TAG, it);
	}

}
