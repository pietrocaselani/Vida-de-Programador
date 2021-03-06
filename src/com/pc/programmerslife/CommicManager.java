package com.pc.programmerslife;

import java.util.ArrayList;

import android.content.Context;

import com.pc.framework.rss.Item;
import com.pc.framework.rss.dom.Manager;
import com.pc.framework.rss.dom.Manager.ManagerListener;

public class CommicManager implements ManagerListener {
	private static final String SMALL_COMMIC_LINK = "-150x150.png";
	private static final String MEDIUM_COMMIC_LINK = "-300x300.png";
	
	private static final CommicManager singleton = new CommicManager();
	
	private Manager rssManager;
	private CommicManagerListener listener;
	private DatabaseHelper databaseHelper;
	
	public static synchronized CommicManager getInstance() {
		return singleton;
	}
	
	public CommicManager() {
		rssManager = Manager.getInstance();
	}
	
	public void startDatabase(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}
	
	public void setLink(String link) {
		rssManager.setLink(link);
	}
	
	public String getLink() {
		return rssManager.getLink();
	}
	
	public void update(CommicManagerListener listener) {
		this.listener = listener;
		rssManager.update(this, false);
	}
	
	public boolean cancel() {
		return rssManager.cancel();
	}
	
	public void setListener(CommicManagerListener listener) {
		this.listener = listener;
	}
	
	public CommicManagerListener getListener() {
		return listener;
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
	
	public boolean updateCommicReaded(Commic commic) {
		return databaseHelper.updateCommicReaded(commic);
	}
	
	public boolean updateCommicFavorite(Commic commic) {
		return databaseHelper.updateCommicFavorite(commic);
	}
	
	public ArrayList<Commic> getCommics(int starting, int quantity) {
		return databaseHelper.getCommics(starting, quantity);
	}
	
	public int getCommicsCount() {
		return databaseHelper.getCommicsCount();
	}
	
	public ArrayList<Commic> getFavorites() {
		return databaseHelper.getFavorites();
	}

	@Override
	public void onManagerFinishUpdate(ArrayList<Item> items, Manager manager) {
		ArrayList<Item> commics = new ArrayList<Item>(items.size());
		
		for (Item item : items)
			if (item.getCategories().contains("Tirinhas") == true)
				commics.add(item);
		
		databaseHelper.saveCommics(commics);
		
		if (listener != null)
			listener.onFinishUpdate(this);
	}

	@Override
	public void onManagerFailUpdate(Exception e, Manager manager) {
		if (listener != null)
			listener.onFailUpdate(e, this);
	}
	
	public interface CommicManagerListener {
		public void onFinishUpdate(CommicManager manager);
		public void onFailUpdate(Exception e, CommicManager manager);
	}
}