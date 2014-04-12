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


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class SplashActivity extends Activity {


	public String LOG_TAG = "SplashActivity";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		final TextView t = (TextView) findViewById(R.id.splashprog);
		t.setText("Loading ..");


		/**
		 * Showing splashscreen while making network calls to download necessary
		 * data before launching the app Will use AsyncTask to make http call
		 */
		final PrefetchData prefetchData = new PrefetchData();
		prefetchData.execute();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run() {
				if ( prefetchData.getStatus() == AsyncTask.Status.RUNNING )
					prefetchData.cancel(true);
					if(readPrefs("twitter").isEmpty()){
					savePrefs("twitter", "Timed out when getting tweets, do you have internet or is it slow?");
					t.setText("Error no internet connection");
					}

					Intent i = new Intent(SplashActivity.this, MainActivity.class);
					startActivity(i);
					finish();
			}
		}, 3000 );


	}

	/**
	 * Async Task to make http call
	 */
	private class PrefetchData extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// before making http calls

		}

		@Override
		protected Void doInBackground(Void... arg0) {
            /*
             * Will make http call here This call will download required data
             * before launching the app
             * example:
             * 1. Downloading and storing in SQLite
             * 2. Downloading images
             * 3. Fetching and parsing the xml / json
             * 4. Sending device information to server
             * 5. etc.,
             */
			if(isNetworkAvailable()) {

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
				List<twitter4j.Status> statuses = null;
				TextView t = (TextView) findViewById(R.id.splashprog);
				t.setText("Loading ..");
				String user;
				user = "epicfinley";
				try {
					statuses = twitter.getUserTimeline(user);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				t.setText("Loading ...");
				assert statuses != null;
				twitter4j.Status status = statuses.get(0);
				Format formatter = new SimpleDateFormat("MMM d, K:mm");
				savePrefs("twitter", status.getText());
				savePrefs("twittertime", formatter.format(status.getCreatedAt()));
				t.setText("Loading .");

			} else {
				Log.e(LOG_TAG, "No internet");

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// After completing http call
			// will close this activity and lauch main activity


			if(readPrefs("twitter").isEmpty()){
				savePrefs("twitter", "Error retriving tweets");
			}
			TextView t = (TextView) findViewById(R.id.splashprog);
			t.setText("Loading ..");
			Intent i = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(i);

			// close this activity
			finish();
		}

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
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

}