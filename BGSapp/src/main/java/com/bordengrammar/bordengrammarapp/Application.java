package com.bordengrammar.bordengrammarapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.parse.Parse;
import com.parse.PushService;

public class Application extends android.app.Application {

	public String TAG = "Application";

	public Application() {
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// Initialize the Parse SDK.
		Parse.initialize(this, "yovKfUASIkl14OmRLMT5sXSJvrySDoS8MLwJ7pAA", "mnVmfBTnSzYneOxuf3jUiRKNs9P53ipsdkSwt5dq");


		// Specify an Activity to handle all pushes by default.

		SharedPreferences mainsettings = PreferenceManager.getDefaultSharedPreferences(this);
		Boolean push = mainsettings.getBoolean("example_checkbox", false);
		if(push) {
			PushService.setDefaultPushCallback(this, MainActivity.class);
		} else {
			PushService.setDefaultPushCallback(this, null);
		}
	}
}