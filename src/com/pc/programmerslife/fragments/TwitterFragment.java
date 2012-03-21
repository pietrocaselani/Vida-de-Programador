package com.pc.programmerslife.fragments;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockListFragment;

public class TwitterFragment extends SherlockListFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setHasOptionsMenu(true);
	}
}