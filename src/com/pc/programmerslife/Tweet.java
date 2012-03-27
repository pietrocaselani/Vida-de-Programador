package com.pc.programmerslife;

import java.util.Date;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Tweet implements Parcelable {
	public static final String EXTRA_TWEET = "Tweet";
	public static final String EXTRA_TWEETS = "Tweets";
	
	private static final String ID_KEY = "ID";
	private static final String USER_NAME_KEY = "UserName";
	private static final String USER_PHOTO_LINK_KEY = "UserPhotoLink";
	private static final String TEXT_KEY = "Text";
	private static final String SOURCE_KEY = "Source";
	private static final String DATE_KEY = "Date";
	
	private String userName, userPhotoLink, text, source;
	private Date date;
	private int id;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhotoLink() {
		return userPhotoLink;
	}

	public void setUserPhotoLink(String userPhotoLink) {
		this.userPhotoLink = userPhotoLink;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Bundle data = new Bundle();
		
		data.putInt(ID_KEY, id);
		data.putString(SOURCE_KEY, source);
		data.putString(TEXT_KEY, text);
		data.putString(USER_NAME_KEY, userName);
		data.putString(USER_PHOTO_LINK_KEY, userPhotoLink);
		data.putLong(DATE_KEY, date != null ? date.getTime() : 0);
		
		dest.writeBundle(data);
	}
	
	public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {

		@Override
		public Tweet createFromParcel(Parcel source) {
			Tweet tweet = new Tweet();
			
			Bundle data = source.readBundle();
			
			tweet.id = data.getInt(ID_KEY);
			tweet.source = data.getString(SOURCE_KEY);
			tweet.text = data.getString(TEXT_KEY);
			tweet.userName = data.getString(USER_NAME_KEY);
			tweet.userPhotoLink = data.getString(USER_PHOTO_LINK_KEY);
			tweet.date = new Date(data.getLong(DATE_KEY));
			
			return tweet;
		}

		@Override
		public Tweet[] newArray(int size) {
			return new Tweet[size];
		}
	};
}