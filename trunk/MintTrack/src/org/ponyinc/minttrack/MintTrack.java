package org.ponyinc.minttrack;

import static android.provider.BaseColumns._ID;
import static org.ponyinc.minttrack.Constants.*;

import java.util.Calendar;

import org.ponyinc.minttrack.MintData;
import org.ponyinc.minttrack.R;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

public class MintTrack extends TabActivity {
	/** Called when the activity is first created. */
	TabHost mTabHost;
	private MintData MintLink;
	private TextView mDateDisplay;
	private Button mPickDate;
	private int mYear;
	private int mMonth;
	private int mDay;
	static final int DATE_DIALOG_ID = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mTabHost = getTabHost();

		mTabHost.addTab(mTabHost.newTabSpec("Home").setIndicator("",
				getResources().getDrawable(R.drawable.homebtn)).setContent(
				R.id.tab1));
		mTabHost.addTab(mTabHost.newTabSpec("Transactions").setIndicator("",
				getResources().getDrawable(R.drawable.transactionbtn))
				.setContent(R.id.tab2));
		mTabHost.addTab(mTabHost.newTabSpec("Audit").setIndicator("",
				getResources().getDrawable(R.drawable.auditbtn)).setContent(
				R.id.tab3));
		mTabHost.addTab(mTabHost.newTabSpec("Tools").setIndicator("",
				getResources().getDrawable(R.drawable.toolsbtn)).setContent(
				R.id.tab4));
		mTabHost.setCurrentTab(0);

		MintLink = new MintData(this);
		//Just closing it till we figure out exactly what we want to do.
		MintLink.close();
		
		Spinner s = (Spinner) findViewById(R.id.spinner);
		fillCatDropDown(s);
		
		try {
			//addCategory("test", 432.43, 1);
			EditCatTotal(1,5000.44);
			Cursor cursor = getCategorys();
			//SimpleCursorAdapter s1 = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,cursor, new String[] {CATEGORY_NAME,_ID}, new int[] {android.R.id.text1,android.R.id.text2});
			//s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			//s.setAdapter(s1);
			showCategory(cursor);
			
//			addAccount("Pentucket Bank", 5000.67);
//			addAccount("Boston Bank", 5000.00);
			
//			ReactivateAccount(1);
//			ReactivateAccount(3);
//			ReactivateAccount(45);
			//EditAccountTotal(1, 14992.98);

//			for(int n = 1; n <= 40; n++)DeactivateAccount(n);
			//DeactivateAccount(1);
			//Cursor cursor2 = getAccounts();
			
			//showAccounts(cursor2);
		} finally {
			MintLink.close();
		}
		
		s.setOnItemSelectedListener(new CatOnItemSelectedListener());
		// Button
		final ImageButton button = (ImageButton) findViewById(R.id.android_button);

		// Dropdown Reason
		Spinner s1 = (Spinner) findViewById(R.id.reason1);
		ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,
				R.array.reason, android.R.layout.simple_spinner_item);
		adapter1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s1.setAdapter(adapter1);

		// Dropdown Pay Type
		Spinner s2 = (Spinner) findViewById(R.id.paytype);
		ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
				R.array.paytype, android.R.layout.simple_spinner_item);
		adapter2
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s2.setAdapter(adapter2);

		// Date Box
		mPickDate = (Button) findViewById(R.id.pickDate);

		// add a click listener to the button
		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// display the current date (this method is below)
		updateDisplay();

	}

	// updates the date in the TextView
	private void updateDisplay() {
		mPickDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear).append(" "));
	}

	// the callback received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	private void addAccount(String strName, double initalValue) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_NAME, strName);
		values.put(ACCOUNT_TOTAL, initalValue);
		values.put(ACCOUNT_ACTIVE, "active");
		db.insertOrThrow(ACCOUNT_TBLNAM, null, values);
	}

	private Cursor getAccounts() {
		final String[] FROM = { _ID, ACCOUNT_NAME, ACCOUNT_TOTAL, ACCOUNT_ACTIVE, };
		final String ORDER_BY = _ID + " ASC";
		SQLiteDatabase db = MintLink.getReadableDatabase();
		Cursor cursor = db.query(ACCOUNT_TBLNAM, FROM, null, null, null, null,
				ORDER_BY);
		startManagingCursor(cursor);
		return cursor;
	}
	
	private void DeactivateAccount(int acc_id)
	//set account to inactive
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_ACTIVE, "inactive");
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}
	private void EditAccountName(int acc_id, String strName)
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();		
		values.put(ACCOUNT_NAME, strName);
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}
	
	private void EditAccountTotal(int acc_id, double total)
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_TOTAL, total);
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}
	
	private void ReactivateAccount(int acc_id)
	//set account to inactive
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_ACTIVE, "active");
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}
	private void showAccounts(Cursor cursor) {
		StringBuilder builder = new StringBuilder("Saved Accounts:\n");
		while (cursor.moveToNext()) {
			if(cursor.getString(3).equalsIgnoreCase("active"))
			{	
				long id = cursor.getLong(0);
				String name = cursor.getString(1);
				double total = cursor.getDouble(2);
				String activity = cursor.getString(3);
				builder.append(id).append(": ");
				builder.append(name).append(": ");
				builder.append(total).append(": ");
				builder.append(activity).append("\n");
			}
		}

		TextView text = (TextView) findViewById(R.id.text);
		text.setText(builder);
	}

	private Cursor getCategorys() {
		final String[] FROM = { _ID, CATEGORY_NAME, CATEGORY_TOTAL,
				CATEGORY_TYPE, };
		final String ORDER_BY = CATEGORY_NAME + " DESC";
		SQLiteDatabase db = MintLink.getReadableDatabase();
		Cursor cursor = db.query(CATEGORY_TBLNAM, FROM, null, null, null, null,
				ORDER_BY);
		startManagingCursor(cursor);
		return cursor;
	}
	private Cursor getCategorys(MintData MintLink) {
		final String[] FROM = { _ID, CATEGORY_NAME, CATEGORY_TOTAL,
				CATEGORY_TYPE, };
		final String ORDER_BY = CATEGORY_NAME + " DESC";
		SQLiteDatabase db = MintLink.getReadableDatabase();
		Cursor cursor = db.query(CATEGORY_TBLNAM, FROM, null, null, null, null,
				ORDER_BY);
		startManagingCursor(cursor);
		return cursor;
	}

	private String getCategory(int intID) {
		final String[] FROM = { _ID, CATEGORY_NAME, CATEGORY_TOTAL,
				CATEGORY_TYPE, };
		final String ORDER_BY = CATEGORY_NAME + " DESC";
		final String SELECTION = "_ID=" + intID;
		

		SQLiteDatabase db = MintLink.getReadableDatabase();
		Cursor cursor = db.query(CATEGORY_TBLNAM, FROM, SELECTION, null, null,
				null, ORDER_BY);
		startManagingCursor(cursor);
		
		return cursor.getString(1);
	}

	
	private void addCategory(String strName, double initalValue, int iType) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_NAME, strName);
		values.put(CATEGORY_TOTAL, initalValue);
		values.put(CATEGORY_TYPE, iType);

		db.insertOrThrow(CATEGORY_TBLNAM, null, values);
	}
	
	private void EditCategoryType(int iCatId, int iType){
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_TYPE, iType);
		db.update(CATEGORY_TBLNAM, values, "_ID=" + iCatId, null);
		db.close();
	}
	
	private void EditCategoryName(int iCatID, String strCatName){
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_NAME, strCatName);
		db.update(CATEGORY_TBLNAM, values, "_ID=" + iCatID, null);
		db.close();
	}
	
	private void updateCategory(int iCatID, double dblTotal){
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_TOTAL, dblTotal);
		db.update(CATEGORY_TBLNAM, values, "_ID=" + iCatID, null);
		db.close();
	}
	
	private void showCategory(Cursor cursor) {
		StringBuilder builder = new StringBuilder("Saved Categories:\n");
		while (cursor.moveToNext()) {
			long id = cursor.getLong(0);
			String name = cursor.getString(1);
			double total = cursor.getDouble(2);
			int type = cursor.getInt(3);
			builder.append(id).append(": ");
			builder.append(name).append(": ");
			builder.append(type).append(": ");
			builder.append(total).append("\n");
		}

		TextView text = (TextView) findViewById(R.id.text);
		text.setText(builder);
	}
	/*
	 * Used to fill Category dropdown from cursor
	 */
	private void fillCatDropDown(Spinner s){
		MintData DBLayer;
		DBLayer = new MintData(this);
		
		try {
			Cursor cursor = getCategorys(DBLayer);
			SimpleCursorAdapter s1 = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,cursor, new String[] {CATEGORY_NAME,_ID}, new int[] {android.R.id.text1,android.R.id.text2});
			s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			s.setAdapter(s1);
		} finally {
			DBLayer.close();
		}
		
	}
	private void EditCatTotal(int catID, double total)
	{	
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CATEGORY_TOTAL, total);
		db.update(CATEGORY_TBLNAM, values, _ID + "=" + catID, null);
		db.close();
	}
}