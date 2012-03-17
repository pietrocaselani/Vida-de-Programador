package com.pc.programmerslife.adapters;

import com.pc.programmerslife.fragments.CommicsFragment;
import com.pc.programmerslife.fragments.InformationsFragment;
import com.pc.programmerslife.fragments.TwitterFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

public class PageViewAdapter extends FragmentPagerAdapter implements OnTabChangeListener, OnPageChangeListener {
	private Context context;
	private TabHost tabHost;
	private ViewPager viewPager;
	
	public PageViewAdapter(FragmentActivity activity, TabHost tabHost, ViewPager viewPager) {
		super(activity.getSupportFragmentManager());
		
		this.context = activity;
		this.tabHost = tabHost;
		this.viewPager = viewPager;
		
		tabHost.setOnTabChangedListener(this);
		
		viewPager.setAdapter(this);
		viewPager.setOnPageChangeListener(this);
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0)
			return new CommicsFragment();	
		else if (position == 1)
			return new TwitterFragment();
		else if (position == 2)
			return new InformationsFragment();
		return null;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		TabWidget widget = tabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        tabHost.setCurrentTab(position);
        widget.setDescendantFocusability(oldFocusability);
	}

	@Override
	public void onTabChanged(String tabId) {
		viewPager.setCurrentItem(tabHost.getCurrentTab());
	}
	
	public void addTab(TabSpec spec, Class<?> clss, Bundle args) {
		spec.setContent(new DummyTabFactory(context));
		tabHost.addTab(spec);
		notifyDataSetChanged();
	}
		
	static class DummyTabFactory implements TabHost.TabContentFactory {
        private final Context context;

        public DummyTabFactory(Context context) {
            this.context = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(context);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }
}