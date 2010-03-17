package com.ponyinc.minttrack;

import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;

import android.widget.CursorAdapter;
import android.widget.TextView;
import android.database.Cursor;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import static com.ponyinc.minttrack.Constants.*;

final class AuditCursorAdapter extends CursorAdapter {
	AuditCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override public void bindView(View view, Context context, Cursor cursor) {              
    	TextView dateView = (TextView) view.findViewById(R.id.transactionDate);
    	TextView tranTypeView = (TextView) view.findViewById(R.id.transactionType);
    	TextView amountView =  (TextView) view.findViewById(R.id.transactionAmount);
    	TextView categoryView = (TextView) view.findViewById(R.id.transactionCategory);
    	TextView noteView =  (TextView) view.findViewById(R.id.transactionNote);
    	
    	NumberFormat nf = NumberFormat.getInstance();
    	long millis = cursor.getLong(cursor.getColumnIndex(TRANSACTION_DATE));
    	double dblAmount = cursor.getDouble(cursor.getColumnIndex(TRANSACTION_AMOUNT));
    	int iTranType = cursor.getInt(cursor.getColumnIndex(TRANSACTION_TYPE));
    	String strCategory = cursor.getString(cursor.getColumnIndex("CATNAME"));
    	String strNote = cursor.getString(cursor.getColumnIndex(TRANSACTION_NOTE));
    	
    	//transaction date
    	dateView.setText(DateFormat.getDateInstance().format(new Date(millis)));
    	
    	//transaction type
    	switch (iTranType) {
        case 0:  tranTypeView.setText("Income"); break;
        case 1:  tranTypeView.setText("Expense"); break;
        case 2:  tranTypeView.setText("Transfer"); break;
    	}

    	//transaction amount
    	nf.getCurrency();
    	amountView.setText("$" + nf.format(dblAmount));
    	//transaction category
    	categoryView.setText(strCategory);
    	//transaction note
    	noteView.setText(strNote);
    }

    @Override public View newView(Context context, Cursor cursor, ViewGroup parent) {        
    	LayoutInflater ContextLayoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

    	View view = ContextLayoutInflater.inflate(R.layout.audititem, null);

        bindView(view, context, cursor);
        
        return view;
    }
}
