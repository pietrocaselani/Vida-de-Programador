package com.pc.programmerslife.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.actionbarsherlock.app.SherlockFragment;
import com.pc.programmerslife.CommicManager;
import com.pc.programmerslife.R;
import com.pc.programmerslife.TwitterManager;
import com.pc.programmerslife.adapters.ProgrammersLifeViewAdapter;

public class ProgrammersLifeFragment extends SherlockFragment {
	private static final String COMMICS_SPEC_TAG = "Commics";
	private static final String FAVORITES_SPEC_TAG = "Favorites";
	private static final String TWITTER_SPEC_TAG = "Twitter";
	private static final String INFO_SPEC_TAG = "Info";
	private static final String CURRENT_TAG = "TabTag";
	
	private TabHost tabHost;
    private ViewPager viewPager;
    private ProgrammersLifeViewAdapter pageViewAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.programmers_life_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getSherlockActivity().getSupportActionBar().setTitle(R.string.app_name);
		
		View view = getView();
		
		tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
		tabHost.setup();
		
		viewPager = (ViewPager) view.findViewById(R.id.programmersLifeFragment_viewPager);
		
		pageViewAdapter = new ProgrammersLifeViewAdapter(getSherlockActivity(), tabHost, viewPager);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				viewPager.setAdapter(pageViewAdapter);
			}
		}, 1);
		
		pageViewAdapter.addTab(tabHost.newTabSpec(COMMICS_SPEC_TAG).setIndicator(getString(R.string.commics)), CommicsFragment.class, null);
		pageViewAdapter.addTab(tabHost.newTabSpec(FAVORITES_SPEC_TAG).setIndicator(getString(R.string.favorites)), FavoritesFragment.class, null);
		pageViewAdapter.addTab(tabHost.newTabSpec(TWITTER_SPEC_TAG).setIndicator(getString(R.string.twitter)), TwitterFragment.class, null);
		pageViewAdapter.addTab(tabHost.newTabSpec(INFO_SPEC_TAG).setIndicator(getString(R.string.informations)), InformationsFragment.class, null);
		
		if (savedInstanceState != null)
			tabHost.setCurrentTabByTag(savedInstanceState.getString(CURRENT_TAG));
		else {
			CommicManager.getInstance().startDatabase(getSherlockActivity());
			TwitterManager.getInstance().startDatabase(getSherlockActivity());
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putString(CURRENT_TAG, tabHost.getCurrentTabTag());
	}
}