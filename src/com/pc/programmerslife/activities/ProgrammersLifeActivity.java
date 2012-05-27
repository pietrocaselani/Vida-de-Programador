package com.pc.programmerslife.activities;

import com.pc.programmerslife.CommicManager;
import com.pc.programmerslife.R;
import com.pc.programmerslife.TwitterManager;
import com.pc.programmerslife.adapters.ProgrammersLifeViewAdapter;
import com.viewpagerindicator.TabPageIndicator;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class ProgrammersLifeActivity extends SherlockFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programmers_life_activity);
		
		if (savedInstanceState == null) {
			CommicManager.getInstance().startDatabase(this);
			TwitterManager.getInstance().startDatabase(this);
		}
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.programmersLifeActivity_viewPager);
		
		ProgrammersLifeViewAdapter pageAdapter = new ProgrammersLifeViewAdapter(this);
		viewPager.setAdapter(pageAdapter);
		
		TabPageIndicator tabPageIndicator = (TabPageIndicator) findViewById(R.id.programmersLifeActivity_page_indicator);
		tabPageIndicator.setViewPager(viewPager);
	}
}