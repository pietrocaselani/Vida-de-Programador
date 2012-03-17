package com.pc.programmerslife.fragments;


import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.pc.programmerslife.R;

public class TwitterFragment extends SherlockListFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setHasOptionsMenu(true);
		
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.twitter_fragment_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.twitter_menu_actions) {
			Toast.makeText(getSherlockActivity(), "Clicou no item do twitter - Share", Toast.LENGTH_LONG).show();
		}
		return super.onOptionsItemSelected(item);
	}
}