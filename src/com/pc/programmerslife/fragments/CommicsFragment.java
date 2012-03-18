package com.pc.programmerslife.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.pc.framework.rss.Item;
import com.pc.framework.rss.Manager;
import com.pc.framework.rss.Manager.ManagerListener;
import com.pc.programmerslife.R;
import com.pc.programmerslife.adapters.ItemGridAdapter;
import com.pc.programmerslife.adapters.ItemListAdapter;

public class CommicsFragment extends SherlockFragment implements OnItemClickListener, ManagerListener {
	private boolean isList;
	private ArrayList<Item> items;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.commics_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		items = new ArrayList<Item>();
		
		Manager manager = Manager.getInstance(getSherlockActivity());
		manager.setLink("http://feeds.feedburner.com/VidaDeProgramador?format=xml");
		manager.update(this, false);
		
		isList = true;
		
		setHasOptionsMenu(true);
		
		View view = getView();
		
		ListView listView = (ListView) view.findViewById(R.id.commicsFragment_listView);
		listView.setOnItemClickListener(this);
		listView.setVisibility(View.VISIBLE);
		listView.setAdapter(new ItemListAdapter(getSherlockActivity(), R.layout.item_list_layout, items));
		
		GridView gridView = (GridView) view.findViewById(R.id.commicsFragment_gridView);
		gridView.setOnItemClickListener(this);
		gridView.setVisibility(View.INVISIBLE);
		gridView.setAdapter(new ItemGridAdapter(getSherlockActivity(), R.layout.item_grid_layout, items));
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.commics_fragment_ment, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.commicsFragmentMenu_viewMode) {
			View view = getView();
			ListView listView = (ListView) view.findViewById(R.id.commicsFragment_listView);
			GridView gridView = (GridView) view.findViewById(R.id.commicsFragment_gridView);
			if (isList == true) {
				listView.setVisibility(View.INVISIBLE);
				gridView.setVisibility(View.VISIBLE);
				item.setIcon(R.drawable.ic_menu_list);
			} else {
				listView.setVisibility(View.VISIBLE);
				gridView.setVisibility(View.INVISIBLE);
				item.setIcon(R.drawable.ic_menu_grid);
			}
			
			isList = !isList;
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}

	@Override
	public void onManagerFinishUpdate(ArrayList<Item> rssItems, Manager manager) {
		items.clear();
		
		for (Item item : rssItems)
			if (item.getCategories().contains("Tirinhas"))
				items.add(item);
		
		View v = getView();
		ListView listView = (ListView) v.findViewById(R.id.commicsFragment_listView);
		GridView gridView = (GridView) v.findViewById(R.id.commicsFragment_gridView);
		
		((ItemListAdapter) listView.getAdapter()).notifyDataSetChanged();
		((ItemGridAdapter) gridView.getAdapter()).notifyDataSetChanged();
	}

	@Override
	public void onManagerFailUpdate(Exception e, Manager manager) {
		Toast.makeText(getSherlockActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
	}
}