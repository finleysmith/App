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
	    Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
			    try {

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
				    try {
					    List<twitter4j.Status> statuses;
					    String user;
					    user = "epicfinley";
					    statuses = twitter.getUserTimeline(user);
					    twitter4j.Status status = statuses.get(0);
					    TWEET = status.getText();


				    } catch (TwitterException te) {
					    te.printStackTrace();
				    }
			    } catch (Exception e) {
				    e.printStackTrace();
			    }
		    }
	    });

	    TextView t;
	    assert myInflatedView != null;
	    t = (TextView) myInflatedView.findViewById(R.id.textView3);
	    if(TWEET != null && !TWEET.isEmpty()){
		    //t.setVisibility(View.VISIBLE);
		    t.setText(TWEET);
		    savePrefs("TWEETY", TWEET);
	    } else {
		    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		    String name = sp.getString("TWEETY", "First time? Explore all the tabs!");
		    if (name != null && !name.isEmpty()) {
			    t.setText(name);

		    } else {
			    t.setVisibility(View.INVISIBLE);
		    }
	    }
	    thread.start();
	    return myInflatedView;
    }


	private void savePrefs(String key, String value) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		SharedPreferences.Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}







}
