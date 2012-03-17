package com.pc.programmerslife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.pc.programmerslife.R;

public class CommicsFragment extends SherlockFragment implements OnItemClickListener {
	private boolean isList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.commics_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		isList = true;
		
		setHasOptionsMenu(true);
		
		View view = getView();
		
		ListView listView = (ListView) view.findViewById(R.id.commicsFragment_listView);
		listView.setOnItemClickListener(this);
		
		GridView gridView = (GridView) view.findViewById(R.id.commicsFragment_gridView);
		gridView.setOnItemClickListener(this);
		gridView.setVisibility(View.INVISIBLE);
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
}