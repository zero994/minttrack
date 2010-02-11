package com.ponyinc.minttrack;

import android.content.Context;
import android.database.Cursor;

public class Budget {
	private MintData MintLink;
	private Accounts accounts;
	private Transactions transactions;
	private Categories categories;

	Budget(Context ctx) {
		MintLink = new MintData(ctx);
		// Just closing it till we figure out exactly what we want to do.
		MintLink.close();

		accounts = new Accounts(MintLink);
		transactions = new Transactions(MintLink);
		categories = new Categories(MintLink);
	}

	void AddAcount(String strName, double value) {
		accounts.addAccount(strName, value);
	}

	Cursor getAccounts() {
		return accounts.getAccounts();
	}

	void DeactivateAccount(int acc_id) {
		accounts.DeactivateAccount(acc_id);
	}

	void EditAccountName(int acc_id, String strName) {
		accounts.EditAccountName(acc_id, strName);
	}

	void EditAccountTotal(int acc_id, double total) {
		accounts.EditAccountTotal(acc_id, total);
	}

	void ReactivateAccount(int acc_id) {
		accounts.ReactivateAccount(acc_id);
	}

	Cursor getCategorys() {
		return categories.getCategorys();
	}

	Cursor getCategorys(MintData MintLink) {
		return categories.getCategorys();
	}

	String getCategory(int intID) {
		return categories.getCategory(intID);
	}

	void addCategory(String strName, double initalValue, int iType) {
		categories.addCategory(strName, initalValue, iType);
	}

	void EditCategoryType(int iCatId, int iType) {
		categories.EditCategoryType(iCatId, iType);
	}

	void EditCategoryName(int iCatID, String strCatName) {
		categories.EditCategoryName(iCatID, strCatName);
	}

	void updateCategory(int iCatID, double dblTotal) {
		categories.updateCategory(iCatID, dblTotal);
	}

	void Transfer(int ToAccount_ID, int FromAccount_ID, double Amount,
			String Note, String Date) {
		transactions.createTransfer(ToAccount_ID, FromAccount_ID, Amount, Note,
				Date);

		Cursor Account_To = accounts.getAccount(ToAccount_ID);
		Account_To.moveToNext();
		EditAccountTotal(ToAccount_ID, Account_To.getDouble(2) + Amount);

		Cursor Account_From = accounts.getAccount(FromAccount_ID);
		Account_From.moveToNext();
		EditAccountTotal(FromAccount_ID, Account_From.getDouble(2) - Amount);
	}

	Cursor getTransactions() {
		return transactions.getTransactions();
	}
}
