package com.pc.programmerslife;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.pc.framework.rss.Item;

public class Commic extends Item {
	public static final String EXTRA_COMMIC = "Extra_Commic";
	
	private static final String FAVORITE_KEY = "Favorite";
	private static final String PATH_KEY = "Path";
	private static final String NUMBER_KEY = "Number";
	
	private boolean isFavorite;
	private String path;
	private Integer number;
	
	public Commic(Parcel source) {
		super(source);
		if (source != null) {
			Bundle data = source.readBundle();
			
			this.isFavorite = data.getBoolean(FAVORITE_KEY);
			this.path = data.getString(PATH_KEY);
			this.number = data.getInt(NUMBER_KEY);
		}
	}
	
	public Commic() {
	}
	
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	
	public boolean isFavorite() {
		return isFavorite;
	}
	
	@Override
	public void setContent(String content) {
		super.setContent(content);
		
		int start = content.indexOf("http://vidadeprogramador.com.br/wp-content/uploads/");
		
		String commicPath = content.substring(start);
		int end = commicPath.indexOf("\"");
		
		this.path = content.substring(start, end + start);
		
		int s = path.lastIndexOf("a") + 1;
		int e = path.indexOf(".png");
		String numberString = path.substring(s, e);
		
		try {
			this.number = Integer.parseInt(numberString);
		} catch (NumberFormatException numberException) {
		}
	}
	
	public String getPath() {
		return path;
	}
	
	public Integer getNumber() {
		return number;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		
		Bundle data = new Bundle();
		data.putString(PATH_KEY, path);
		data.putBoolean(FAVORITE_KEY, isFavorite);
		data.putInt(NUMBER_KEY, number);
		
		dest.writeBundle(data);
	}
	
	public static final Parcelable.Creator<Commic> CREATOR = new Creator<Commic>() {
		
		@Override
		public Commic[] newArray(int size) {
			return new Commic[size];
		}
		
		@Override
		public Commic createFromParcel(Parcel source) {
			return new Commic(source);
		}
	};
}