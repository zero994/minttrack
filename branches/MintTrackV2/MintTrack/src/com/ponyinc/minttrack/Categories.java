/** 
*	Class represents the Categories object 
*   and contains methods for interacting with them
*	@author Christopher C. Wilkins
*/
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
	/** Secondary Constructor
	*	@param mintdata Database object to be used for querys
	*/
	Categories(MintData mintdata) {
		MintLink = mintdata;
	}
	/** Outputs a cursor containing all categories
	*	@return A cursor containing all categories
	*/
	public Cursor getCategorys() {
		final String[] FROM = { _ID, CATEGORY_NAME, CATEGORY_TOTAL,
				CATEGORY_TYPE, };
		final String ORDER_BY = CATEGORY_NAME + " DESC";
		SQLiteDatabase db = MintLink.getReadableDatabase();
		Cursor cursor = db.query(CATEGORY_TBLNAM, FROM, null, null, null, null,
				ORDER_BY);
		return cursor;
	}
	/** Outputs a cursor containing all categories, allows DB passed in
	*	@param MintLink 
	*	@return A cursor containing all categories
	*/
	public Cursor getCategorys(MintData MintLink) {
		final String[] FROM = { _ID, CATEGORY_NAME, CATEGORY_TOTAL,
				CATEGORY_TYPE, };
		final String ORDER_BY = CATEGORY_NAME + " DESC";
		SQLiteDatabase db = MintLink.getReadableDatabase();
		Cursor cursor = db.query(CATEGORY_TBLNAM, FROM, null, null, null, null,
				ORDER_BY);
		return cursor;
	}
	/** Outputs a cursor with a single category from the database from the ID
	*	@param intID ID of the category you want
	*	@return A cursor contain the category based on the ID handed to the method
	*/
	public Cursor getCategory(int intID) {
		final String[] FROM = { _ID, CATEGORY_NAME, CATEGORY_TOTAL,
				CATEGORY_TYPE, };
		final String ORDER_BY = CATEGORY_NAME + " DESC";
		final String SELECTION = "_ID=" + intID;

		SQLiteDatabase db = MintLink.getReadableDatabase();
		Cursor cursor = db.query(CATEGORY_TBLNAM, FROM, SELECTION, null, null,
				null, ORDER_BY);
		return cursor;
	}
	/** Method is used to add an category to the category table
	*	@param strName Name of the new account
	*	@param initalValue Inital balance the category will be initalized to
	*	@param iType Type of category that it is
	*/
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
	/** Used to edit an existing categories type
	*	@param iCatId The ID of the category being modified
	*	@param iType New type for the category
	*/
	public void EditCategoryType(int iCatId, int iType) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_TYPE, iType);
		db.update(CATEGORY_TBLNAM, values, "_ID=" + iCatId, null);
	}
	/** Used to edit an existing categories name
	*	@param iCatID iCatId The ID of the category being modified
	*	@param strCatName New name to be applied to the category
	*/
	public void EditCategoryName(int iCatID, String strCatName) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_NAME, strCatName);
		db.update(CATEGORY_TBLNAM, values, "_ID=" + iCatID, null);
	}
	/** Used to edit an existing category's balance
	*	@param iCatID ID of the category being modified
	*	@param dblTotal New balance amount for the category
	*/
	public void updateCategory(int iCatID, double dblTotal) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_TOTAL, dblTotal);
		db.update(CATEGORY_TBLNAM, values, "_ID=" + iCatID, null);
	}
}
