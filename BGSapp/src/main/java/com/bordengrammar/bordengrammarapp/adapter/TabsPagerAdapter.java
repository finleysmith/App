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

package com.bordengrammar.bordengrammarapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bordengrammar.bordengrammarapp.HomeFragment;
import com.bordengrammar.bordengrammarapp.ParentsFragment;
import com.bordengrammar.bordengrammarapp.StudentsFragment;

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
	public CharSequence getPageTitle(int position) {

		if (position == 0)
		{
			return "Home";
		}
		if (position == 1)
		{
			return "Parents";
		}
		if (position == 2)
		{
			return "Students";
		}
		return null;
	}

}
//random comment for video on git gui
