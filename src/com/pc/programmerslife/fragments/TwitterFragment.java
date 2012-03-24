package com.pc.programmerslife.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.pc.programmerslife.Manager;
import com.pc.programmerslife.Manager.TwitterListener;
import com.pc.programmerslife.Tweet;

public class TwitterFragment extends SherlockListFragment implements TwitterListener {
	private ArrayList<Tweet> tweets;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setListShown(false);
		
		tweets = new ArrayList<Tweet>();
		
		Manager.getInstance(getSherlockActivity()).getTweets(this);
	}

	@Override
	public void onFinishGetTweets(ArrayList<Tweet> tweets) {
		Toast.makeText(getSherlockActivity(), "Baixou " + String.valueOf(tweets.size()) + " tweets", Toast.LENGTH_SHORT).show();
		
		this.tweets.clear();
		this.tweets.addAll(tweets);
		
		setListShown(true);
	}

	@Override
	public void onFailGetTweets(Exception e) {
		Toast.makeText(getSherlockActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
	}
}