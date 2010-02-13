package com.ponyinc.minttrack;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.*;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;

public class EntryActivity extends Activity {
	private TextView mDateDisplay;
	private Button mPickDate;
	private int mYear;
	private int mMonth;
	private int mDay;
	Budget budget;

	static final int DATE_DIALOG_ID = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.entry);
		budget = new Budget(this);

		// Date Box
		mPickDate = (Button) findViewById(R.id.pickDate);

		// add a click listener to the button
		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		Spinner ss = (Spinner) findViewById(R.id.reason1);
		fillCatDropDown(ss);
		
		/*
		 * Dropdown Reason Spinner s1 = (Spinner) findViewById(R.id.reason1);
		 * ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,
		 * R.array.reason, android.R.layout.simple_spinner_item); adapter1
		 * .setDropDownViewResource
		 * (android.R.layout.simple_spinner_dropdown_item);
		 * s1.setAdapter(adapter1);
		 */

		// Dropdown Pay Type
		Spinner s2 = (Spinner) findViewById(R.id.paytype);
		fillAccountDropDown(s2);
		
		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		
		//example of hiding an element
		//s2.setVisibility(s2.GONE);
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

	public void fillCatDropDown(Spinner s) {
		Cursor cursor = budget.getCategorys();
		SimpleCursorAdapter s1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, new String[] {
						CATEGORY_NAME, _ID }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		s1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(s1);
	}
	public void fillAccountDropDown(Spinner s) {
		Cursor cursor = budget.getAccounts();
		SimpleCursorAdapter s1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, new String[] {
				ACCOUNT_NAME, _ID }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		s1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(s1);
	}
}
