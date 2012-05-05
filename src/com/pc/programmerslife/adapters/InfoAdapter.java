package com.pc.programmerslife.adapters;

import java.util.List;

import com.pc.programmerslife.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class InfoAdapter extends ArrayAdapter<String> {
	private LayoutInflater inflater;

	public InfoAdapter(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
		
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.info_item_layout, parent, false);
		
		TextView textView = (TextView) convertView.findViewById(R.id.infoItemLayout_textView);
		textView.setText(getItem(position));
		
		return convertView;
	}
}
