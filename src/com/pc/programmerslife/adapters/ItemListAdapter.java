package com.pc.programmerslife.adapters;

import java.util.List;
import com.pc.programmerslife.Commic;
import com.pc.programmerslife.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemListAdapter extends ArrayAdapter<Object> {
	private static final int COMMIC_VIEW_TYPE = 0;
	private static final int LOAD_MORE_VIEW_TYPE = 1;
	
	private LayoutInflater inflater;
	private Typeface typefaceBold, typefaceRegular;

	public ItemListAdapter(Context context, int textViewResourceId, List<Object> objects) {
		super(context, textViewResourceId, objects);
		inflater = LayoutInflater.from(context);
		
		typefaceBold = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Black.ttf");
		typefaceRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
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
			CommicViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_list_layout, parent, false);
				
				holder = new CommicViewHolder();
				holder.textViewTitle = (TextView) convertView.findViewById(R.id.itemListLayout_textView_title);
				holder.textViewNumber = (TextView) convertView.findViewById(R.id.itemListLayout_textView_number);
				
				convertView.setTag(holder);
			} else
				holder = (CommicViewHolder) convertView.getTag();
			
			Commic commic = (Commic) getItem(position);
			
			holder.textViewTitle.setText(commic.getTitle());
			holder.textViewTitle.setTypeface((commic.isRead() == false) ? typefaceBold : typefaceRegular);
			
			holder.textViewNumber.setText(String.valueOf(commic.getNumber()));
		} else {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.load_more_commics_layout, parent, false);
				
				TextView textViewLoadMore = (TextView) convertView.findViewById(R.id.loadMoreCommics_textView);
				textViewLoadMore.setTypeface(typefaceBold);
			}
		}
		
		return convertView;
	}
	
	static class CommicViewHolder {
		TextView textViewTitle, textViewNumber;
	}
}