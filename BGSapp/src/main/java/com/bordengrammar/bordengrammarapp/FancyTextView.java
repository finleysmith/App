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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;



public class FancyTextView extends TextView {
	public FancyTextView(Context context) {
		super(context);
		onInitTypeface(context, null, 0);
	}

	
	public FancyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		onInitTypeface(context, attrs, 0);
	}

	
	public FancyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		onInitTypeface(context, attrs, defStyle);
	}

	
	private void onInitTypeface(Context context, AttributeSet attrs, int defStyle) {
		// Typeface.createFromAsset doesn't work in the layout editor, so skipping.
		if (isInEditMode()) {
			return;
		}

		int typefaceValue = 0;
		if (attrs != null) {
			TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.FancyTextView, defStyle, 0);
			typefaceValue = values.getInt(R.styleable.FancyTextView_typeface, 0);
			values.recycle();
		}

		Typeface fancyTypeface = FancyTypefaceManager.obtaintTypeface(context, typefaceValue);
		setTypeface(fancyTypeface);
	}
}
