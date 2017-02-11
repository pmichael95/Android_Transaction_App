package com.example.sbpackagedb;

public class Customer {
	String _name;
	int _custId, _accId;
	
	public Customer(String name, int id, int acc){
		_name = name;
		_custId = id;
		_accId = acc;
	}
	
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public int get_custId() {
		return _custId;
	}
	public void set_custId(int _custId) {
		this._custId = _custId;
	}
	public int get_accId() {
		return _accId;
	}
	public void set_accId(int _accId) {
		this._accId = _accId;
	}
}
