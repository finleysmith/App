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

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Finley on 5/4/2014.
 */

public class BGSWidgetService extends Service {
	public static final String UPDATEMOOD = "UpdateMood";
	public static final String CURRENTMOOD = "CurrentMood";

	private String currentMood;
	private LinkedList<String> moods;

	public void CurrentMoodService(){
		this.moods = new LinkedList<String>();
		fillMoodsList();
	}

	private void fillMoodsList() {
		this.moods.add(":)");
		this.moods.add(":(");
		this.moods.add(":D");
		this.moods.add(":X");
		this.moods.add(":S");
		this.moods.add(";)");

		this.currentMood = ";)";
	}
	public String hour() {
			/*
		    * Work out whether tommorow is right day, (Right meaning mon-fri)
		    * If it is work out the amount of hours and minutes until 8.30am tommorow
			* Else work out how many days, hours and minutes until monday 8.30am
			*
			 */
		String result;
		Calendar now = Calendar.getInstance();
		int currentDay = now.get(Calendar.DAY_OF_WEEK);

		Calendar school = Calendar.getInstance();
		school.add(Calendar.DAY_OF_YEAR, 1);
		if (currentDay == Calendar.SATURDAY)
		{
			school.add(Calendar.DAY_OF_YEAR, 1);
		}
		else if (currentDay == Calendar.FRIDAY)
		{
			school.add(Calendar.DAY_OF_YEAR, 2);
		}

		school.set(Calendar.HOUR_OF_DAY, 8);
		school.set(Calendar.MINUTE, 30);
		long millisLeft = school.getTimeInMillis() - now.getTimeInMillis();
		long hoursLeft = millisLeft / (60 * 60 * 1000);
		String hours = Long.toString(hoursLeft);
		return hours;


	}
	public String minutes() {
			/*
		    * Work out whether tommorow is right day, (Right meaning mon-fri)
		    * If it is work out the amount of hours and minutes until 8.30am tommorow
			* Else work out how many days, hours and minutes until monday 8.30am
			*
			 */
		Calendar now = Calendar.getInstance();
		int currentDay = now.get(Calendar.DAY_OF_WEEK);

		Calendar school = Calendar.getInstance();
		school.add(Calendar.DAY_OF_YEAR, 1);
		if (currentDay == Calendar.SATURDAY)
		{
			school.add(Calendar.DAY_OF_YEAR, 1);
		}
		else if (currentDay == Calendar.FRIDAY)
		{
			school.add(Calendar.DAY_OF_YEAR, 2);
		}

		school.set(Calendar.HOUR_OF_DAY, 8);
		school.set(Calendar.MINUTE, 30);

		long millisLeft = school.getTimeInMillis() - now.getTimeInMillis();
		long minutesLeft =  (millisLeft % (60 * 60 * 1000)) / (60 * 1000);
		String minutes = Long.toString(minutesLeft);


		return minutes;


	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStart(intent, startId);

		Log.i(BGSWidgetProvider.WIDGETTAG, "onStartCommand");

		//updateMood(intent);

		stopSelf(startId);

		return START_STICKY;
	}

	private String getRandomMood() {
		Random r = new Random(Calendar.getInstance().getTimeInMillis());
		int pos = r.nextInt(moods.size());
		return moods.get(pos);
	}

	private void updateMood(Intent intent) {
		Log.i(BGSWidgetProvider.WIDGETTAG, "This is the intent " + intent);
		if (intent != null){
			String requestedAction = intent.getAction();
			Log.i(BGSWidgetProvider.WIDGETTAG, "This is the action " + requestedAction);
			if (requestedAction != null && requestedAction.equals(UPDATEMOOD)){
				//this.currentMood = getRandomMood();

				int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);

				//Log.i(BGSWidgetProvider.WIDGETTAG, "This is the currentMood " + currentMood + " to widget " + widgetId);

				AppWidgetManager appWidgetMan = AppWidgetManager.getInstance(this);
				RemoteViews views = new RemoteViews(this.getPackageName(),R.layout.widget_layout);
				views.setTextViewText(R.id.hours, hour());
				views.setTextViewText(R.id.minutes, minutes());
				appWidgetMan.updateAppWidget(widgetId, views);

				Log.i(BGSWidgetProvider.WIDGETTAG, "CurrentMood updated!");
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
