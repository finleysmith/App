package com.bordengrammar.bordengrammarapp;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class Application extends android.app.Application {

	public Application() {
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// Initialize the Parse SDK.
		Parse.initialize(this, "yovKfUASIkl14OmRLMT5sXSJvrySDoS8MLwJ7pAA", "mnVmfBTnSzYneOxuf3jUiRKNs9P53ipsdkSwt5dq");

		// Specify an Activity to handle all pushes by default.
		PushService.setDefaultPushCallback(this, MainActivity.class);
	}
}