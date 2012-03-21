package com.pc.programmerslife;

import android.os.Parcel;
import android.os.Parcelable;

import com.pc.framework.rss.Item;

public class Commic extends Item implements Parcelable {
	public static final String EXTRA_COMMIC = "Extra_Commic";
	
	private boolean isFavorite;
	private String path;
	
	public Commic(Parcel source) {
		super(source);
		
		isFavorite = source.readInt() == 1;
		path = source.readString();
	}
	
	public Commic() {
		super();
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
		
		path = content.substring(start, end + start);
	}
	
	public String getPath() {
		return path;
	}
	
	@Override
	public int describeContents() {
//		return super.describeContents();
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(isFavorite == true ? 1 : 0);
		dest.writeString(path);
		
		super.writeToParcel(dest, flags);
	}
	
	Parcelable.Creator<Commic> CREATOR = new Creator<Commic>() {
		
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