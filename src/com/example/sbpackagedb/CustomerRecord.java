/*Here, an index based system is used to keep track of the customers
 * as the user click next or previous. It is also used to interact with
 * DatabaseHandler and TransactionWindow classes
 */
 

package com.example.sbpackagedb;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import as2.com.sbpack.FragmentMain.OnClick;

public class CustomerRecord extends Fragment {
	TextView name, custId, accId;
	Button previous, transaction, back, next;
	ImageView image;
	
	int user1 = R.drawable.doge;
	int user2 = R.drawable.nic;
	int user3 = R.drawable.gone;
	int user4 = R.drawable.yao;
	int[] imageArray = {user1, user2, user3, user4};
	DatabaseHandler db;
	int index = 0;
	
	CustomerRecord cr;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_customer_record, container, false);
		return v;	
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		//TextViews
		name = (TextView) getActivity().findViewById(R.id.frag1_name);
		custId = (TextView) getActivity().findViewById(R.id.frag1_custId);
		accId = (TextView) getActivity().findViewById(R.id.frag1_accountId);
		
		//Buttons
		previous = (Button) getActivity().findViewById(R.id.frag1_button_previous);
		previous.setOnClickListener(new OnClick());
		
		transaction = (Button) getActivity().findViewById(R.id.frag1_button_transaction);
		transaction.setOnClickListener(new OnClick());
		
		back = (Button) getActivity().findViewById(R.id.frag1_button_back);
		back .setOnClickListener(new OnClick());
		
		next = (Button) getActivity().findViewById(R.id.frag1_button_next);
		next.setOnClickListener(new OnClick());
		
		image = (ImageView) getActivity().findViewById(R.id.frag1_image);
		image.setImageResource(imageArray[index]);
		
		Context context= getActivity();
		db = new DatabaseHandler(context);
		cr = new CustomerRecord();

		//Get from database first user when this fragment launches
		//This calls the method in DatabaseHandler and calls the method in Customer to get the customerID and parses the int to String
		//name.setText(db.getCustomerName(Integer.toString(db.getCustomerObject(1).get_accId())));
		
		//Setting for default customer when opening app
		name.setText(db.getCustomerName("12"));
		//We already know the ID, so no need to query it from the database 
		custId.setText("12");
		accId.setText(db.getCustomerAccountId("12"));
		
	}
	
	public class OnClick implements View.OnClickListener 
	{
		public void onClick(View v) 
		{
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			switch (v.getId()) 
			{
			case R.id.frag1_button_previous:
				
				if (index > 0)
				{
					index--;
					image.setImageResource(imageArray[index]);
					
					name.setText(db.getNameFromArray(index));
					custId.setText(db.getCustIdFromArray(index));
					accId.setText(db.getCustAccIdFromArray(index));
				}
				else
				{
					Toast toast = Toast.makeText(getActivity(), "Already on first", Toast.LENGTH_SHORT);
					toast.show();
				}
				break;
			case R.id.frag1_button_transaction:
				Bundle arguments = new Bundle();
				arguments.putString("accId", accId.getText().toString());
				fragmentManager = getFragmentManager();
				fragmentTransaction = fragmentManager.beginTransaction();
				//Switch to FragmentMain when app is launched
				TransactionWindow trans = new TransactionWindow();
				trans.setArguments(arguments);
				//fragmentTransaction.replace(android.R.id.content, customerMenu);
				fragmentTransaction.replace(android.R.id.content, trans);
				
				//Add to the back stack
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
				break;
			case R.id.frag1_button_back:
				fragmentManager = getFragmentManager();
				fragmentTransaction = fragmentManager.beginTransaction();
				CustomerMenu customerMenu = new CustomerMenu();
				fragmentTransaction.replace(android.R.id.content, customerMenu);
				
				//Add to the back stack
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
				break;
			case R.id.frag1_button_next:
				if (index < 3)
				{
					index++;
					image.setImageResource(imageArray[index]);
					
					name.setText(db.getNameFromArray(index));
					custId.setText(db.getCustIdFromArray(index));
					accId.setText(db.getCustAccIdFromArray(index));
				}
				else
				{
					Toast toast = Toast.makeText(getActivity(), "Already on last", Toast.LENGTH_SHORT);
					toast.show();
				}
				break;
			}
		}
	}
	
	//Used in TransactionWindow, to determine for what customer the transaciton window is for
	int getIndex()
	{
		return index;
	}
	
	
			

}
