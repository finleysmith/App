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

import com.bordengrammar.bordengrammarapp.utils.ut;

import java.net.MalformedURLException;
import java.net.URL;

import it.sauronsoftware.feed4j.FeedIOException;
import it.sauronsoftware.feed4j.FeedParser;
import it.sauronsoftware.feed4j.FeedXMLParseException;
import it.sauronsoftware.feed4j.UnsupportedFeedException;
import it.sauronsoftware.feed4j.bean.Feed;
import it.sauronsoftware.feed4j.bean.FeedItem;

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
					    email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "school@bordengrammar.kent.sch.uk" });
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
	    URL url = null;
	    try {
		    url = new URL("http://website.bordengrammar.kent.sch.uk/index.php?option=com_rubberdoc&view=category&id=63%3Aletters&Itemid=241&format=feed&type=rss");
	    } catch (MalformedURLException e) {
		    e.printStackTrace();
	    }

	    Feed feed = null;
	    try {
		    feed = FeedParser.parse(url);
	    } catch (FeedIOException e) {
		    e.printStackTrace();
	    } catch (FeedXMLParseException e) {
		    e.printStackTrace();
	    } catch (UnsupportedFeedException e) {
		    e.printStackTrace();
	    }
	    int items = feed.getItemCount();
	    for (int i = 0; i < 3; i++) {
		    FeedItem item = feed.getItem(i);
		    ut.logIt("Title: " + item.getTitle());
		    ut.logIt("Link: " + item.getLink());
	    }
	    final FeedItem post1 = feed.getItem(1);
	    final FeedItem post2 = feed.getItem(2);
	    final FeedItem post3 = feed.getItem(3);
	    TextView title1 = (TextView)root.findViewById(R.id.title1);
	    TextView title2 = (TextView)root.findViewById(R.id.title2);
	    TextView title3 = (TextView)root.findViewById(R.id.title3);
	    title1.setText(post1.getTitle());
	    title2.setText(post2.getTitle());
	    title3.setText(post3.getTitle());
	    TextView link1 = (TextView)root.findViewById(R.id.link1);
	    TextView link2 = (TextView)root.findViewById(R.id.link2);
	    TextView link3 = (TextView)root.findViewById(R.id.link3);
	    link1.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    String url1 = post1.getLink().toString();
			    Intent intent = new Intent(Intent.ACTION_VIEW);
			    intent.setDataAndType(Uri.parse(url1), "text/html");
			    startActivity(intent);
		    }
	    });
	    link2.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    String url1 = post2.getLink().toString();
			    Intent intent = new Intent(Intent.ACTION_VIEW);
			    intent.setDataAndType(Uri.parse(url1), "text/html");
			    startActivity(intent);
		    }
	    });
	    link3.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    String url1 = post3.getLink().toString();
			    Intent intent = new Intent(Intent.ACTION_VIEW);
			    intent.setDataAndType(Uri.parse(url1), "text/html");
			    startActivity(intent);
		    }
	    });
	    TextView reader = (TextView)root.findViewById(R.id.reader);
	    reader.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    final String appPackageName = "com.adobe.reader"; // getPackageName() from Context or Activity object
			    try {
				    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
			    } catch (android.content.ActivityNotFoundException anfe) {
				    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
			    }
		    }
	    });




        return root;
    }

}
