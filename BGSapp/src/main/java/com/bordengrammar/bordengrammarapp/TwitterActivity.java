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

/*
* Created By Finley Smith on 5/4/14
 */

package com.bordengrammar.bordengrammarapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.bordengrammar.bordengrammarapp.utils.ut;
import com.suredigit.inappfeedback.FeedbackDialog;
import com.suredigit.inappfeedback.FeedbackSettings;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterActivity extends Activity {

	public String TAG = "MainActivity";
	private FeedbackDialog feedBack;
	private ActionBar actionBar;
	public static String PACKAGE_NAME;
	ListView listView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter);
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


		//Now lets get down into the twitter

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("Toqp03fcUErG5P8e9nhfsw")
				.setOAuthConsumerSecret(
						"SEqktstO9h7SqSm7zmcuWlH3bOtElJm1Ds2TFSwFBc")
				.setOAuthAccessToken(
						"2245935685-U5LMfl4oEcOv6Khw58JZqRdcH2PlABEeUP2JeXj")
				.setOAuthAccessTokenSecret(
						"uFcGRpCx8aGXdv3AiAkfVImnoLrlNNCUnZ2UtE76Zbnpa");

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		List<Status> statuses = null;
		String user;
		user = "epicfinley";
		try {
			statuses = twitter.getUserTimeline(user);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		assert statuses != null;
		twitter4j.Status status1 = statuses.get(0);
		Format formatter = new SimpleDateFormat("MMM d, K:mm");
		savePrefs("twitter", status1.getText());
		savePrefs("twittertime", formatter.format(status1.getCreatedAt()));
		twitter4j.Status status2 = statuses.get(1);
		twitter4j.Status status3 = statuses.get(2);
		twitter4j.Status status4 = statuses.get(3);
		twitter4j.Status status5 = statuses.get(4);
		twitter4j.Status status6 = statuses.get(5);
		twitter4j.Status status7 = statuses.get(6);
		twitter4j.Status status8 = statuses.get(7);
		twitter4j.Status status9 = statuses.get(8);
		twitter4j.Status status10 = statuses.get(9);
		TextView time1 = (TextView)findViewById(R.id.time1);
		TextView time2 = (TextView)findViewById(R.id.time2);
		TextView time3 = (TextView)findViewById(R.id.time3);
		TextView time4 = (TextView)findViewById(R.id.time4);
		TextView time5 = (TextView)findViewById(R.id.time5);
		TextView time6 = (TextView)findViewById(R.id.time6);
		TextView time7 = (TextView)findViewById(R.id.time7);
		TextView time8 = (TextView)findViewById(R.id.time8);
		TextView time9 = (TextView)findViewById(R.id.time9);
		TextView time10 = (TextView)findViewById(R.id.time10);
		time1.setText(formatter.format(status1.getCreatedAt()));
		time2.setText(formatter.format(status2.getCreatedAt()));
		time3.setText(formatter.format(status3.getCreatedAt()));
		time4.setText(formatter.format(status4.getCreatedAt()));
		time5.setText(formatter.format(status5.getCreatedAt()));
		time6.setText(formatter.format(status6.getCreatedAt()));
		time7.setText(formatter.format(status7.getCreatedAt()));
		time8.setText(formatter.format(status8.getCreatedAt()));
		time9.setText(formatter.format(status9.getCreatedAt()));
		time10.setText(formatter.format(status10.getCreatedAt()));
		TextView text1 = (TextView)findViewById(R.id.text1);
		TextView text2 = (TextView)findViewById(R.id.text2);
		TextView text3 = (TextView)findViewById(R.id.text3);
		TextView text4 = (TextView)findViewById(R.id.text4);
		TextView text5 = (TextView)findViewById(R.id.text5);
		TextView text6 = (TextView)findViewById(R.id.text6);
		TextView text7 = (TextView)findViewById(R.id.text7);
		TextView text8 = (TextView)findViewById(R.id.text8);
		TextView text9 = (TextView)findViewById(R.id.text9);
		TextView text10 = (TextView)findViewById(R.id.text10);
		text1.setText(readPrefs("twitter"));
		text2.setText(status2.getText());
		text3.setText(status3.getText());
		text4.setText(status4.getText());
		text5.setText(status5.getText());
		text6.setText(status6.getText());
		text7.setText(status7.getText());
		text8.setText(status8.getText());
		text9.setText(status9.getText());
		text10.setText(status10.getText());
		/* i think i could have used a for loop... oh well i typed it all already. */
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent home = new Intent(TwitterActivity.this, MainActivity.class);
				startActivity(home);
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
				Intent i = new Intent(TwitterActivity.this, AboutActivity.class);
				startActivity(i);

				return true;
			case R.id.action_feedback:
				ut.logIt("feedback");
				feedBack.show();
				return true;
			case R.id.action_realsettings:
				ut.logIt("settings");
				Intent s = new Intent(TwitterActivity.this, SettingsActivity.class);
				startActivity(s);
				return true;
			case R.id.action_privacy:
				Intent ss = new Intent(TwitterActivity.this, PrivacyActivity.class);
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

	public void logIt(String it) {
		Log.i(TAG, it);
	}
	private void savePrefs(String key, String value) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}
	public String readPrefs(String key) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		return sp.getString(key, "An error occured whilst fetching twitter feed");
	}


}
