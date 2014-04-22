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


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class HomeFragment extends Fragment {
	static final LatLng BORDEN = new LatLng(51.337692, 0.734823);
	private GoogleMap map;
	TextView contact;
	TextView calltext;
	TextView mailtext;
	ImageView callimage;
	ImageView mailimage;
	LinearLayout call;
	LinearLayout mail;
	boolean click;
	String status;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View myInflatedView = inflater.inflate(R.layout.fragment_home, container, false);
		map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		try {
			Marker borden = map.addMarker(new MarkerOptions()
					.position(BORDEN)
					.title("Borden Grammar School")
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.ic_launcher)));
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			map.setMyLocationEnabled(true);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(BORDEN, 15));


			// Zoom in, animating the camera.
			map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		TextView t = (TextView) myInflatedView.findViewById(R.id.tweet);
		if(readPrefs("twitter")=="error"){
			//crouton
			Style style = new Style.Builder()
					.setImageResource(R.drawable.ic_launcher)
					.setTextSize(20)
					.setPaddingInPixels(20)
					.setTextColor(R.color.bordenyellow)
					.setBackgroundColor(R.color.bordenpurple)
					.build();

			Crouton.makeText(getActivity(), R.string.tweeterror, style);
		} else {
			status = readPrefs("twitter");
		}
		t.setText('"' + status + '"');
		TextView t1;
		t1 = (TextView) myInflatedView.findViewById(R.id.date);
		String source2 = "&#64;";
		t1.setText(Html.fromHtml(source2) + "bordengrammar | " + readPrefs("twittertime"));
		TextView info = (TextView) myInflatedView.findViewById(R.id.info);
		String sourceString = "<b>" + "Borden Grammar School" + "</b> " + "is a selective boy's grammar school in Sittingbourne, with a fierce commitment to educate, inspire and prepare students academically and socially";
		info.setText(Html.fromHtml(sourceString));


		//now for contact stuff
		PhoneCallListener phoneListener = new PhoneCallListener();
		TelephonyManager telephonyManager = (TelephonyManager) getActivity().getApplicationContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
		click = false;
		mail = (LinearLayout) myInflatedView.findViewById(R.id.linmail);
		call = (LinearLayout) myInflatedView.findViewById(R.id.lincall);
		contact = (TextView) myInflatedView.findViewById(R.id.contacttext);
		calltext = (TextView) myInflatedView.findViewById(R.id.calltext);
		mailtext = (TextView) myInflatedView.findViewById(R.id.mailtext);
		callimage = (ImageView) myInflatedView.findViewById(R.id.callimage);
		mailimage = (ImageView) myInflatedView.findViewById(R.id.mailimage);
		calltext.setVisibility(View.INVISIBLE);
		mailtext.setVisibility(View.INVISIBLE);
		callimage.setVisibility(View.INVISIBLE);
		mailimage.setVisibility(View.INVISIBLE);
		contact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!click){
					calltext.setVisibility(View.VISIBLE);
					mailtext.setVisibility(View.VISIBLE);
					callimage.setVisibility(View.VISIBLE);
					mailimage.setVisibility(View.VISIBLE);
					contact.setVisibility(View.INVISIBLE);
					click = true;
				}
			}
		});
		call.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(click){
					Intent cally = new Intent(Intent.ACTION_CALL);
					cally.setData(Uri.parse("tel:01795424192"));
					startActivity(cally);
				}

			}
		});
		mail.setOnClickListener(new View.OnClickListener(){
			@Override
		    public void onClick(View arg0) {
				if(click){
					AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

					alert.setTitle("Send a email to Borden Grammar");
					alert.setMessage("Message:");

					final EditText input = new EditText(getActivity());
					alert.setView(input);

					alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							String value = input.getText().toString();
							// Do something with value!
							Intent email = new Intent(Intent.ACTION_SEND);
							email.setType("plain/text");
							email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "epicfinley@gmail.com" });
							email.putExtra(Intent.EXTRA_SUBJECT, "Email(Sent from BGS APP)");
							email.putExtra(Intent.EXTRA_TEXT, value);
							startActivity(Intent.createChooser(email,
									"Choose an Email client :"));
						}
					});

					alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							// Canceled.
						}
					});

					alert.show();
				}

			}

		});
		return myInflatedView;
	}

	@Override
	public void onDestroy() {
		Crouton.cancelAllCroutons();
	}

	private void savePrefs(String key, String value) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		SharedPreferences.Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}
	private class PhoneCallListener extends PhoneStateListener {

		private boolean isPhoneCalling = false;

		String LOG_TAG = "LOGGING 123";

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			if (TelephonyManager.CALL_STATE_RINGING == state) {
				// phone ringing
				Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
			}

			if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
				// active
				Log.i(LOG_TAG, "OFFHOOK");

				isPhoneCalling = true;
			}

			if (TelephonyManager.CALL_STATE_IDLE == state) {
				// run when class initial and phone call ended,
				// need detect flag from CALL_STATE_OFFHOOK
				Log.i(LOG_TAG, "IDLE");

				if (isPhoneCalling) {

					Log.i(LOG_TAG, "restart app");

					// restart app
					Intent i = getActivity().getBaseContext().getPackageManager()
							.getLaunchIntentForPackage(
									getActivity().getBaseContext().getPackageName());
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);

					isPhoneCalling = false;
				}

			}
		}
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


