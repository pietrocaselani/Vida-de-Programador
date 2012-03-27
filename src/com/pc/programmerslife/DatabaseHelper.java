package com.pc.programmerslife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.pc.framework.Utilities;
import com.pc.framework.rss.Item;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "VDPDatabase";
	private static final int DATABASE_VERSION = 1;
	
	private Context context;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql;
		try {
			sql = Utilities.getStringFromInputStream(context.getAssets().open("create_commics_table"));
			db.execSQL(sql);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	public ArrayList<Commic> getCommics(int starting, int quantity) {
		SQLiteDatabase db = getReadableDatabase();
		
		String selectSQL = "SELECT title, description, content, link, pubDate, isFavorite, isUnread FROM commics DESC LIMIT ?, ?";
		
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
					commic.setUnread(c.getInt(6) == 1);
					
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
	
	public ArrayList<Commic> getFavorites() {
		SQLiteDatabase db = getReadableDatabase();
		
		String selectSQL = "SELECT title, description, content, link, pubDate FROM commics WHERE isFavorite == 1";
		
		try {
			Cursor c = db.rawQuery(selectSQL, null);
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
					commic.setFavorite(true);
					commic.setUnread(false);
					
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
		SQLiteDatabase db = getReadableDatabase();
		
		String selectSQL = "SELECT count(title) FROM commics";
		
		Cursor c = db.rawQuery(selectSQL, null);
		
		int count = (c == null || c.moveToFirst() == false) ? 0 : c.getInt(0);
		
		return count;
	}
	
	public boolean updateCommic(Commic commic) {
		String updateSQL = "UPDATE commics SET isUnread = ?, isFavorite = ? WHERE title = ?";
		
		SQLiteDatabase db = getWritableDatabase();
		Exception exception = null;
		
		int favorite = (commic.isFavorite() == true) ? 1 : 0;
		int unread = (commic.isUnread() == true) ? 1 : 0;
		
		try {
			db.execSQL(updateSQL, new Object[] {
					favorite,
					unread,
					commic.getTitle()
			});
		} catch (SQLException e) {
			exception = e;
		}
		
		return exception == null;
	}
	
	public void save(ArrayList<Item> items) {
		SQLiteDatabase db = getWritableDatabase();
		
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
}