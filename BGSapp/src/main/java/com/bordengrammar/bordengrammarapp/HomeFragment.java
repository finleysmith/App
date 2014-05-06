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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bordengrammar.bordengrammarapp.utils.ut;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.keyboardsurfer.android.widget.crouton.Crouton;


public class HomeFragment extends Fragment {
	static final LatLng BORDEN = new LatLng(51.337692, 0.734823);
	private GoogleMap map;
	//String status;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View myInflatedView = inflater.inflate(R.layout.fragment_home, container, false);
		map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		ut.logIt("1");
		try {
			Marker borden = map.addMarker(new MarkerOptions()
					.position(BORDEN)
					.snippet("This is where we are")
					.title("Borden Grammar School")
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.ic_launcher)));
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			map.setMyLocationEnabled(true);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(BORDEN, 15));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}



		TextView t = (TextView) myInflatedView.findViewById(R.id.tweet);
		t.setText('"' + readPrefs("twitter") + '"');
		TextView t1;


		t1 = (TextView) myInflatedView.findViewById(R.id.date);
		String t1source = "<b>" + "&#64;" + "bordengrammar" + "</b>" + " | " + readPrefs("twittertime");
		t1.setText(Html.fromHtml(t1source));


		TextView info = (TextView) myInflatedView.findViewById(R.id.info);
		String sourceString = "<b>" + "Borden Grammar School" + "</b> " + "is a selective boy's grammar school in Sittingbourne, with a fierce commitment to educate, inspire and prepare students academically and socially";
		info.setText(Html.fromHtml(sourceString));



		//now for contact stuff


		return myInflatedView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Crouton.cancelAllCroutons();
	}



	public void onDestroyView() {
		super.onDestroyView();

		try {
			Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));
			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.remove(fragment);
			ft.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readPrefs(String key) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		return sp.getString(key, "error");

	}





}


