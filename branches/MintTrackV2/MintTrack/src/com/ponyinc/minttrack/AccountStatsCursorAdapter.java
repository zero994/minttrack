package com.ponyinc.minttrack;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import static com.ponyinc.minttrack.Constants.*;

public final class AccountStatsCursorAdapter extends CursorAdapter {

	public AccountStatsCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor){
		TextView accountName = (TextView) view.findViewById(R.id.accountName);
		TextView accountAmount = (TextView) view.findViewById(R.id.accountTotal);
		TextView accountActivity = (TextView) view.findViewById(R.id.accountActivity);
		
		String strAccountName = cursor.getString(cursor.getColumnIndex(ACCOUNT_NAME));
		double dblAccountAmount = cursor.getDouble(cursor.getColumnIndex(ACCOUNT_TOTAL));
		int intAccountActivity = cursor.getInt(cursor.getColumnIndex(ACCOUNT_ACTIVE));

		switch(intAccountActivity){
		case 0:
			accountActivity.setTextColor(Color.RED);
			accountActivity.setText("Inactive");
			break;
		case 1:
			accountActivity.setTextColor(Color.GREEN);
			accountActivity.setText("Active");
			break;
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater ContextLayoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

    	View view = ContextLayoutInflater.inflate(R.layout.accountitem, null);

        bindView(view, context, cursor);
        
        return view;
	}

}
