package com.pc.programmerslife.fragments;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.pc.programmerslife.Commic;
import com.pc.programmerslife.CommicManager;
import com.pc.programmerslife.R;
import com.pc.programmerslife.activities.CommicActivity;
import com.pc.programmerslife.adapters.ItemGridAdapter;
import com.pc.programmerslife.adapters.ItemListAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

public class FavoritesFragment extends SherlockFragment implements OnItemClickListener {
	private boolean isList;
	private ArrayList<Object> commics;
	private ItemListAdapter listAdapter;
	private ItemGridAdapter gridAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.favorites_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		reload();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		commics = new ArrayList<Object>();
		
		listAdapter = new ItemListAdapter(getSherlockActivity(), R.layout.item_list_layout, commics);
		gridAdapter = new ItemGridAdapter(getSherlockActivity(), R.layout.item_grid_layout, commics);
		
		View view = getView();
		ListView listView = (ListView) view.findViewById(R.id.favoritesFragment_listView);
		listView.setOnItemClickListener(this);
		listView.setVisibility(View.VISIBLE);
		listView.setAdapter(listAdapter);
		
		GridView gridView = (GridView) view.findViewById(R.id.favoritesFragment_gridView);
		gridView.setOnItemClickListener(this);
		gridView.setVisibility(View.INVISIBLE);
		gridView.setAdapter(gridAdapter);
		
		isList = true;
		
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		reload();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.favorites_fragment_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.favoritesFragmentMenu_viewMode) {
			View view = getView();
			ListView listView = (ListView) view.findViewById(R.id.favoritesFragment_listView);
			GridView gridView = (GridView) view.findViewById(R.id.favoritesFragment_gridView);
			
			if (isList == true) {
				listView.setVisibility(View.INVISIBLE);
				gridView.setVisibility(View.VISIBLE);
				item.setIcon(R.drawable.action_bar_show_as_list);
			} else {
				listView.setVisibility(View.VISIBLE);
				gridView.setVisibility(View.INVISIBLE);
				item.setIcon(R.drawable.action_bar_show_as_grid);
			}
			
			isList = !isList;
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Object object = commics.get(position);
		
		if (object instanceof Commic) {
			Commic commic = (Commic) object;
			
			startActivity(new Intent(getSherlockActivity(), CommicActivity.class).putExtra(Commic.EXTRA_COMMIC, commic));
		}
	}
	
	public void reload() {
		ArrayList<Commic> dbCommics = CommicManager.getInstance().getFavorites();
		
		if (commics == null)
			commics = new ArrayList<Object>();
		
		commics.clear();
		
		if (dbCommics != null)
			commics.addAll(dbCommics);
		
		if (listAdapter != null)
			listAdapter.notifyDataSetChanged();
		if (gridAdapter != null)
			gridAdapter.notifyDataSetChanged();
		
//		View view = getView();
//		if (view != null) {
//			ListView listView = (ListView) view.findViewById(R.id.favoritesFragment_listView);
//			GridView gridView = (GridView) view.findViewById(R.id.favoritesFragment_gridView);
//			
//			if (gridView.getAdapter() instanceof ItemGridAdapter)
//				((ItemGridAdapter) gridView.getAdapter()).notifyDataSetChanged();
//			
//			if (listView.getAdapter() instanceof ItemListAdapter)
//				((ItemListAdapter) listView.getAdapter()).notifyDataSetChanged();
//		}
	}
}