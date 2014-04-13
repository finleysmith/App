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

import android.util.Log;

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
		logIt("-              " + mydate + "                   -");
		logIt("-----------------------------------------------------");

	}
}
