package com.pc.programmerslife.adapters;

import com.viewpagerindicator.TitleProvider;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

public class PageViewAdapter extends FragmentPagerAdapter implements TitleProvider {
	private String[] titles;

	public PageViewAdapter(FragmentManager fm, String[] titles) {
		super(fm);
		this.titles = titles;
	}

	@Override
	public Fragment getItem(int position) {
		return null;
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public String getTitle(int position) {
		return titles[position];
	}
}