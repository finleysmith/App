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

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bordengrammar.bordengrammarapp.utils.ut;


public class Licenses extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licenses);
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
				Intent home = new Intent(Licenses.this, MainActivity.class);
				startActivity(home);
				ut.logIt("Used home button");
				return true;
			case R.id.facebook:
				ut.logIt("Facebook button clicked");
				Intent faceBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/BordenGrammarSchool"));
				startActivity(faceBrowserIntent);
				return true;
			case R.id.website:
				ut.logIt("Website Clicked");
				Intent websiteBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://website.bordengrammar.kent.sch.uk/"));
				startActivity(websiteBrowserIntent);
				return true;
			case R.id.action_settings:
				ut.logIt("clicked about");
				Intent i = new Intent(Licenses.this, AboutActivity.class);
				startActivity(i);
				return true;
			case R.id.action_feedback:
				Toast.makeText(this, "Sorry but this is not available on this page. Try another!", Toast.LENGTH_LONG);
				return true;
			case R.id.action_realsettings:
				ut.logIt("settings");
				Intent s = new Intent(Licenses.this, SettingsActivity.class);
				startActivity(s);
				return true;
			case R.id.action_privacy:
				Intent ss = new Intent(Licenses.this, PrivacyActivity.class);
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
}
