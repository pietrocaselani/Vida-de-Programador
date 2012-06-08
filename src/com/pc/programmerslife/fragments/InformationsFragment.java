package com.pc.programmerslife.fragments;

import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.pc.programmerslife.R;
import com.pc.programmerslife.adapters.InfoAdapter;
import com.pc.programmerslife.fragments.dialogs.AppDialogFragment;

public class InformationsFragment extends SherlockListFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setListAdapter(new InfoAdapter(getSherlockActivity(), R.layout.info_item_layout, getResources().getStringArray(R.array.infos)));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.informations_fragment, container, false);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if (position == 1) {
			showAppDialog();
		}
	}
	
	private void showAppDialog() {
		new AppDialogFragment().show(getFragmentManager(), AppDialogFragment.APP_DIALOG_FRAGMENT_TAG);
	}
}