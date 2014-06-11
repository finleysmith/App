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

package com.bordengrammar.bordengrammarapp.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by Finley on 4/12/2014.
 */

/* This is a class to stop me from typing long log commands forever */
public class ut {
	public static String TAG = "Logger";





	public static void logIt(String it) {
		Log.e(TAG, it);

	}
	public static void startup() {
		logIt("-----------------------------------------------------");
		logIt("-                     BGS App                       -");
		logIt("-----------------------------------------------------");
		logIt("-                                                   -");
		String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		logIt("-              " + mydate + "             -");
		logIt("-----------------------------------------------------");

	}
	public static void DownloadFile(String fileURL, File directory) {
		try {

			FileOutputStream f = new FileOutputStream(directory);
			URL u = new URL(fileURL);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setDoOutput(true);
			c.connect();

			InputStream in = c.getInputStream();

			byte[] buffer = new byte[1024];
			int len1 = 0;
			while ((len1 = in.read(buffer)) > 0) {
				f.write(buffer, 0, len1);
			}
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private Context _context;

	public void ConnectionDetector(Context context){
		this._context = context;
	}

	public boolean isConnectingToInternet(){
		ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null)
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}

		}
		return false;
	}

}
