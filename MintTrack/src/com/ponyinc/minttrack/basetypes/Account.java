package com.ponyinc.minttrack.basetypes;

public class Account {
	private String accountName;
	private double accountTotal;
	private boolean accountIsActive;
	//Default constructor
	public Account()
	{
		setName("");
		setTotal(0.0);
		setActive(false);
	}
	//Secondary constructor
	public Account(String name, double total, boolean active)
	{
		setName(name);
		setTotal(total);
		setActive(active);
	}
	public void setName(String aName) {
		this.accountName = aName;
	}
	public String getName() {
		return accountName;
	}
	public void setTotal(double aTotal) {
		this.accountTotal = aTotal;
	}
	public double getTotal() {
		return accountTotal;
	}
	public void setActive(boolean aActive) {
		this.accountIsActive = aActive;
	}
	public boolean isActive() {
		return accountIsActive;
	}
}
