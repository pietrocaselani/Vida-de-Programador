package com.pc.programmerslife;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Tweet implements Parcelable {
	public static final String EXTRA_TWEET = "Tweet";
	
	private static final String USER_NAME_KEY = "UserName";
	private static final String USER_PHOTO_LINK_KEY = "UserPhotoLink";
	private static final String TEXT_KEY = "Text";
	private static final String SOURCE_KEY = "Source";
	
	private String userName, userPhotoLink, text, source;

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Bundle data = new Bundle();
		
		data.putString(SOURCE_KEY, source);
		data.putString(TEXT_KEY, text);
		data.putString(USER_NAME_KEY, userName);
		data.putString(USER_PHOTO_LINK_KEY, userPhotoLink);
		
		dest.writeBundle(data);
	}
	
	public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {

		@Override
		public Tweet createFromParcel(Parcel source) {
			Tweet tweet = new Tweet();
			
			Bundle data = source.readBundle();
			tweet.source = data.getString(SOURCE_KEY);
			tweet.text = data.getString(TEXT_KEY);
			tweet.userName = data.getString(USER_NAME_KEY);
			tweet.userPhotoLink = data.getString(USER_PHOTO_LINK_KEY);
			
			return tweet;
		}

		@Override
		public Tweet[] newArray(int size) {
			return new Tweet[size];
		}
	};
}