package com.bordengrammar.bordengrammarapp.adapter;

import com.bordengrammar.bordengrammarapp.ParentsFragment;
import com.bordengrammar.bordengrammarapp.HomeFragment;
import com.bordengrammar.bordengrammarapp.StudentsFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Home fragment activity
			return new HomeFragment();
		case 1:
			// Parents fragment activity
			return new ParentsFragment();
		case 2:
			// Students? fragment activity
			return new StudentsFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// THIS MUST EQUAL THE NUMBER OF TABS YOU HAVE
		return 3;
	}

}
//random comment for video on git gui
