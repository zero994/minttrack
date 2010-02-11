package com.ponyinc.minttrack;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.CATEGORY_NAME;
import static com.ponyinc.minttrack.Constants.CATEGORY_TBLNAM;
import static com.ponyinc.minttrack.Constants.CATEGORY_TOTAL;
import static com.ponyinc.minttrack.Constants.CATEGORY_TYPE;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Categories {
	private MintData MintLink;

	Categories(MintData mintdata) {
		MintLink = mintdata;
	}

	public Cursor getCategorys() {
		final String[] FROM = { _ID, CATEGORY_NAME, CATEGORY_TOTAL,
				CATEGORY_TYPE, };
		final String ORDER_BY = CATEGORY_NAME + " DESC";
		SQLiteDatabase db = MintLink.getReadableDatabase();
		Cursor cursor = db.query(CATEGORY_TBLNAM, FROM, null, null, null, null,
				ORDER_BY);
		return cursor;
	}

	public Cursor getCategorys(MintData MintLink) {
		final String[] FROM = { _ID, CATEGORY_NAME, CATEGORY_TOTAL,
				CATEGORY_TYPE, };
		final String ORDER_BY = CATEGORY_NAME + " DESC";
		SQLiteDatabase db = MintLink.getReadableDatabase();
		Cursor cursor = db.query(CATEGORY_TBLNAM, FROM, null, null, null, null,
				ORDER_BY);
		return cursor;
	}

	public String getCategory(int intID) {
		final String[] FROM = { _ID, CATEGORY_NAME, CATEGORY_TOTAL,
				CATEGORY_TYPE, };
		final String ORDER_BY = CATEGORY_NAME + " DESC";
		final String SELECTION = "_ID=" + intID;

		SQLiteDatabase db = MintLink.getReadableDatabase();
		Cursor cursor = db.query(CATEGORY_TBLNAM, FROM, SELECTION, null, null,
				null, ORDER_BY);
		return cursor.getString(1);
	}

	public void addCategory(String strName, double initalValue, int iType) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_NAME, strName);
		values.put(CATEGORY_TOTAL, initalValue);
		values.put(CATEGORY_TYPE, iType);
		db.insertOrThrow(CATEGORY_TBLNAM, null, values);
	}

	public void EditCategoryType(int iCatId, int iType) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_TYPE, iType);
		db.update(CATEGORY_TBLNAM, values, "_ID=" + iCatId, null);
	}

	public void EditCategoryName(int iCatID, String strCatName) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_NAME, strCatName);
		db.update(CATEGORY_TBLNAM, values, "_ID=" + iCatID, null);
	}

	public void updateCategory(int iCatID, double dblTotal) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_TOTAL, dblTotal);
		db.update(CATEGORY_TBLNAM, values, "_ID=" + iCatID, null);
	}
}
