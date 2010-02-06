package org.ponyinc.minttrack;

import android.provider.BaseColumns;

public interface Constants extends BaseColumns{
	public static final String ACCOUNT_TBLNAM = "account";
	public static final String CATEGORY_TBLNAM = "categorie";
	public static final String TRANSACTION_TBLNAM = "tran_table";

	// Columns in the Accounts Table
	public static final String ACCOUNT_NAME = "account_name";
	public static final String ACCOUNT_TOTAL = "account_total";
	public static final String ACCOUNT_ACTIVE = "account_active";
	
	//Columns in the Category Table
	public static final String CATEGORY_NAME = "category_name";
	public static final String CATEGORY_TOTAL = "category_total";
	public static final String CATEGORY_TYPE = "category_type";
	
	//Columns in the Transaction Table
	public static final String TRANSACTION_DATE = "tran_date";
	public static final String TRANSACTION_TOACCOUNT = "tran_toaccount";
	public static final String TRANSACTION_FROMACCOUNT = "tran_fromaccount";
	public static final String TRANSACTION_AMOUNT = "tran_amount";
	public static final String TRANSACTION_CATEGORY = "tran_category";
	public static final String TRANSACTION_NOTE = "tran_note";
	public static final String TRANSACTION_TYPE = "tran_type";
	
}
