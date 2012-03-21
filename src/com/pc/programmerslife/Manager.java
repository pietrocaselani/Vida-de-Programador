package com.pc.programmerslife;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pc.framework.rss.Item;
import com.pc.framework.rss.Manager.ManagerListener;

public class Manager implements ManagerListener {
	private static final String SMALL_COMMIC_LINK = "-150x150.png";
	private static final String MEDIUM_COMMIC_LINK = "-300x300.png";
	
	private static Manager singleton;
	
	private com.pc.framework.rss.Manager rssManager;
	private ManagerListener listener;
	private DatabaseHelper databaseHelper;
	
	public static Manager getInstance(Context context) {
		if (singleton == null)
			singleton = new Manager(context);
		return singleton;
	}
	
	public Manager(Context context) {
		rssManager = new com.pc.framework.rss.Manager();
		databaseHelper = new DatabaseHelper(context);
	}
	
	public void setLink(String link) {
		rssManager.setLink(link);
	}
	
	public String getLink() {
		return rssManager.getLink();
	}
	
	public void update(ManagerListener listener) {
		this.listener = listener;
		rssManager.update(this, false);
	}
	
	public boolean cancel() {
		return rssManager.cancel();
	}
	
	public String getSmallCommicPath(String commicPath) {
		int index = commicPath.indexOf(".png");
		
		String result = commicPath.substring(0, index);
		
		return result + SMALL_COMMIC_LINK;
	}
	
	public String getMediumCommicPath(String commicPath) {
		int index = commicPath.indexOf(".png");
		
		String result = commicPath.substring(0, index);
		
		return result + MEDIUM_COMMIC_LINK;
	}
	
	public ArrayList<Commic> getCommics(int starting, int quantity) {
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		
		String selectSQL = "SELECT title, description, content, link, pubDate, isFavorite FROM commics DESC LIMIT ?, ?";
		
		try {
			Cursor c = db.rawQuery(selectSQL, new String[] {
					String.valueOf(starting),
					String.valueOf(quantity)
				});
				
				if (c != null && c.moveToFirst() == true) {
					ArrayList<Commic> commics = new ArrayList<Commic>(c.getCount());
					Commic commic;
					do {
						commic = new Commic();
						
						commic.setTitle(c.getString(0));
						commic.setDescription(c.getString(1));
						commic.setContent(c.getString(2));
						commic.setLink(c.getString(3));
						commic.setDate(new Date(c.getLong(4)));
						commic.setFavorite(c.getInt(5) == 1);
						
						commics.add(commic);
						
					} while (c.moveToNext() == true);
					
					c.close();
					
					return commics;
				}
		} catch (SQLException e) {
			Log.e("VDP-MANAGER", e.getMessage());
		}
		
		return null;
	}
	
	public int getCommicsCount() {
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		
		String selectSQL = "SELECT count(title) FROM commics";
		
		Cursor c = db.rawQuery(selectSQL, null);
		
		int count = (c == null || c.moveToFirst() == false) ? 0 : c.getInt(0);
		
		return count;
	}
	
	private void save(ArrayList<Item> items) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		
		Exception e = null;
		long time;
		
		String insertSQL = "INSERT OR REPLACE INTO commics (guid, title, description, content, link, pubDate) VALUES (?, ?, ?, ?, ?, ?)";
		
		db.execSQL("BEGIN");
		
		for (Item item : items) {
			time = item.getDate() == null ? 0 : item.getDate().getTime();
			try {
				db.execSQL(insertSQL, new Object[] {
					item.getGuid(),
					item.getTitle(),
					item.getDescription(),
					item.getContent(),
					item.getLink(),
					time
				});
			} catch (SQLException insertException) {
				e = insertException;
			}
		}
		
		db.execSQL("COMMIT");
		
		if (e != null)
			Log.e("VDP-MANAGER", e.getMessage());
	}

	@Override
	public void onManagerFinishUpdate(ArrayList<Item> items, com.pc.framework.rss.Manager manager) {
		ArrayList<Item> commics = new ArrayList<Item>(items.size());
		
		for (Item item : items)
			if (item.getCategories().contains("Tirinhas") == true)
				commics.add(item);
		
		save(commics);
		if (listener != null)
			listener.onFinishUpdate(this);
	}

	@Override
	public void onManagerFailUpdate(Exception e, com.pc.framework.rss.Manager manager) {
		if (listener != null)
			listener.onFailUpdate(e, this);
	}
	
	public interface ManagerListener {
		public void onFinishUpdate(Manager manager);
		public void onFailUpdate(Exception e, Manager manager);
	}
}