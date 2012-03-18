package com.pc.programmerslife.adapters;

import java.util.List;

import com.pc.framework.rss.Item;
import com.pc.programmerslife.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemGridAdapter extends ArrayAdapter<Item> {
	private LayoutInflater inflater;

	public ItemGridAdapter(Context context, int textViewResourceId, List<Item> objects) {
		super(context, textViewResourceId, objects);
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.item_grid_layout, parent, false);
		
		Item item = getItem(position);
		
		TextView textViewTitle = (TextView) convertView.findViewById(R.id.itemGridLayout_textView_title);
		textViewTitle.setTag(item.getTitle());
		
		return convertView;
	}
}