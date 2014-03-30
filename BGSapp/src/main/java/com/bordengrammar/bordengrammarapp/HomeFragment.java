package com.bordengrammar.bordengrammarapp;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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


public class HomeFragment extends Fragment {
	static final LatLng BORDEN = new LatLng(51.337692, 0.734823);
	private GoogleMap map;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


	    View myInflatedView = inflater.inflate(R.layout.fragment_home, container,false);

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
	    t = (TextView) myInflatedView.findViewById(R.id.textView3);
	    t.setText('"' + readPrefs("twitter") + '"');


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
		return sp.getString(key, "An error occured whilst fetching twitter feed");
	}











}
