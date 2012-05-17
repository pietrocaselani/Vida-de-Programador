package com.pc.programmerslife.adapters;

import com.pc.programmerslife.fragments.BitmapFragment;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class BitmapPageAdapter extends FragmentStatePagerAdapter {
	private Bitmap[] bitmaps;

	public BitmapPageAdapter(FragmentManager fm, Bitmap[] bitmaps) {
		super(fm);
		this.bitmaps = bitmaps;
	}

	@Override
	public Fragment getItem(int position) {
		return BitmapFragment.newInstance(bitmaps[position]);
	}

	@Override
	public int getCount() {
		return bitmaps.length;
	}
}