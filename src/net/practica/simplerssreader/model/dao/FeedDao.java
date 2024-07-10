package net.practica.simplerssreader.model.dao;

import java.util.ArrayList;

import net.practica.simplerssreader.dbo.DBOpenHelperFeed;
import net.practica.simplerssreader.model.Feed;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class FeedDao {
	DBOpenHelperFeed helper;
	SQLiteDatabase db;
	Feed feed;

	private final String DB_NAME = "feeds.sqlite";
	private final String FEEDS_TABLE = "Feeds";

	public FeedDao(Context context) {
		helper = new DBOpenHelperFeed(context, DB_NAME, 1);
	}

	public boolean insertarFeed(Feed feed) {
		ContentValues contentFeed;
		long res = -1;

		try {
			//url = new URL(feed);
			db = helper.getWritableDatabase();
			contentFeed = new ContentValues(); 
			contentFeed.put("feed", feed.getFeed());
			res = db.insert(FEEDS_TABLE, null, contentFeed);
		} catch (SQLiteException e) {
			Log.e("SimpleRssReader", "BD error ...");
		}

		return (res != -1)? true : false;
	}

	public boolean eliminarFeed(Feed feed) {
		int res = 0;

		try {
			db = helper.getWritableDatabase();
			res = db.delete(FEEDS_TABLE, "feed = '" + feed.getFeed() + "'", null);
		} catch (SQLiteException e) {
			Log.e("SimpleRssReader", "BD error ...");
		}

		return (res != 0)? true : false;
	}

	public ArrayList<String> listarFeeds() {
		ArrayList<String> listaFeeds = new ArrayList<String>();
		Cursor c;

		try {
			db = helper.getReadableDatabase();
			c = db.query(FEEDS_TABLE, 					// tabla
					new String[] { "feed" }, 			// columna
					null,								// where
					null, 								// where args
					null,								// group by 
					null, 								// having
					null);								// order by

			if (c.moveToFirst()) {
				do {
					String feed = c.getString(0);
					listaFeeds.add(feed);
				} while (c.moveToNext());
			}
		} catch (SQLiteException e) {
			Log.e("SimpleRssReader", "BD error ...");
		}

		return listaFeeds;
	}
}
