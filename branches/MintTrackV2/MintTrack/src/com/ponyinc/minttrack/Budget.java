package com.ponyinc.minttrack;

import android.content.Context;
import android.database.Cursor;
import static com.ponyinc.minttrack.Constants.*;
/**	
*	Class represents the Budget object 
*   and contains methods for interacting with them.
*	The budget class is an interface class that contains methods 
*	that work with all database objects to provide correct functionality
*	@author Stephan Krach & Christopher Wilkins
*/
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
	*	@param value Initial balance of the account
	*	@param isActive Is account active
	*/
	void addAccount(String strName, double value, boolean isActive) {
		accounts.addAccount(strName, value, isActive);
		
	}
	/** Method is a delegated version of getAllAccounts(). This method should always be used instead of the Account Version directly.
	*	@return A cursor containing all accounts.
	*/
	Cursor getAllAccounts() {
		return accounts.getAllAccounts();
	}
	
	Cursor getAccount(long acc_ID)
	{
		return accounts.getAccount(acc_ID);
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
	void DeactivateAccount(long acc_id) {
		accounts.DeactivateAccount(acc_id);
	}
	/** Method is a delegated version of EditAccountName(). This method should always be used instead of the Account Version directly.
	*	@param acc_id ID of account being modified
	*	@param strName New name to be applied to existing account
	*/
	void EditAccountName(long acc_id, String strName) {
		accounts.EditAccountName(acc_id, strName);
	}
	/** Method is a delegated version of EditAccountTotal(). This method should always be used instead of the Account Version directly.
	*	@param acc_id ID of account to be modified
	*	@param total New total to be applied to existing account
	*/
	void EditAccountTotal(long acc_id, double total) {
		accounts.EditAccountTotal(acc_id, total);
	}
	/** Method is a delegated version of ReactivateAccount(). This method should always be used instead of the Account Version directly.
	*	@param acc_id ID of existing account being reactivated
	*/
	void ReactivateAccount(long acc_id) {
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
	Cursor getCategory(long intID) {
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
	void EditCategoryType(long iCatId, int iType) {
		categories.EditCategoryType(iCatId, iType);
	}
	/** Method is a delegated version of getCategorys(). This method should always be used instead of the Categories Version directly.
	*	@param iCatID ID of category being modified
	*	@param strCatName New name for the category
	*/
	void EditCategoryName(long iCatID, String strCatName) {
		categories.EditCategoryName(iCatID, strCatName);
	}
	/** Method is a delegated version of getCategorys(). This method should always be used instead of the Categories Version directly.
	*	@param iCatID ID of category being modified
	*	@param dblTotal New total for the category
	*/
	void EditCategoryTotal(long iCatID, double dblTotal) {
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
	void Transfer(long ToAccount_ID, long FromAccount_ID, double Amount,
			String Note, String Date, long Category) {
		transactions.createTransfer(ToAccount_ID, FromAccount_ID, Amount, Note,
				Date, Category);
		Cursor Account_To = accounts.getAccount(ToAccount_ID);
		Cursor Account_From = accounts.getAccount(FromAccount_ID);
		
		Account_To.moveToNext();
		Account_From.moveToNext();
		
		EditAccountTotal(ToAccount_ID, Account_To.getDouble(Account_To.getColumnIndex(ACCOUNT_TOTAL)) + Amount);
		EditAccountTotal(FromAccount_ID, Account_From.getDouble(Account_From.getColumnIndex(ACCOUNT_TOTAL)) - Amount);
	}
	
	void updateTransfer(long trans_ID, long ToAccount_ID, long FromAccount_ID, double newAmount,
			String Note, String Date, long Category)
	{
		Cursor trans = transactions.getTransaction(trans_ID);
		trans.moveToFirst();
		double oldAmount = trans.getDouble(trans.getColumnIndex(TRANSACTION_AMOUNT));
		
		transactions.updateTransfer(trans_ID, ToAccount_ID, FromAccount_ID, newAmount, Note,
				Date, Category);
		
		Cursor Account_To = accounts.getAccount(ToAccount_ID);
		Cursor Account_From = accounts.getAccount(FromAccount_ID);
		
		Account_To.moveToNext();
		Account_From.moveToNext();
		
		EditAccountTotal(ToAccount_ID, Account_To.getDouble(Account_To.getColumnIndex(ACCOUNT_TOTAL)) - oldAmount);
		EditAccountTotal(FromAccount_ID, Account_From.getDouble(Account_From.getColumnIndex(ACCOUNT_TOTAL)) + oldAmount);
		
		EditAccountTotal(ToAccount_ID, Account_To.getDouble(Account_To.getColumnIndex(ACCOUNT_TOTAL)) + newAmount);
		EditAccountTotal(FromAccount_ID, Account_From.getDouble(Account_From.getColumnIndex(ACCOUNT_TOTAL)) - newAmount);
	}
	/** Method is used to create an expense transaction.  An expense transaction is one that removes money to an account and places that money into an categorys value.
	*	@param FromAccount_ID Account the money is being deducted from
	*	@param Amount The value of the currency being moved from the from account
	*	@param Note A string that contains information about the reason for the transaction
	*	@param Date Date the transaction is being created
	*	@param Category_ID The category or reason that the expense is occurring
	*/
	void Expense(long FromAccount_ID, double Amount, String Note, String Date, long Category_ID) {
		transactions.createExpense(FromAccount_ID, Amount, Note, Date, Category_ID);
		Cursor Account_From = accounts.getAccount(FromAccount_ID);
		Cursor Category = categories.getCategory(Category_ID);

		Account_From.moveToNext();
		Category.moveToNext();
		EditAccountTotal(FromAccount_ID, Account_From.getDouble(Account_From.getColumnIndex(ACCOUNT_TOTAL)) - Amount);
		EditCategoryTotal(Category_ID, Category.getDouble(Category.getColumnIndex(CATEGORY_TOTAL)) + Amount);

	}
	
	void updateExpense(long trans_ID, long FromAccount_ID, double newAmount,
			String Note, String Date, long Category_ID)
	{
		
		Cursor trans = transactions.getTransaction(trans_ID);
		trans.moveToFirst();
		double oldAmount = trans.getDouble(trans.getColumnIndex(TRANSACTION_AMOUNT));
		
		transactions.updateExpense(trans_ID, FromAccount_ID, newAmount, Note,
				Date, Category_ID);
		
		Cursor Account_From = accounts.getAccount(FromAccount_ID);
		Cursor Category = categories.getCategory(Category_ID);

		Account_From.moveToNext();
		Category.moveToNext();
		
		EditAccountTotal(FromAccount_ID, Account_From.getDouble(Account_From.getColumnIndex(ACCOUNT_TOTAL)) + oldAmount);
		EditCategoryTotal(Category_ID, Category.getDouble(Category.getColumnIndex(CATEGORY_TOTAL)) - oldAmount);
		
		EditAccountTotal(FromAccount_ID, Account_From.getDouble(Account_From.getColumnIndex(ACCOUNT_TOTAL)) - newAmount);
		EditCategoryTotal(Category_ID, Category.getDouble(Category.getColumnIndex(CATEGORY_TOTAL)) + newAmount);
	}
	/** Method is used to create an Income transaction.  An income transaction is one that added money to an account and to a category from thin air.
	*	@param ToAccount_ID Account the money is being added to
	*	@param Amount The value of the currency being moved into the ToAccount
	*	@param Note A string that contains information about the reason for the transaction
	*	@param Date Date the transaction is being created
	*	@param Category_ID The category or reason that the expense is occurring	
	*/
	void Income(long ToAccount_ID, double Amount, String Note, String Date, long Category_ID) {
		
		transactions.createIncome(ToAccount_ID, Amount, Note, Date, Category_ID);
		Cursor Account_To = accounts.getAccount(ToAccount_ID);
		Cursor Category = categories.getCategory(Category_ID);

		Account_To.moveToNext();
		Category.moveToNext();
		EditAccountTotal(ToAccount_ID, Account_To.getDouble(Account_To.getColumnIndex(ACCOUNT_TOTAL)) + Amount);
		EditCategoryTotal(Category_ID, Category.getDouble(Category.getColumnIndex(CATEGORY_TOTAL)) + Amount);		
	
		
	}
	
	void updateIncome(long trans_ID, long ToAccount_ID, double newAmount, String Note, String Date, long Category_ID) {
		
		Cursor trans = transactions.getTransaction(trans_ID);
		trans.moveToFirst();
		double oldAmount = trans.getDouble(trans.getColumnIndex(TRANSACTION_AMOUNT));
		
		transactions.updateIncome(trans_ID, ToAccount_ID, newAmount, Note, Date, Category_ID);
	
		Cursor Account_To = accounts.getAccount(ToAccount_ID);
		Cursor Category = categories.getCategory(Category_ID);

		Account_To.moveToNext();
		Category.moveToNext();
		
		EditAccountTotal(ToAccount_ID, Account_To.getDouble(Account_To.getColumnIndex(ACCOUNT_TOTAL)) - oldAmount);
		EditCategoryTotal(Category_ID, Category.getDouble(Category.getColumnIndex(CATEGORY_TOTAL)) - oldAmount);		
		
		EditAccountTotal(ToAccount_ID, Account_To.getDouble(Account_To.getColumnIndex(ACCOUNT_TOTAL)) + newAmount);
		EditCategoryTotal(Category_ID, Category.getDouble(Category.getColumnIndex(CATEGORY_TOTAL)) + newAmount);	
	
		
	}
	/** Method is a delegated version of getTransactions(). This method should always be used instead of the transactions Version directly.
	*	@return a cursor from the database containing all transactions
	*/
	Cursor getTransactions() {
		return transactions.getTransactions();
	}
	Cursor getTransactions(String FromDate, String ToDate) {
		return transactions.getTransactions(FromDate,ToDate);
	}
	Cursor getTransaction(long transID){
		return transactions.getTransaction(transID);
	}
/*	
 	void deleteTransaction(double transID){
		transactions.deleteTransaction(transID);
	}
*/
	/** Undo and deleted transaction from log
	 * @param trans_ID transaction to be deleted
	 */
	void deleteTransaction(long trans_ID)
	{
		Cursor trans = transactions.getTransaction(trans_ID);
		trans.moveToFirst();
		
		double Amount = trans.getDouble(trans.getColumnIndex(TRANSACTION_AMOUNT));
		
		long ToAccount_ID = trans.getInt(trans.getColumnIndex(TRANSACTION_TOACCOUNT));
		long FromAccount_ID = trans.getInt(trans.getColumnIndex(TRANSACTION_FROMACCOUNT));
		long Category_ID = trans.getInt(trans.getColumnIndex(TRANSACTION_CATEGORY));
		
		Cursor Account_To = accounts.getAccount(ToAccount_ID);
		Cursor Account_From = accounts.getAccount(FromAccount_ID);
		Cursor Category = categories.getCategory(Category_ID);
		
		Account_To.moveToFirst();
		Account_From.moveToFirst();
		Category.moveToFirst();
		
		if(Account_To.getCount()!= 0)
			EditAccountTotal(ToAccount_ID, Account_To.getDouble(Account_To.getColumnIndex(ACCOUNT_TOTAL)) - Amount);
		if(Category.getCount()!= 0)
			EditCategoryTotal(Category_ID, Category.getDouble(Category.getColumnIndex(CATEGORY_TOTAL)) - Amount);
		if(Account_From.getCount()!= 0)
			EditAccountTotal(FromAccount_ID, Account_From.getDouble(Account_From.getColumnIndex(ACCOUNT_TOTAL)) + Amount);
		
		transactions.removeTransaction(trans_ID);
	}
	/** Method is a delegated version of ClearTransTable(). This method should always be used instead of the transactions Version directly.
	*/
	void ClearTransTable()
	{
		transactions.ClearTable();
	}
}
