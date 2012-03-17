package com.pc.programmerslife.fragments;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;

public class InformationsFragment extends SherlockFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setHasOptionsMenu(true);
	}
}