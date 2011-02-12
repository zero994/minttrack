package com.ponyinc.minttrack.basetypes;

import com.ponyinc.minttrack.MintData;

public abstract class Transaction {
	private String tDate;
	private long tToAccount;
	private long tFromAccount;
	private double tAmount;
	private long tCategory;
	private String tNote;
	private long tId;
	
	//Default constructor
	public Transaction()
	{
		setId(-1);
		setDate("");
		setToAccount(-1);
		setFromAccount(-1);
		setAmount(0.0);
		setCategory(-1);
		setNote("");
	}
	//Secondary constructor
	public Transaction(long id, String date, long to, long from, 
			double amount, long category, String note, int type)
	{
		setId(id);
		setDate(date);
		setToAccount(to);
		setFromAccount(from);
		setAmount(amount);
		setCategory(category);
		setNote(note);
	}
	public void setDate(String tDate) {
		this.tDate = tDate;
	}
	public String getDate() {
		return tDate;
	}
	public void setToAccount(long tToAccount) {
		this.tToAccount = tToAccount;
	}
	public long getToAccount() {
		return tToAccount;
	}
	public void setFromAccount(long tFromAccount) {
		this.tFromAccount = tFromAccount;
	}
	public long getFromAccount() {
		return tFromAccount;
	}
	public void setAmount(double tAmount) {
		this.tAmount = tAmount;
	}
	public double getAmount() {
		return tAmount;
	}
	public void setCategory(long tCategory) {
		this.tCategory = tCategory;
	}
	public long getCategory() {
		return tCategory;
	}
	public void setNote(String tNote) {
		this.tNote = tNote;
	}
	public String getNote() {
		return tNote;
	}
	public long getId() {
		return tId;
	}
	public void setId(long tId) {
		this.tId = tId;
	}
	
	public abstract void update(MintData md, Transaction t);
	public abstract void create(MintData md, Transaction t);
}
