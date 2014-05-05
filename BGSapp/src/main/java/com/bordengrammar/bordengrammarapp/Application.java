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

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.parse.Parse;
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

		SharedPreferences mainsettings = PreferenceManager.getDefaultSharedPreferences(this);
		Boolean push = mainsettings.getBoolean("example_checkbox", false);
		if(push) PushService.setDefaultPushCallback(this, SplashActivity.class);
		else {
			PushService.setDefaultPushCallback(this, null);
		}
	}
}