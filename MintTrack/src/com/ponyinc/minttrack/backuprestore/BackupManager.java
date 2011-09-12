package com.ponyinc.minttrack.backuprestore;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ponyinc.minttrack.Constants;
import com.ponyinc.minttrack.basetypes.Account;
import com.ponyinc.minttrack.basetypes.Category;
import com.ponyinc.minttrack.basetypes.Transaction;


public class BackupManager{
	
	private static Vector<Transaction> transactions = new Vector<Transaction>();
	private static Vector<Account> accounts = new Vector<Account>();
	private static Vector<Category> categories = new Vector<Category>();
	
	private static Cursor aCursor, cCursor, tCursor;
	
	public BackupManager() {
		SQLiteDatabase db =  SQLiteDatabase.openDatabase(Constants.PATH_TO_DB, null, SQLiteDatabase.OPEN_READONLY);
		aCursor = db.query(Constants.ACCOUNT_TBLNAM, null, null, null, null, null, null);
		cCursor = db.query(Constants.CATEGORY_TBLNAM, null, null, null, null, null, null);
		tCursor = db.query(Constants.TRANSACTION_TBLNAM, null, null, null, null, null, null);
		db.close();
	}
	
	public void exportToXml()
	{
		//Access database and Retrieve data
		getData();
		//Format to XML
		String xmlData = "<Backup><Accounts size=\"" + accounts.size() + "\">";
		for(int i = 0; i < accounts.size(); i++)
		{
			xmlData += "<Account>";
			xmlData += "<Id>" + String.valueOf(accounts.get(i).getId()) + "</Id>";
			xmlData += "<Total>" + String.valueOf(accounts.get(i).getTotal()) + "</Total>";
			xmlData += "<Name>" + String.valueOf(accounts.get(i).getName()) + "</Name>";
			xmlData += "<Active>" + String.valueOf(accounts.get(i).isActive()) + "</Active>";
			xmlData += "</Account>";
		}
		xmlData += "</Accounts><Categories size=\"" + categories.size() + "\">";
		for(int i = 0; i < categories.size(); i++)
		{
			xmlData += "<Category>";
			xmlData += "<Id>" + String.valueOf(categories.get(i).getId()) + "</Id>";
			xmlData += "<Total>" + String.valueOf(categories.get(i).getTotal()) + "</Total>";
			xmlData += "<Name>" + String.valueOf(categories.get(i).getName()) + "</Name>";
			xmlData += "<Type>" + String.valueOf(categories.get(i).getType()) + "</Type>";
			xmlData += "<Active>" + String.valueOf(categories.get(i).isActive()) + "</Active>";
			xmlData += "</Category>";
		}
		xmlData += "</Categories><Transactions size=\"" + transactions.size() + "\">";
		for(int i = 0; i < transactions.size(); i++)
		{
			xmlData += "<Transaction>";
			xmlData += "<Id>" + String.valueOf(transactions.get(i).getId()) + "</Id>";
			xmlData += "<Date>" + String.valueOf(transactions.get(i).getDate()) + "</Date>";
			xmlData += "<To>" + String.valueOf(transactions.get(i).getToAccount()) + "</To>";
			xmlData += "<From>" + String.valueOf(transactions.get(i).getFromAccount()) + "</From>";
			xmlData += "<Amount>" + String.valueOf(transactions.get(i).getAmount()) + "</Amount>";
			xmlData += "<Category>" + String.valueOf(transactions.get(i).getCategory()) + "</Category>";
			xmlData += "<Note>" + String.valueOf(transactions.get(i).getNote()) + "</Note>";
			xmlData += "<Type>" + String.valueOf(transactions.get(i).getType()) + "</Type>";
			xmlData += "</Transaction>";
			
		}
		xmlData += "</Transactions></Backup>";
		
		System.out.println(xmlData);
	}
	
	public void exportToHtml(Context c)
	{
		//Access database and Retrieve data
		getData();
		//Format to HTML
	}
	
	/**
	 * Gets all user data and stores it into multiple vectors
	 * @param c Context to gain data from
	 */
	private static void getData()
	{
		transactions.clear();
		

		tCursor.moveToFirst();
		
		for(; !tCursor.isAfterLast(); tCursor.moveToNext())
		{
			transactions.add(
				new Transaction(
					//ID
						tCursor.getLong(0),
					//Date
						tCursor.getString(1),
					//To
						tCursor.getLong(2),
					//From
						tCursor.getLong(3),
					//Amount
						tCursor.getDouble(4),
					//Category
						tCursor.getLong(5),
					//Note
						tCursor.getString(6),
					//Type
						tCursor.getInt(7)
				)
			);
		}

		accounts.clear();
		
		aCursor.moveToFirst();
		
		for(; !aCursor.isAfterLast(); aCursor.moveToNext())
		{
			accounts.add(
				new Account(
					//ID
						aCursor.getLong(0),
					//Total
						aCursor.getDouble(1),
					//Name
						aCursor.getString(2),
					//Active
						aCursor.getString(3)
				)
			);
		}
		
		categories.clear();
		
		cCursor.moveToFirst();
		
		for(; !cCursor.isAfterLast(); cCursor.moveToNext())
		{
			categories.add(
				new Category(
					//ID
						cCursor.getLong(0),
					//Total
						cCursor.getDouble(1),
					//Name
						cCursor.getString(2),
					//Type
						cCursor.getInt(3),
					//Active
						cCursor.getString(4)
				)
			);
		}
		
		tCursor.close();
		cCursor.close();
		aCursor.close();
	}	
}
