package com.example.sbpackagedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper 
{
	//Database Version
	private static final int DATABASE_VERSION = 1;
	
	//Database Name
	private static final String DATABASE_NAME = "CustDB";
	
	//Database Tables
	private static final String TABLE_CUSTOMER = "tblCustomer";
	private static final String TABLE_ACCOUNT = "tblAccount";
	
	//tblCustomer columns names
	private static final String custId = "CustID";	//PK
	private static final String custName = "CustName";
	private static final String custAccountId = "AccountId";
	
	//tblAccount columns names
	private static final String accAccountId = "AccountID";
	private static final String accTransacType = "TransacType";
	private static final String accCurrBalance = "CurrBalance";
	private static final String accTransAmount = "TransAmount";
	private static final String accNewBalance = "NewBalance";
	SQLiteDatabase db;
	
	public DatabaseHandler(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	//Creating Objects
	Customer customer1 = new Customer("Steve", 12, 123);
	Customer customer2 = new Customer("Phil", 13, 134);
	Customer customer3 = new Customer("Joshua", 14, 145);
	Customer customer4 = new Customer("Doge", 15, 156);
	
	Account acc1 = new Account(123,1000);
	Account acc2 = new Account(134,2000);
	Account acc3 = new Account(145,10000);
	Account acc4 = new Account(156,20000);
	
	String[] accIds = new String[5];
	String[] names = new String[5];
	String[] custIds = new String[5];
	
	double[] balances = new double[5];
	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{	
		/**
		  	CREATE TABLE tblCustomer(
			CustID INTEGER PRIMARY KEY,
			CustName TEXT,
			AccountID INTEGER UNIQUE,
			FOREIGN KEY(AccountID) REFERENCES tblAccount(AccountID));
		 */
		//Added primary key, foreign key and constraints
		//Tested in SQLite on Windows
		String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_CUSTOMER + " (" + custId + " INTEGER PRIMARY KEY, "
				+ custName + " TEXT, " + custAccountId + " INTEGER UNIQUE, "
				+ "FOREIGN KEY(" + accAccountId + ") " + "REFERENCES " + TABLE_ACCOUNT + "(" + accAccountId + "));"; 
		
		db.execSQL(CREATE_CUSTOMER_TABLE);
		
		
		ContentValues ct1 = new ContentValues();
		ContentValues ct2 = new ContentValues();
		ContentValues ct3 = new ContentValues();
		ContentValues ct4 = new ContentValues();
		
		ct1.put(custId, customer1.get_custId());
		ct1.put(custName, customer1.get_name());
		ct1.put(custAccountId, customer1.get_accId());
		
		ct2.put(custId, customer2.get_custId());
		ct2.put(custName, customer2.get_name());
		ct2.put(custAccountId, customer2.get_accId());
		
		ct3.put(custId, customer3.get_custId());
		ct3.put(custName, customer3.get_name());
		ct3.put(custAccountId, customer3.get_accId());
		
		ct4.put(custId, customer4.get_custId());
		ct4.put(custName, customer4.get_name());
		ct4.put(custAccountId, customer4.get_accId());
		
		db.insert(TABLE_CUSTOMER, null, ct1);
		db.insert(TABLE_CUSTOMER, null, ct2);
		db.insert(TABLE_CUSTOMER, null, ct3);
		db.insert(TABLE_CUSTOMER, null, ct4);
		
		/**
		 CREATE TABLE tblAccount(
		 AccountID INTEGER PRIMARY KEY UNIQUE, 
		 TransacType CHAR, 
		 CurrBalance NUMERIC CHECK(CurrBalance > 0), 
		 TransAmount NUMERIC,
		 NewBalance NUMERIC);
		 */
        
        //Tested in SQLite on Windows
        String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + "(" + accAccountId + " INTEGER PRIMARY KEY UNIQUE, "
        		+ accTransacType + " CHAR, " + accCurrBalance + " NUMERIC CHECK(" + accCurrBalance + " > 0), "
        		+ accTransAmount + " NUMERIC, " + accNewBalance + " NUMERIC);";
        
		db.execSQL(CREATE_ACCOUNT_TABLE);
		
		ContentValues ct5 = new ContentValues();
		ContentValues ct6 = new ContentValues();
		ContentValues ct7 = new ContentValues();
		ContentValues ct8 = new ContentValues();
		
		ct5.put(accAccountId, acc1.get_accId());
		ct5.put(accCurrBalance, acc1.get_Balance());
		
		ct6.put(accAccountId, acc2.get_accId());
		ct6.put(accCurrBalance, acc2.get_Balance());
		
		ct7.put(accAccountId, acc3.get_accId());
		ct7.put(accCurrBalance, acc3.get_Balance());
		
		ct8.put(accAccountId, acc4.get_accId());
		ct8.put(accCurrBalance, acc4.get_Balance());
		
        
		this.db = db;
		
		updateArrays();
        
		// CLOSING DATABASE ON ONCREATE() RESULTS IN CLOSED DB FOR OTHER METHODS, THUS CRASHING
		//db.close(); // Closing database connection
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		//Drop old tables if they exist
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
		
		//Recreate tables
		onCreate(db);
		
	}
	
	/**
	 * CRUD Operations (Create, Read, Update, Delete Operations:
	 */
	
	//Withdraws and deposit will be a static 50,00$ for this assignment.
	double withdraw(int index)
	{
		double initialBalance = 0;
		double newBalance = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Sets transac type to W
		ContentValues vals = new ContentValues();
		vals.put(accTransacType, "W");
		
		String[] columns = new String[]{accCurrBalance};
		String selection = accAccountId + "==" + getCustAccIdFromArray(index);
		Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, null, null ,null, null);
		
		int rowIndex = cursor.getColumnIndex(accCurrBalance);
		
		if (cursor != null)
		{
			cursor.moveToFirst();
			initialBalance = initialBalance + Double.parseDouble(cursor.getString(rowIndex));
		}
		
		newBalance = initialBalance - 50.00;
		
		//Push into database
		vals.put(accNewBalance, newBalance);
		
		return newBalance;
	}
	
	double deposit(int index)
	{
		double initialBalance = 0;
		double newBalance = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Sets transacType to D
		ContentValues vals = new ContentValues();
		vals.put(accTransacType, "D");
		
		String[] columns = new String[]{accCurrBalance};
		String selection = accAccountId + "==" + getCustAccIdFromArray(index);
		Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, null, null ,null, null);
		
		int rowIndex = cursor.getColumnIndex(accCurrBalance);
		
		if (cursor != null)
		{
			cursor.moveToFirst();
			initialBalance = initialBalance + Double.parseDouble(cursor.getString(rowIndex));
		}
		
		newBalance = initialBalance + 50.00;
		
		//Push into database
		vals.put(accNewBalance, newBalance);
		//Also assigns to current balance so that one can be updated and the old balance will still be available
		vals.put(accCurrBalance, newBalance);
		
		return newBalance;
	}
	
	//Query customer's name using id
	 String getCustomerName(String customerId)
	{
		String name = "";
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Using .query instead of .rawQuery
		String[] columns = new String[]{custName};
		String selection = customerId + " == " + custId;
		Cursor cursor = db.query(TABLE_CUSTOMER, columns, selection, null, null, null, null);
		
		int rowIndex = cursor.getColumnIndex(custName);
		
		if (cursor != null)
		{
			cursor.moveToFirst();
			name = name + cursor.getString(rowIndex);
		}
		//cursor.close();
		//db.close();
		
		return name;
	} 
	 
	 //Get accountID by custID
	 String getCustomerAccountId(String customerId)
	 {
		 String id = "";
		 SQLiteDatabase db = this.getWritableDatabase();
		 
		 String[] columns = new String[]{custAccountId};
		 String selection = customerId + " == " + custId;
		 Cursor cursor = db.query(TABLE_CUSTOMER, columns, selection, null, null, null, null);
		 
		 int rowIndex = cursor.getColumnIndex(custAccountId);
		 if (cursor != null)
			{
				cursor.moveToFirst();
				id = id + cursor.getString(rowIndex);
			}
		 return id;
	 }
	 
	
	//Returns a customerObject, parameter specifies which object
	Customer getCustomerObject(int cust)
	{
		if (cust == 1) return customer1;
		else if (cust == 2) return customer2;
		else if (cust == 3) return customer3;
		else if (cust == 4) return customer4;
		else return null;
	}
	
	//Returns db object, if needed
	SQLiteDatabase getDatabaseObject()
	{
		return db;
	}
	
	//Gets all from the table and stores in an array for convenient and easy access later
	void getAllCustAccIdFromCustomer()
	{
		 SQLiteDatabase db = this.getWritableDatabase();
		 
		 String[] columns = new String[]{custAccountId};
		 Cursor cursor = db.query(TABLE_CUSTOMER, columns, null, null, null, null, null);
		 
		 int rowIndex = cursor.getColumnIndex(custAccountId);
		 for (int index = 0; index < 5; index++)
		 {
			 for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
			 {
				//s = s + cursor.getString(rowIndex);
				 accIds[index] = cursor.getString(rowIndex);
			 }
		 }
	}
	
	//Returns an element from the array populated by the method above
	String getCustAccIdFromArray(int index)
	{
		return accIds[index];
	}
	
	void getAllCustIdFromCustomer()
	{
		 SQLiteDatabase db = this.getWritableDatabase();
		 
		 String[] columns = new String[]{custId};
		 Cursor cursor = db.query(TABLE_CUSTOMER, columns, null, null, null, null, null);
		 
		 int rowIndex = cursor.getColumnIndex(custId);
		 for (int index = 0; index < 5; index++)
		 {
			 for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
			 {
				//s = s + cursor.getString(rowIndex);
				 custIds[index] = cursor.getString(rowIndex);
			 }
		 }
	}
	
	String getCustIdFromArray(int index)
	{
		return custIds[index];
	}
	
	
	
	
	void getAllNamesFromCustomer()
	{
		 SQLiteDatabase db = this.getWritableDatabase();
		 
		 String[] columns = new String[]{custName};
		 Cursor cursor = db.query(TABLE_CUSTOMER, columns, null, null, null, null, null);
		 
		 int rowIndex = cursor.getColumnIndex(custName);
		 for (int index = 0; index < 5; index++)
		 {
			 for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
			 {
				//s = s + cursor.getString(rowIndex);
				 names[index] = cursor.getString(rowIndex);
			 }
		 }
	}
	
	String getNameFromArray(int index)
	{
		return names[index];
	}
	
	void getAllBalancesFromAccount()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		String[] columns = new String[]{accCurrBalance};
		 Cursor cursor = db.query(TABLE_ACCOUNT, columns, null, null, null, null, null);
		 
		 int rowIndex = cursor.getColumnIndex(accCurrBalance);
		 for (int index = 0; index < 5; index++)
		 {
			 for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
			 {
				 balances[index] = Double.parseDouble(cursor.getString(rowIndex));
			 }
		 }
	}
	
	double getBalanceFromArray(int index)
	{
		return balances[index];
	}
	
	/*This method is called whenever there is a change in the database to refresh 
	 * the arrays and to be able to get values from the array instead of multiple 
	 * queries (one big query instead of small multiple queries, which is ideal 
	 * for a small program like this). Obviously with thousand of entries, this
	 * would not be ideal, but for a small number of entries, it is very light
	 * on the device's battery.
	 */
	void updateArrays()
	{
		getAllBalancesFromAccount();
		getAllCustAccIdFromCustomer();
		getAllCustIdFromCustomer();
		getAllNamesFromCustomer();
	}
	

	
	
	

}
