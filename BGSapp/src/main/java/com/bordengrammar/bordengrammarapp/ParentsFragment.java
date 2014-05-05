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
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ParentsFragment extends Fragment {
	TextView contact;
	LinearLayout call;
	LinearLayout mail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
	    View root = inflater.inflate(R.layout.fragment_parents, container, false);


	    mail = (LinearLayout) root.findViewById(R.id.linmail);
	    call = (LinearLayout) root.findViewById(R.id.lincall);
	    call.setOnClickListener(new View.OnClickListener(){
		    @Override
		    public void onClick(View arg0) {

			    Intent cally = new Intent(Intent.ACTION_CALL);
			    cally.setData(Uri.parse("tel:01795424192"));
			    startActivity(cally);


		    }
	    });
	    mail.setOnClickListener(new View.OnClickListener(){
		    @Override
		    public void onClick(View arg0) {

			    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

			    alert.setTitle("Send a email to Borden Grammar");
			    alert.setMessage("Message: ");

			    final EditText input = new EditText(getActivity());
			    alert.setView(input);

			    alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int whichButton) {
					    String value = input.getText().toString();
					    // Do something with value!
					    Intent email = new Intent(Intent.ACTION_SEND);
					    email.setType("plain/text");
					    email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "epicfinley@gmail.com" });
					    email.putExtra(Intent.EXTRA_SUBJECT, "Email (Sent From BGS APP) ");
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



	    });


        return root;
    }

}
