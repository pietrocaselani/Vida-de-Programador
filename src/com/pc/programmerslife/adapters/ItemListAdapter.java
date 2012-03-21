package com.pc.programmerslife.adapters;

import java.util.List;
import com.pc.programmerslife.Commic;
import com.pc.programmerslife.Manager;
import com.pc.programmerslife.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemListAdapter extends ArrayAdapter<Object> {
	private static final int COMMIC_VIEW_TYPE = 0;
	private static final int LOAD_MORE_VIEW_TYPE = 1;
	
	private LayoutInflater inflater;
	private Manager manager;

	public ItemListAdapter(Context context, int textViewResourceId, List<Object> objects) {
		super(context, textViewResourceId, objects);
		inflater = LayoutInflater.from(context);
		manager = Manager.getInstance(context);
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	@Override
	public int getItemViewType(int position) {
		return (getItem(position) instanceof Integer) ? LOAD_MORE_VIEW_TYPE : COMMIC_VIEW_TYPE;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getItemViewType(position) == COMMIC_VIEW_TYPE) {
			if (convertView == null)
				convertView = inflater.inflate(R.layout.item_list_layout, parent, false);
			
			Commic commic = (Commic) getItem(position);
			
			String commicNumber = manager.getCommicNumber(commic.getPath());
			
			TextView textViewNumber = (TextView) convertView.findViewById(R.id.itemListLayout_textView_number);
			textViewNumber.setText(commicNumber);
			
			TextView textViewTitle = (TextView) convertView.findViewById(R.id.itemListLayout_textView_title);
			textViewTitle.setText(commic.getTitle());
		} else {
			if (convertView == null)
				convertView = inflater.inflate(R.layout.load_more_commics_layout, parent, false);
		}
		
		return convertView;
	}
}