package com.ponyinc.minttrack.basetypes;

public class Account {
	private String aName;
	private double aTotal;
	private boolean aActive;
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
		this.aName = aName;
	}
	public String getName() {
		return aName;
	}
	public void setTotal(double aTotal) {
		this.aTotal = aTotal;
	}
	public double getTotal() {
		return aTotal;
	}
	public void setActive(boolean aActive) {
		this.aActive = aActive;
	}
	public boolean isActive() {
		return aActive;
	}
}
