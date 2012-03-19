package com.pc.programmerslife;

import android.os.Parcel;
import android.os.Parcelable;

import com.pc.framework.rss.Item;

public class Commic extends Item implements Parcelable {
	private boolean isFavorite;
	
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	
	public boolean isFavorite() {
		return isFavorite;
	}
	
	@Override
	public int describeContents() {
		return super.describeContents();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		
		dest.writeInt(isFavorite == true ? 1 : 0);
	}
	
	Parcelable.Creator<Commic> CREATOR = new Creator<Commic>() {
		
		@Override
		public Commic[] newArray(int size) {
			return new Commic[size];
		}
		
		@Override
		public Commic createFromParcel(Parcel source) {
			Commic commic = new Commic();
			
			commic.setFavorite(source.readInt() == 1);
			
			return commic;
		}
	};
}