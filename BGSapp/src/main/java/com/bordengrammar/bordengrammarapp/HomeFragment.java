package com.bordengrammar.bordengrammarapp;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class HomeFragment extends Fragment {
	public static String TWEET;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

	    View myInflatedView = inflater.inflate(R.layout.fragment_home, container,false);


	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
			    .setOAuthConsumerKey("Toqp03fcUErG5P8e9nhfsw")
			    .setOAuthConsumerSecret(
					    "SEqktstO9h7SqSm7zmcuWlH3bOtElJm1Ds2TFSwFBc")
			    .setOAuthAccessToken(
					    "2245935685-U5LMfl4oEcOv6Khw58JZqRdcH2PlABEeUP2JeXj")
			    .setOAuthAccessTokenSecret(
					    "uFcGRpCx8aGXdv3AiAkfVImnoLrlNNCUnZ2UtE76Zbnpa");
	    TwitterFactory tf = new TwitterFactory(cb.build());
	    Twitter twitter = tf.getInstance();
	    List<twitter4j.Status> statuses = null;
	    String user;
	    user = "epicfinley";
	    try {
		    statuses = twitter.getUserTimeline(user);
	    } catch (TwitterException e) {
		    e.printStackTrace();
	    }
	    twitter4j.Status status = statuses.get(0);
	    TextView t;
	    t = (TextView) myInflatedView.findViewById(R.id.textView3);
	    t.setText('"' + status.getText() + '"');


	    return myInflatedView;
    }

	private void savePrefs(String key, String value) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		SharedPreferences.Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}







}
