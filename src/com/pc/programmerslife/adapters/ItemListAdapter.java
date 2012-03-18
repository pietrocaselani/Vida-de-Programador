package com.pc.programmerslife.adapters;

import java.util.List;

import com.androidquery.AQuery;
import com.pc.framework.rss.Item;
import com.pc.programmerslife.Manager;
import com.pc.programmerslife.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemListAdapter extends ArrayAdapter<Item> {
	private LayoutInflater inflater;
	private AQuery aq;
	private Manager manager;

	public ItemListAdapter(Context context, int textViewResourceId, List<Item> objects) {
		super(context, textViewResourceId, objects);
		inflater = LayoutInflater.from(context);
		aq = new AQuery(context);
		manager = Manager.getInstance();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.item_list_layout, parent, false);
		
		Item item = getItem(position);
		
		String commicPath = manager.getCommicPath(item.getContent());
		
		String smallPath = manager.getSmallCommicPath(commicPath);
		
		aq.recycle(convertView).id(R.id.itemListLayout_imageView_preview).image(smallPath);
		
		TextView textViewTitle = (TextView) convertView.findViewById(R.id.itemListLayout_textView_title);
		textViewTitle.setText(item.getTitle());
		
		return convertView;
	}
}