package com.pc.programmerslife.activities;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.pc.programmerslife.R;
import com.pc.programmerslife.adapters.PageViewAdapter;
import com.viewpagerindicator.TitlePageIndicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class ProgrammersLifeActivity extends SherlockFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programmers_life_activity);
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.programmersLifeActivity_viewPager);
		viewPager.setAdapter(new PageViewAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.titles)));
		
		TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.programmersLifeActivity_titleIndicator);
		titleIndicator.setViewPager(viewPager);
	}
}