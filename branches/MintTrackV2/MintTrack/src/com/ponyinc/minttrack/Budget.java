/**	
*	Class represents the Budget object 
*   and contains methods for interacting with them.
*	The budget class is an interface class that contains methods 
*	that work with all database objects to provide correct functionality
*	@author Stephan Krach & Christopher Wilkins
*/
package com.ponyinc.minttrack;

import android.content.Context;
import android.database.Cursor;
import android.widget.Spinner;

public class Budget {
	private MintData MintLink;
	private Accounts accounts;
	private Transactions transactions;
	private Categories categories;
	
	/** Secondary Constructor
	*	@param ctx The context in which the object is being createdf
	*/
	Budget(Context ctx) {
		MintLink = new MintData(ctx);
		// Just closing it till we figure out exactly what we want to do.
		MintLink.close();

		accounts = new Accounts(MintLink);
		transactions = new Transactions(MintLink);
		categories = new Categories(MintLink);
	}
	/** Method is a delegated version of addAccount.  This method should always be used instead of the Account Version directly.
	*	@param strName Name of the account being added
	*	@param value Inital balance of the account
	*/
	void addAccount(String strName, double value) {
		accounts.addAccount(strName, value);
	}
	/** Method is a delegated version of getAllAccounts(). This method should always be used instead of the Account Version directly.
	*	@return A cursor containing all accounts.
	*/
	Cursor getAllAccounts() {
		return accounts.getAllAccounts();
	}
	/** Method is a delegated version of getActiveAccounts(). This method should always be used instead of the Account Version directly.
	*	@return A cursor containing only all the active accounts
	*/
	Cursor getActiveAccounts() {
		return accounts.getActiveAccounts();
	}
	/** Method is a delegated version of DeactivateAccount(). This method should always be used instead of the Account Version directly.
	*	@param acc_id ID of the account to be deactivated
	*/
	void DeactivateAccount(int acc_id) {
		accounts.DeactivateAccount(acc_id);
	}
	/** Method is a delegated version of EditAccountName(). This method should always be used instead of the Account Version directly.
	*	@param acc_id ID of account being modified
	*	@param strName New name to be applied to existing account
	*/
	void EditAccountName(int acc_id, String strName) {
		accounts.EditAccountName(acc_id, strName);
	}
	/** Method is a delegated version of EditAccountTotal(). This method should always be used instead of the Account Version directly.
	*	@param acc_id ID of account to be modified
	*	@param total New total to be applied to existing account
	*/
	void EditAccountTotal(int acc_id, double total) {
		accounts.EditAccountTotal(acc_id, total);
	}
	/** Method is a delegated version of ReactivateAccount(). This method should always be used instead of the Account Version directly.
	*	@param acc_id ID of existing account being reactivated
	*/
	void ReactivateAccount(int acc_id) {
		accounts.ReactivateAccount(acc_id);
	}
	/** Method is a delegated version of getCategorys(). This method should always be used instead of the Categories Version directly.
	*	@return Cursor containg all existing categories
	*/
	Cursor getCategorys() {
		return categories.getCategorys();
	}
	/**
	 * 
	 * @param type 0 is for income and 1 is for expense
	 * @return Cursor of category of type income our expense.
	 */
	Cursor getCategorys(int type) {
		return categories.getCategorys(type);
	}
	
	/** Method is a delegated version of getCategorys(). This method should always be used instead of the Categories Version directly.
	*	@param MintLink Database object to be used
	*	@return Cursor containg all existing categories
	*/
	Cursor getCategorys(MintData MintLink) {
		return categories.getCategorys();
	}
	/** Method is a delegated version of getCategory(). This method should always be used instead of the Categories Version directly.
	*	@param intID ID of the category that you want returned in a query
	*	@return Cursor containing the cursor requested
	*/
	Cursor getCategory(int intID) {
		return categories.getCategory(intID);
	}
	/** Method is a delegated version of addCategory(). This method should always be used instead of the Categories Version directly.
	*	@param strName Name of new category
	*	@param initalValue Inital balance of new category
	*	@param iType Type of category that it is
	*/
	void addCategory(String strName, double initalValue, int iType) {
		categories.addCategory(strName, initalValue, iType);
	}
	/** Method is a delegated version of EditCategoryType(). This method should always be used instead of the Categories Version directly.
	*	@param iCatId ID of category being modified
	*	@param iType New type for the account
	*/
	void EditCategoryType(int iCatId, int iType) {
		categories.EditCategoryType(iCatId, iType);
	}
	/** Method is a delegated version of getCategorys(). This method should always be used instead of the Categories Version directly.
	*	@param iCatID ID of category being modified
	*	@param strCatName New name for the category
	*/
	void EditCategoryName(int iCatID, String strCatName) {
		categories.EditCategoryName(iCatID, strCatName);
	}
	/** Method is a delegated version of getCategorys(). This method should always be used instead of the Categories Version directly.
	*	@param iCatID ID of category being modified
	*	@param dblTotal New total for the category
	*/
	void EditCategoryTotal(int iCatID, double dblTotal) {
		categories.updateCategory(iCatID, dblTotal);
	}
	/** Method is used to create a transfer transaction.  A transfer is any transaction that results in money being moved from one account to another.
	*	@param ToAccount_ID Account the transfer amount is going to
	*	@param FromAccount_ID Account the money is being deducted from
	*	@param Amount The value of the currency being moved from one account to another
	*	@param Note A string that contains information about the reason for the transaction
	*	@param Date Date the transaction is being created
	*	@param Category The category or reason that the transfer is occurring
	*/
	void Transfer(int ToAccount_ID, int FromAccount_ID, double Amount,
			String Note, String Date, int Category) {
		transactions.createTransfer(ToAccount_ID, FromAccount_ID, Amount, Note,
				Date, Category);
		Cursor Account_To = accounts.getAccount(ToAccount_ID);
		Cursor Account_From = accounts.getAccount(FromAccount_ID);
		
		Account_To.moveToNext();
		Account_From.moveToNext();
		
		EditAccountTotal(ToAccount_ID, Account_To.getDouble(2) + Amount);
		EditAccountTotal(FromAccount_ID, Account_From.getDouble(2) - Amount);
	}
	/** Method is used to create an expense transaction.  An expense transaction is one that removes money to an account and places that money into an categorys value.
	*	@param FromAccount_ID Account the money is being deducted from
	*	@param Amount The value of the currency being moved from the from account
	*	@param Note A string that contains information about the reason for the transaction
	*	@param Date Date the transaction is being created
	*	@param Category_ID The category or reason that the expense is occurring
	*/
	void Expense(int FromAccount_ID, double Amount, String Note, String Date, int Category_ID) {
		transactions.createExpense(FromAccount_ID, Amount, Note, Date, Category_ID);
		Cursor Account_From = accounts.getAccount(FromAccount_ID);
		Cursor Category = categories.getCategory(Category_ID);

		Account_From.moveToNext();
		Category.moveToNext();
		EditAccountTotal(FromAccount_ID, Account_From.getDouble(2) - Amount);
		EditCategoryTotal(Category_ID, Category.getDouble(2) + Amount);

	}
	/** Method is used to create an Income transaction.  An income transaction is one that added money to an account and to a category from thin air.
	*	@param ToAccount_ID Account the money is being added to
	*	@param Amount The value of the currency being moved into the ToAccount
	*	@param Note A string that contains information about the reason for the transaction
	*	@param Date Date the transaction is being created
	*	@param Category_ID The category or reason that the expense is occurring	
	*/
	void Income(int ToAccount_ID, double Amount, String Note, String Date, int Category_ID) {
		
		transactions.createIncome(ToAccount_ID, Amount, Note, Date, Category_ID);
		Cursor Account_To = accounts.getAccount(ToAccount_ID);
		Cursor Category = categories.getCategory(Category_ID);

		Account_To.moveToNext();
		Category.moveToNext();
		EditAccountTotal(ToAccount_ID, Account_To.getDouble(2) + Amount);
		EditCategoryTotal(Category_ID, Category.getDouble(2) + Amount);		
	
		
	}
	/** Method is a delegated version of getTransactions(). This method should always be used instead of the transactions Version directly.
	*	@return a cursor from the database containing all transactions
	*/
	Cursor getTransactions() {
		return transactions.getTransactions();
	}
	Cursor getTransaction(int _id){
		return transactions.getTransaction(_id);
	}
	/** Method is a delegated version of ClearTransTable(). This method should always be used instead of the transactions Version directly.
	*/
	void ClearTransTable()
	{
		transactions.ClearTable();
	}
}
