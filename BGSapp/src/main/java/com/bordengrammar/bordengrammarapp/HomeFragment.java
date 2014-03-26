package com.bordengrammar.bordengrammarapp;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class HomeFragment extends Fragment implements OnClickListener {
	public static String TWEET;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

	    // -- inflate the layout for this fragment
	    //SharedPreferences mainsettings = this.getActivity().getSharedPreferences("twitter", 0);
	    View myInflatedView = inflater.inflate(R.layout.fragment_home, container,false);



	    // Set the Text to try this out
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
					    Log.i("Status Count", statuses.size() + " Feeds");
					    twitter4j.Status status = statuses.get(1);
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
	    t = (TextView) myInflatedView.findViewById(R.id.textView3);
	    if(TWEET != null && !TWEET.isEmpty()){
		    t.setVisibility(View.VISIBLE);
		    t.setText(TWEET);
	    } else {
		    t.setVisibility(View.INVISIBLE);
	    }


	    thread.start();




	    return myInflatedView;




    }






	@Override
    public void onClick(View v) {
        /* TODO Auto-generated method stub */

    }
}
