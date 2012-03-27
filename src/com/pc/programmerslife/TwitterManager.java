package com.pc.programmerslife;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask.Status;

import com.pc.framework.json.JSONRequest;
import com.pc.framework.json.JSONRequest.JSONRequestListener;

public class TwitterManager implements JSONRequestListener {
	private static final String TWITTER_LINK = "https://twitter.com/status/user_timeline/programadorREAL.json";
	
	private static TwitterManager sharedInstance; 
	
	private TwitterListener twitterListener;
	private JSONRequest jsonRequest;
	private DatabaseHelper databaseHelper;
	
	public static TwitterManager getInstance(Context context) {
		if (sharedInstance == null)
			sharedInstance = new TwitterManager(context);
		return sharedInstance;
	}
	
	public TwitterManager(Context context) {
		this.databaseHelper = new DatabaseHelper(context);
	}
	
	public void setTwitterListener(TwitterListener twitterListener) {
		this.twitterListener = twitterListener;
	}
	
	public TwitterListener getTwitterListener() {
		return twitterListener;
	}
	
	public void getTweets(TwitterListener listener) {
		if (jsonRequest == null || jsonRequest.getStatus() == Status.FINISHED) {
			this.twitterListener = listener;
			
			jsonRequest = new JSONRequest(this);
			jsonRequest.getAsyncJSON(TWITTER_LINK);
		}
	}
	
	private void parseTweets(JSONArray json) throws JSONException {
		int i, size = json.length();
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(size);
		Tweet tweet;
		JSONObject userObject, tweetObject;
		
		for (i = 0; i < size; i++) {
			tweetObject = json.getJSONObject(i);
			
			userObject = tweetObject.getJSONObject("user");
			
			tweet = new Tweet();
			tweet.setUserName(userObject.getString("name"));
			tweet.setUserPhotoLink(userObject.getString("profile_image_url"));
			tweet.setSource(tweetObject.getString("source"));
			tweet.setText(tweetObject.getString("text"));
			
			tweets.add(tweet);
		}
		
		databaseHelper.saveTweets(tweets);
		
		if (twitterListener != null)
			twitterListener.onFinishGetTweets(tweets);
	}

	@Override
	public void onJSONRequestCompleted(Object json, JSONRequest jsonRequest) {
		if (json != null)
			try {
				parseTweets((JSONArray) json);
			} catch (JSONException e) {
				if (twitterListener != null)
					twitterListener.onFailGetTweets(e);
			}
		else
			if (twitterListener != null)
				twitterListener.onFailGetTweets(null);
	}

	@Override
	public void onJSONRequestFail(Exception e, JSONRequest jsonRequest) {
		if (twitterListener != null)
			twitterListener.onFailGetTweets(e);
	}
	
	public interface TwitterListener {
		public void onFinishGetTweets(ArrayList<Tweet> tweets);
		public void onFailGetTweets(Exception e);
	}
}