package com.bordengrammar.bordengrammarapp.adapter;

import com.bordengrammar.bordengrammarapp.GamesFragment;
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
			return new GamesFragment();
		case 2:
			// Students? fragment activity
			return new StudentsFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
