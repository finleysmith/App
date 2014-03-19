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

import com.suredigit.inappfeedback.FeedbackDialog;
import com.suredigit.inappfeedback.FeedbackSettings;

import java.util.List;


public class SettingsActivity extends PreferenceActivity {

	private static final boolean ALWAYS_SIMPLE_PREFS = false;
	private ActionBar actionBar;
	private FeedbackDialog feedBack;

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		setupSimplePreferencesScreen();
		actionBar = getActionBar();
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		FeedbackSettings feedbackSettings = new FeedbackSettings();
		feedbackSettings.setCancelButtonText("Cancel");
		feedbackSettings.setSendButtonText("Send");
		feedbackSettings.setText("Send feedback to improve the app");
		feedbackSettings.setTitle("Feedback");
		feedbackSettings.setToast("We value your feedback");
		feedBack = new FeedbackDialog(this, "AF-186C1F794D93-1A", feedbackSettings);
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
				Intent l = new Intent(SettingsActivity.this, MainActivity.class);
				startActivity(l);
				return true; //break it so it does not go onto next case
			case R.id.facebook: //if it is facebook button

				Intent faceBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/BordenGrammarSchool"));  //Create intent variable
				startActivity(faceBrowserIntent); //Start that intent
				return true;
			case R.id.website: //if they clicked they

				Intent websiteBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://website.bordengrammar.kent.sch.uk/")); //Same as above
				startActivity(websiteBrowserIntent);
				return true;
			case R.id.action_settings: //action settings actually about, can't change it now

				Intent i = new Intent(SettingsActivity.this, AboutActivity.class);
				startActivity(i);

				return true;
			case R.id.action_feedback: //if they clciked send feedback

				feedBack.show(); //show the feedback that we declared
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	protected boolean isValidFragment(String fragmentName) {
		// We only accept our own fragments to use, or those provided by the super
		return GeneralPreferenceFragment.class.getName().equals(fragmentName) ||
				super.isValidFragment(fragmentName);
	}
	private void setupSimplePreferencesScreen() {
		if (!isSimplePreferences(this)) {
			return;
		}
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onBuildHeaders(List<Header> target) {
		if (!isSimplePreferences(this)) {
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
				// For all other preferences, set the summary to the value's
				// simple string representation.
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