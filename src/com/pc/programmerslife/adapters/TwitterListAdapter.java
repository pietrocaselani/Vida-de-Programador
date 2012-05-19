package com.pc.programmerslife.adapters;

import java.util.List;

import com.androidquery.AQuery;
import com.pc.programmerslife.R;
import com.pc.programmerslife.Tweet;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TwitterListAdapter extends ArrayAdapter<Tweet> {
	private LayoutInflater inflater;
	private AQuery aq;
	private Typeface typefaceBold, typefaceRegular;
	
	public TwitterListAdapter(Context context, int textViewResourceId, List<Tweet> objects) {
		super(context, textViewResourceId, objects);
		inflater = LayoutInflater.from(context);
		aq = new AQuery(context);
		
		typefaceBold = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
		typefaceRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TweetViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.tweet_item_layout, parent, false);
			
			holder = new TweetViewHolder();
			holder.textViewText = (TextView) convertView.findViewById(R.id.tweetItemLayout_textView_text);
			holder.textViewUser = (TextView) convertView.findViewById(R.id.tweetItemLayout_textView_user);
			
			holder.textViewText.setTypeface(typefaceRegular);
			holder.textViewUser.setTypeface(typefaceBold);
			
			convertView.setTag(holder);
		} else
			holder = (TweetViewHolder) convertView.getTag();
		
		Tweet tweet = getItem(position);
		
		aq.recycle(convertView).id(R.id.tweetItemLayout_imageView_user).image(tweet.getUserPhotoLink());
		
		holder.textViewUser.setText(tweet.getUserName());
		holder.textViewText.setText(Html.fromHtml(tweet.getText()));
		
		return convertView;
	}
	
	static class TweetViewHolder {
		TextView textViewUser, textViewText;
	}
}