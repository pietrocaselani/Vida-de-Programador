package com.pc.programmerslife.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.pc.programmerslife.R;
import com.pc.programmerslife.adapters.InfoAdapter;

public class InformationsFragment extends SherlockListFragment {
	private ArrayList<String> infos;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		infos = new ArrayList<String>(2);
		infos.add(getString(R.string.site));
		infos.add(getString(R.string.app));
		
//		getListView().setSelector(android.R.drawable.list_selector_background);
		getListView().setDivider(getResources().getDrawable(R.drawable.list_divider_holo_light));
		
		setListAdapter(new InfoAdapter(getSherlockActivity(), R.layout.info_item_layout, infos));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.informations_fragment, container, false);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	}
}