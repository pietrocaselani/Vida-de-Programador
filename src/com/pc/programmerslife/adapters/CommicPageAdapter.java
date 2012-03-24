package com.pc.programmerslife.adapters;

import com.pc.programmerslife.fragments.CommicFragment;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CommicPageAdapter extends FragmentStatePagerAdapter {
	private Bitmap[] bitmaps;

	public CommicPageAdapter(FragmentManager fm, Bitmap[] bitmaps) {
		super(fm);
		this.bitmaps = bitmaps;
	}

	@Override
	public Fragment getItem(int position) {
		return CommicFragment.newInstance(bitmaps[position]);
	}

	@Override
	public int getCount() {
		return bitmaps.length;
	}
}