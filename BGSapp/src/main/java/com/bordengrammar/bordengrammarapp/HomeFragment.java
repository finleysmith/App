package com.bordengrammar.bordengrammarapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
	static final LatLng BORDEN = new LatLng(51.337692, 0.734823);
	private GoogleMap map;
	private GMapV2Direction md;
	public static LatLng fromvar;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {


		View myInflatedView = inflater.inflate(R.layout.fragment_home, container, false);
		md = new GMapV2Direction();

		map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();


		Marker borden = map.addMarker(new MarkerOptions()
				.position(BORDEN)
				.title("Borden Grammar School")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_launcher)));

		// Move the camera instantly to borden with a zoom of 15.
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		map.setTrafficEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(BORDEN, 15));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);


		TextView t;
		assert myInflatedView != null;
		t = (TextView) myInflatedView.findViewById(R.id.tweet);
		t.setText('"' + readPrefs("twitter") + '"');
		TextView t1;
		t1 = (TextView) myInflatedView.findViewById(R.id.date);
		t1.setText("bordengrammar | " + readPrefs("twittertime"));

		//Typeface normal = Typeface.createFromAsset(getActivity().getResources().getAssets(), "fonts/normal.ttf");
		//Typeface light = Typeface.createFromAsset(getActivity().getResources().getAssets(), "fonts/light.tff");
		//Typeface extralight = Typeface.createFromAsset(getActivity().getResources().getAssets(), "fonts/extralight.tff");
		//TextView title = (TextView) myInflatedView.findViewById(R.id.title);
		TextView info = (TextView) myInflatedView.findViewById(R.id.info);
		//info.setTypeface(light);
		//title.setTypeface(extralight);
		//t1.setTypeface(extralight);

		//make text bold
		String sourceString = "<b>" + "Borden Grammar School" + "</b> " + "is a selective boy's grammar school in Sittingbourne, with a fierce commitment to educate, inspire and prepare students academically and socially";
		info.setText(Html.fromHtml(sourceString));





		//getdriving();


		return myInflatedView;
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
		return sp.getString(key, "Error.");

	}




	public void getdriving() {
		LocationManager locationManager =
				(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		LatLng from = new LatLng(latitude, longitude);
		real(from);

	}
	public void real(LatLng from){
		LatLng to = new LatLng(51.337692, 0.734823);
		GoogleMap mMap = ((SupportMapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		GMapV2Direction md = new GMapV2Direction();
		Document doc = md.getDocument(from, to, GMapV2Direction.MODE_DRIVING);
		ArrayList<LatLng> directionPoint = md.getDirection(doc);
		PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.BLUE);
		for (int i = 0; i < directionPoint.size(); i++) {
			rectLine.add(directionPoint.get(i));
		}

		mMap.addPolyline(rectLine);
	}
}


