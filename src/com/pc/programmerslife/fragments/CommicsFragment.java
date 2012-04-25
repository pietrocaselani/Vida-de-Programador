package com.pc.programmerslife.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.pc.programmerslife.Commic;
import com.pc.programmerslife.CommicManager;
import com.pc.programmerslife.CommicManager.CommicManagerListener;
import com.pc.programmerslife.R;
import com.pc.programmerslife.activities.CommicActivity;
import com.pc.programmerslife.adapters.ItemGridAdapter;
import com.pc.programmerslife.adapters.ItemListAdapter;

public class CommicsFragment extends SherlockFragment implements OnItemClickListener, CommicManagerListener {
	private static final String COMMICS_SIZE_TAG = "CommicsSize";
	private static final int QUANTITY = 10;
	private static final int LOAD_MORE_TAG = 1;
	
	private boolean isList;
	private ArrayList<Object> commics;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.commics_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		commics = new ArrayList<Object>();
		
		View view = getView();
		
		ListView listView = (ListView) view.findViewById(R.id.commicsFragment_listView);
		listView.setOnItemClickListener(this);
		listView.setVisibility(View.VISIBLE);
		listView.setAdapter(new ItemListAdapter(getSherlockActivity(), R.layout.item_list_layout, commics));
		
		GridView gridView = (GridView) view.findViewById(R.id.commicsFragment_gridView);
		gridView.setOnItemClickListener(this);
		gridView.setVisibility(View.INVISIBLE);
		gridView.setAdapter(new ItemGridAdapter(getSherlockActivity(), R.layout.item_grid_layout, commics));
		
		isList = true;
		
		setHasOptionsMenu(true);
		
		if (savedInstanceState == null) {
			update();
			reloadCommics();
		} else {
			ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.commicsFragment_progressBar);
			progressBar.setVisibility(View.INVISIBLE);
			int q = savedInstanceState.getInt(COMMICS_SIZE_TAG);
			reloadCommics(0, q);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putInt(COMMICS_SIZE_TAG, commics.size());
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.commics_fragment_menu, menu);
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
		Object obj = commics.get(position);
		
		if (obj instanceof Commic) {
			final Commic commic = (Commic) obj;
			
			Intent commicActivityIntent = new Intent(getSherlockActivity(), CommicActivity.class);
			commicActivityIntent.putExtra(Commic.EXTRA_COMMIC, commic);
			startActivity(commicActivityIntent);
			commic.setRead(true);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					if (CommicManager.getInstance().updateCommic(commic) == true)
						notifyAdapters();
				}
			}, 1000);
		} else
			loadMore();
	}
	
	private void update() {
		ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.commicsFragment_progressBar);
		progressBar.setVisibility(View.VISIBLE);
		
		CommicManager manager = CommicManager.getInstance();
		manager.setLink("http://feeds.feedburner.com/VidaDeProgramador?format=xml");
		manager.update(this);
	}
	
	private int quantity() {
		return (commics.size() < QUANTITY) ? QUANTITY : commics.size();
	}
	
	private void reloadCommics() {
		reloadCommics(0, quantity());
	}
	
	private void reloadCommics(int s, int q) {
		ArrayList<Commic> dbCommics = CommicManager.getInstance().getCommics(s, q);
		if (dbCommics != null) {
			commics.clear();
			
			commics.addAll(dbCommics);
			
			reloadViews();
		}
	}
	
	private void loadMore() {
		int s = commics.size() - 1;
		ArrayList<Commic> dbCommics = CommicManager.getInstance().getCommics(s, QUANTITY);
		if (dbCommics != null) {
			commics.remove(s);
			commics.addAll(dbCommics);
			
			reloadViews();
		}
	}
	
	private void reloadViews() {
		int count = CommicManager.getInstance().getCommicsCount();
		if (count > commics.size())
			commics.add(LOAD_MORE_TAG);
		
		notifyAdapters();
	}
	
	private void notifyAdapters() {
		View view = getView();
		
		ListView listView = (ListView) view.findViewById(R.id.commicsFragment_listView);
		GridView gridView = (GridView) view.findViewById(R.id.commicsFragment_gridView);
		
		((ItemListAdapter) listView.getAdapter()).notifyDataSetChanged();
		((ItemGridAdapter) gridView.getAdapter()).notifyDataSetChanged();
	}

	@Override
	public void onFinishUpdate(CommicManager manager) {
		if (isResumed() == true) {
			ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.commicsFragment_progressBar);
			progressBar.setVisibility(View.INVISIBLE);
			reloadCommics(0, QUANTITY);
		}
	}

	@Override
	public void onFailUpdate(Exception e, CommicManager manager) {
		if (isResumed() == true) {
			ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.commicsFragment_progressBar);
			progressBar.setVisibility(View.INVISIBLE);
			Toast.makeText(getSherlockActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
}