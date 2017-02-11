package com.example.sbpackagedb;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CustomerMenu extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_customer_menu, container, false);
		return v;
	}
	
	//Buttons
	Button add, delete, update, search;

	@Override
	public void onStart() {
		super.onStart();
		
		//Buttons
		add = (Button) getActivity().findViewById(R.id.frag0_button_add);
		add.setOnClickListener(new OnClick());
		
		delete = (Button) getActivity().findViewById(R.id.frag0_button_delete);
		delete.setOnClickListener(new OnClick());
		
		
	//potato	
		update = (Button) getActivity().findViewById(R.id.frag0_button_update);
		update.setOnClickListener(new OnClick());
		
		search = (Button) getActivity().findViewById(R.id.frag0_button_search);
		search.setOnClickListener(new OnClick());
	}
	
	public class OnClick implements View.OnClickListener
	{
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.frag0_button_add:
				showInConstructionDialog();
				break;
			case R.id.frag0_button_delete:
				showInConstructionDialog();
				break;
			case R.id.frag0_button_update:
				showInConstructionDialog();
				break;
			case R.id.frag0_button_search:
				//Switch to CustomerRecord.java fragment
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				
				CustomerRecord customerRecord = new CustomerRecord();
				fragmentTransaction.replace(android.R.id.content, customerRecord);
				
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
				
				break;
			
			}
		}
		
	}
	
	//Shows a Dialog saying "In Construction"
	public void showInConstructionDialog()
	{
		new AlertDialog.Builder(getActivity())
		.setTitle("In Construction")
		.setMessage("This feature is in construction.")
		.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				//Do Nothing
				
			}
		})
		
		.show();
	}
		
	

}
