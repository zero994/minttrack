package org.ponyinc.minttrack;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

public class MintTrack extends TabActivity {
    /** Called when the activity is first created. */
	TabHost mTabHost;
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
        
        mTabHost.addTab(mTabHost.newTabSpec("Home").setIndicator("", getResources().getDrawable(R.drawable.homebtn)).setContent(R.id.tab1));
        mTabHost.addTab(mTabHost.newTabSpec("Transactions").setIndicator("", getResources().getDrawable(R.drawable.transactionbtn)).setContent(R.id.tab2));
        mTabHost.addTab(mTabHost.newTabSpec("Audit").setIndicator("", getResources().getDrawable(R.drawable.auditbtn)).setContent(R.id.tab3));
        mTabHost.addTab(mTabHost.newTabSpec("Tools").setIndicator("", getResources().getDrawable(R.drawable.toolsbtn)).setContent(R.id.tab4));
        mTabHost.setCurrentTab(0);
        
        //Button
        final ImageButton button = (ImageButton) findViewById(R.id.android_button);
        
        //Dropdown for planets
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.planets, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        
       //Dropdown Reason
        Spinner s1 = (Spinner) findViewById(R.id.reason1);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
                this, R.array.reason, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);
        
      //Dropdown Pay Type
        Spinner s2 = (Spinner) findViewById(R.id.paytype);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(
                this, R.array.paytype, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);
        
        //Date Box
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
    	mPickDate.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("-")
                    .append(mDay).append("-")
                    .append(mYear).append(" "));
    }
 // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
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
                    return new DatePickerDialog(this,
                                mDateSetListener,
                                mYear, mMonth, mDay);
                }
                return null;
            }
}