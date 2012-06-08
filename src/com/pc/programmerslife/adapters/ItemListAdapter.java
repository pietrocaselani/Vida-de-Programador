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

public class ItemListAdapter extends ArrayAdapter<Commic> {
	private LayoutInflater inflater;
	private Typeface typefaceBold, typefaceRegular;

	public ItemListAdapter(Context context, int textViewResourceId, List<Commic> objects) {
		super(context, textViewResourceId, objects);
		inflater = LayoutInflater.from(context);
		
		typefaceBold = Typeface.createFromAsset(context.getAssets(), "Roboto-Black.ttf");
		typefaceRegular = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommicViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_list_layout, parent, false);
			
			holder = new CommicViewHolder();
			holder.textViewTitle = (TextView) convertView.findViewById(R.id.itemListLayout_textView_title);
			holder.textViewNumber = (TextView) convertView.findViewById(R.id.itemListLayout_textView_number);
			
			convertView.setTag(holder);
		} else
			holder = (CommicViewHolder) convertView.getTag();
		
		Commic commic = getItem(position);
		
		holder.textViewTitle.setText(commic.getTitle());
		holder.textViewTitle.setTypeface((commic.isRead() == false) ? typefaceBold : typefaceRegular);
		
		int commicNumber = commic.getNumber();
		if (commicNumber == 0)
			holder.textViewNumber.setVisibility(View.INVISIBLE);
		else {
			holder.textViewNumber.setVisibility(View.VISIBLE);
			holder.textViewNumber.setText(getContext().getString(R.string.commiNumberText, commicNumber));
		}
		
		return convertView;
	}
	
	static class CommicViewHolder {
		TextView textViewTitle, textViewNumber;
	}
}