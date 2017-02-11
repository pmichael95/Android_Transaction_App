package com.example.sbpackagedb;

public class Account {
	int _accId;
	double _balance;
	
	public Account(int accId, double balance)
	{
		_accId = accId;
		_balance = balance;
	}
	public int get_accId() {
		return _accId;
	}
	public void set_accId(int _accId) {
		this._accId = _accId;
	}
	public double get_Balance() {
		return _balance;
	}
	public void set_Balance(double _Balance) {
		this._balance = _Balance;
	}

}
