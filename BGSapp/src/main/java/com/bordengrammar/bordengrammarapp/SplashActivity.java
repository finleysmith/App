package com.bordengrammar.bordengrammarapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class SplashActivity extends Activity {


	public String LOG_TAG = "SplashActivity.java";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		/**
		 * Showing splashscreen while making network calls to download necessary
		 * data before launching the app Will use AsyncTask to make http call
		 */
		new PrefetchData().execute();

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
				String user;
				user = "epicfinley";
				try {
					statuses = twitter.getUserTimeline(user);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				assert statuses != null;
				twitter4j.Status status = statuses.get(0);
				savePrefs("twitter", status.getText());
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