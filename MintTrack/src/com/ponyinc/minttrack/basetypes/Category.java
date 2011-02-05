package com.ponyinc.minttrack.basetypes;

public class Category {
	private String cName;
	private int cType;
	private double cTotal;
	private boolean cActive;
	
	//Default constructor
	public Category()
	{
		setName("");
		setType(-1);
		setTotal(0.0);
		setActive(false);
	}
	//Secondary constructor
	public Category(String name, int type, double total, boolean active)
	{
		setName(name);
		setType(type);
		setTotal(total);
		setActive(active);
	}
	
	public void setName(String cName)
	{
		this.cName = cName;
	}
	public String getName()
	{
		return cName;
	}

	public void setType(int cType) {
		this.cType = cType;
	}

	public int getType() {
		return cType;
	}

	public void setTotal(double cTotal) {
		this.cTotal = cTotal;
	}

	public double getTotal() {
		return cTotal;
	}

	public void setActive(boolean cActive) {
		this.cActive = cActive;
	}

	public boolean isActive() {
		return cActive;
	}
}
