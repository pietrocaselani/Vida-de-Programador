package com.pc.programmerslife.fragments;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.pc.programmerslife.Manager;
import com.pc.programmerslife.Manager.TwitterListener;
import com.pc.programmerslife.R;
import com.pc.programmerslife.Tweet;
import com.pc.programmerslife.adapters.TwitterListAdapter;

public class TwitterFragment extends SherlockListFragment implements TwitterListener {
	private ArrayList<Tweet> tweets;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getListView().setCacheColorHint(Color.TRANSPARENT);
		getListView().setDividerHeight(5);
		getListView().setDivider(new ColorDrawable(Color.BLACK));
//		getListView().setBackgroundColor(Color.TRANSPARENT);
		getListView().setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		getListView().setSelector(new ColorDrawable(Color.TRANSPARENT));
		
		if (savedInstanceState == null)
			tweets = new ArrayList<Tweet>();
		else
			tweets = savedInstanceState.getParcelableArrayList(Tweet.EXTRA_TWEETS);
		
		if (tweets != null) {
			setListAdapter(new TwitterListAdapter(getSherlockActivity(), R.layout.tweet_item_layout, tweets));
			
			setListShown(tweets.size() > 0);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		Manager.getInstance(getSherlockActivity()).getTweets(this);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(Tweet.EXTRA_TWEETS, tweets);
	}

	@Override
	public void onFinishGetTweets(ArrayList<Tweet> tweets) {
		this.tweets.clear();
		this.tweets.addAll(tweets);
		
		((TwitterListAdapter) getListAdapter()).notifyDataSetChanged();
		
		if (isResumed() == true) {
			setListShown(true);
		}
	}

	@Override
	public void onFailGetTweets(Exception e) {
		Toast.makeText(getSherlockActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
	}
}