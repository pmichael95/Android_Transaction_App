package com.example.sbpackagedb;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TransactionWindow extends Fragment {
	String accId; //Holds the accountId to query for the balances
	Button back, compute;
	TextView accountId, currentBalance, transactionAmount, newBalance;
	SQLiteDatabase db;
	private static final String TABLE_CUSTOMER = "tblCustomer";
	private static final String accCurrBalance = "CurrBalance";
	DatabaseHandler dh;
	
	CustomerRecord cr;
	int customerIndex;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View v = inflater.inflate(R.layout.fragment_transaction_window, container, false);
		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		
		//Buttons
		back = (Button) getActivity().findViewById(R.id.frag2_button_back);
		back.setOnClickListener( new OnClick());
		compute = (Button) getActivity().findViewById(R.id.frag2_button_back);
		compute.setOnClickListener( new OnClick());
		
		//TextViews
		accountId = (TextView) getActivity().findViewById(R.id.frag2_accountId);
		currentBalance = (TextView) getActivity().findViewById(R.id.frag2_currentBalance);
		transactionAmount = (TextView) getActivity().findViewById(R.id.frag2_transactionAmount);
		newBalance  = (TextView) getActivity().findViewById(R.id.frag2_newBalance);
		
		//accId = getArguments().getString("accId");
		
		//Because we are in a fragment, we use getAcitity()
		Context context = getActivity();
		
		//class objects
		dh = new DatabaseHandler(context);		
		cr = new CustomerRecord();
		
		customerIndex = cr.getIndex();
		
		//Set certain fields when the fragment opens
		currentBalance.setText(String.valueOf(dh.getBalanceFromArray(customerIndex)));
		accountId.setText(dh.getCustIdFromArray(customerIndex));
		
		}
	
	public class OnClick implements View.OnClickListener 
	{
		public void onClick(View v) 
		{
			if( v.getId() == R.id.frag2_button_back)
			{
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				CustomerMenu customerMenu = new CustomerMenu();
				fragmentTransaction.replace(android.R.id.content, customerMenu);
				
				//Add to the back stack
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
			else if (v.getId() == R.id.frag2_button_compute)
			{
				//Check for which is checked, then do the operation
				if (R.id.frag2_radio_deposit.isChecked())
				{
					newBalance.setText(String.valueOf(dh.deposit(customerIndex)));
					//Transaction amount is always the same for this assignment
					transactionAmount.setText("50,00");
				}
				else if (R.id.frag2_radio_withdraw.isChecked())
				{
					//Doing the withdraw as well as setting text in one line
					newBalance.setText(String.valueOf(dh.withdraw(customerIndex)));
					//Transaction amount is always the same for this assignment
					transactionAmount.setText("50,00");
				}
				else if (R.id.frag2_radio_balance.isChecked())
				{
					//Turns off the textViews
					transactionAmount.setText("");
					newBalance.setText("");
					
					currentBalance.setText(String.valueOf(dh.getBalanceFromArray(customerIndex)));
				}
			}
		}
	}
}
