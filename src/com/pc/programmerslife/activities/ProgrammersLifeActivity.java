package com.pc.programmerslife.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.pc.programmerslife.R;
import com.pc.programmerslife.adapters.PageViewAdapter;
import com.pc.programmerslife.fragments.CommicsFragment;
import com.pc.programmerslife.fragments.InformationsFragment;
import com.pc.programmerslife.fragments.TwitterFragment;

public class ProgrammersLifeActivity extends SherlockFragmentActivity {
	private static final String COMMICS_SPEC_TAG = "Commics";
	private static final String TWITTER_SPEC_TAG = "Twitter";
	private static final String INFO_SPEC_TAG = "Info";
	private static final String CURRENT_TAG = "TabTag";
	
	TabHost tabHost;
    ViewPager  viewPager;
    PageViewAdapter pageViewAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programmers_life_activity);
		
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		
		viewPager = (ViewPager) findViewById(R.id.programmersLifeActivity_viewPager);
		
		pageViewAdapter = new PageViewAdapter(this, tabHost, viewPager);
		
		pageViewAdapter.addTab(tabHost.newTabSpec(COMMICS_SPEC_TAG).setIndicator(getString(R.string.commics)), CommicsFragment.class, null);
		pageViewAdapter.addTab(tabHost.newTabSpec(TWITTER_SPEC_TAG).setIndicator(getString(R.string.twitter)), TwitterFragment.class, null);
		pageViewAdapter.addTab(tabHost.newTabSpec(INFO_SPEC_TAG).setIndicator(getString(R.string.informations)), InformationsFragment.class, null);
		
		if (savedInstanceState != null)
			tabHost.setCurrentTabByTag(savedInstanceState.getString(CURRENT_TAG));
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(CURRENT_TAG, tabHost.getCurrentTabTag());
	}
}