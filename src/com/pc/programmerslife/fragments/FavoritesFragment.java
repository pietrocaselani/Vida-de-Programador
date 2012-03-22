package com.pc.programmerslife.fragments;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;
import com.pc.programmerslife.Commic;
import com.pc.programmerslife.Manager;
import com.pc.programmerslife.R;
import com.pc.programmerslife.adapters.ItemGridAdapter;
import com.pc.programmerslife.adapters.ItemListAdapter;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

public class FavoritesFragment extends SherlockFragment implements OnItemClickListener {
	private static final String COMMICS_SIZE_TAG = "CommicsSize";
	
	private boolean isList;
	private ArrayList<Object> commics;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.favorites_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		commics = new ArrayList<Object>();
		
		View view = getView();
		ListView listView = (ListView) view.findViewById(R.id.favoritesFragment_listView);
		listView.setOnItemClickListener(this);
		listView.setVisibility(View.VISIBLE);
		listView.setAdapter(new ItemListAdapter(getSherlockActivity(), R.layout.item_list_layout, commics));
		
		GridView gridView = (GridView) view.findViewById(R.id.favoritesFragment_gridView);
		gridView.setOnItemClickListener(this);
		gridView.setVisibility(View.INVISIBLE);
		gridView.setAdapter(new ItemGridAdapter(getSherlockActivity(), R.layout.item_grid_layout, commics));
		
		isList = true;
		
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		ArrayList<Commic> dbCommics = Manager.getInstance(getSherlockActivity()).getFavorites();
		if (dbCommics != null) {
			commics.clear();
			commics.addAll(dbCommics);
			
			View view = getView();
			ListView listView = (ListView) view.findViewById(R.id.favoritesFragment_listView);
			GridView gridView = (GridView) view.findViewById(R.id.favoritesFragment_gridView);
			
			((ItemGridAdapter) gridView.getAdapter()).notifyDataSetChanged();
			((ItemListAdapter) listView.getAdapter()).notifyDataSetChanged();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(COMMICS_SIZE_TAG, commics.size());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		
	}
}