package com.pc.programmerslife.adapters;

import java.util.List;

import com.androidquery.AQuery;
import com.pc.programmerslife.R;
import com.pc.programmerslife.Tweet;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TwitterListAdapter extends ArrayAdapter<Tweet> {
	private LayoutInflater inflater;
	private AQuery aq;
	
	public TwitterListAdapter(Context context, int textViewResourceId, List<Tweet> objects) {
		super(context, textViewResourceId, objects);
		inflater = LayoutInflater.from(context);
		aq = new AQuery(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.tweet_item_layout, parent, false);
		
		Tweet tweet = getItem(position);
		
		aq.recycle(convertView).id(R.id.tweetItemLayout_imageView_user).image(tweet.getUserPhotoLink());
		
		TextView textViewUser = (TextView) convertView.findViewById(R.id.tweetItemLayout_textView_user);
		TextView textViewText = (TextView) convertView.findViewById(R.id.tweetItemLayout_textView_text);
		
		textViewUser.setText(tweet.getUserName());
		textViewText.setText(Html.fromHtml(tweet.getText()));
		
		return convertView;
	}
}