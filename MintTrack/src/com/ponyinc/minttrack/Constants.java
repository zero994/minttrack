package com.ponyinc.minttrack;

import android.provider.BaseColumns;

public interface Constants extends BaseColumns {
	public static final String ACCOUNT_TBLNAM = "account";
	public static final String CATEGORY_TBLNAM = "category";
	public static final String TRANSACTION_TBLNAM = "tran_table";

	// Columns in the Accounts Table
	public static final String ACCOUNT_NAME = "account_name";
	public static final String ACCOUNT_TOTAL = "account_total";
	public static final String ACCOUNT_ACTIVE = "account_active";

	// Columns in the Category Table
	public static final String CATEGORY_NAME = "category_name";
	public static final String CATEGORY_TOTAL = "category_total";
	public static final String CATEGORY_TYPE = "category_type";

	// Columns in the Transaction Table
	
	//Date of transaction
	public static final String TRANSACTION_DATE = "tran_date";
	//_ID from account table of where the money is going too
	public static final String TRANSACTION_TOACCOUNT = "tran_toaccount";
	//_ID from account table of where the money is coming from
	public static final String TRANSACTION_FROMACCOUNT = "tran_fromaccount";
	//The amount of the transaction
	public static final String TRANSACTION_AMOUNT = "tran_amount";
	//The reason or category of the transaction. _ID from category table
	public static final String TRANSACTION_CATEGORY = "tran_category";
	//A note left by the user as a reminder of the transaction
	public static final String TRANSACTION_NOTE = "tran_note";
	//The type of transaction 0=Income 1= Expense 2=Transfer
	public static final String TRANSACTION_TYPE = "tran_type";

}