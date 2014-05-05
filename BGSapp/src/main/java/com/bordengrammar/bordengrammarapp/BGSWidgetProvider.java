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

/**
 * Created by Finley on 5/2/2014.
 */

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class BGSWidgetProvider extends AppWidgetProvider {
	public static final String WIDGETTAG = "WidgetMood";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	                     int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		Log.i(WIDGETTAG, "onUpdate");

		final int N = appWidgetIds.length;

		// Perform this loop procedure for each App Widget that belongs to this provider
		for (int i=0; i<N; i++) {
			int appWidgetId = appWidgetIds[i];

			Log.i(WIDGETTAG, "updating widget[id] " + appWidgetId);

			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

			Intent intent = new Intent(context, BGSWidgetService.class);
			intent.setAction(BGSWidgetService.UPDATEMOOD);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

			views.setOnClickPendingIntent(R.id.refresh, pendingIntent);
			Log.i(WIDGETTAG, "pending intent set");

			// Tell the AppWidgetManager to perform an update on the current App Widget
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}
