package com.bordengrammar.bordengrammarapp;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

import com.suredigit.inappfeedback.FeedbackDialog;
import com.suredigit.inappfeedback.FeedbackSettings;

import java.util.List;


public class SettingsActivity extends PreferenceActivity {

	private static final boolean ALWAYS_SIMPLE_PREFS = false;
	private FeedbackDialog feedBack;
	public String TAG = "SettingsActivity";

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		logIt("startofoncreate");
		super.onPostCreate(savedInstanceState);
		setupSimplePreferencesScreen();
		logIt("1");
		ActionBar actionBar = getActionBar();
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		logIt("2");
		FeedbackSettings feedbackSettings = new FeedbackSettings();
		feedbackSettings.setCancelButtonText("Cancel");
		feedbackSettings.setSendButtonText("Send");
		feedbackSettings.setText("Send feedback to improve the app");
		feedbackSettings.setTitle("Feedback");
		feedbackSettings.setToast("We value your feedback");
		feedBack = new FeedbackDialog(this, "AF-186C1F794D93-1A", feedbackSettings);
		logIt("3");
	}
	public void logIt(String log) { Log.w(TAG, log);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent l = new Intent(SettingsActivity.this, MainActivity.class);
				startActivity(l);
				return true;
			case R.id.facebook:

				Intent faceBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/BordenGrammarSchool"));  //Create intent variable
				startActivity(faceBrowserIntent);
				return true;
			case R.id.website:

				Intent websiteBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://website.bordengrammar.kent.sch.uk/")); //Same as above
				startActivity(websiteBrowserIntent);
				return true;
			case R.id.action_settings:

				Intent i = new Intent(SettingsActivity.this, AboutActivity.class);
				startActivity(i);

				return true;
			case R.id.action_feedback:

				feedBack.show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	protected boolean isValidFragment(String fragmentName) {
		// We only accept our own fragments to use, or those provided by the super


		logIt("isValidfragment");
		return GeneralPreferenceFragment.class.getName().equals(fragmentName) ||
				super.isValidFragment(fragmentName);
	}
	private void setupSimplePreferencesScreen() {
		if (!isSimplePreferences(this)) {
			logIt("simple1");
			return;
		}
		logIt("addprefsimple");
		addPreferencesFromResource(R.xml.pref_general);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onIsMultiPane() {
		return isXLargeTablet(this) && !isSimplePreferences(this);
	}


	private static boolean isXLargeTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
	}


	private static boolean isSimplePreferences(Context context) {
		return ALWAYS_SIMPLE_PREFS
				|| Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
				|| !isXLargeTablet(context);
	}


	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onBuildHeaders(List<Header> target) {
		if (!isSimplePreferences(this)) {
			logIt("load headers");
			loadHeadersFromResource(R.xml.pref_headers, target);
		}
	}


	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			String stringValue = value.toString();


			if (preference instanceof ListPreference) {
				// For list preferences, look up the correct display value in
				// the preference's 'entries' list.
				ListPreference listPreference = (ListPreference) preference;
				int index = listPreference.findIndexOfValue(stringValue);

				// Set the summary to reflect the new value.
				preference.setSummary(
						index >= 0
								? listPreference.getEntries()[index]
								: null
				);

			} else {

				preference.setSummary(stringValue);
			}
			return true;
		}
	};


	private static void bindPreferenceSummaryToValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);



		sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
				PreferenceManager
						.getDefaultSharedPreferences(preference.getContext())
						.getString(preference.getKey(), "")
		);
	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class GeneralPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_general);


		}
	}
}