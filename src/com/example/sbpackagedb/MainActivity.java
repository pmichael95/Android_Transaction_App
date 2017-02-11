package com.example.sbpackagedb;

import android.os.Bundle;
import android.app.Activity;
//import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		//Switch to FragmentMain when app is launched
		CustomerMenu customerMenu = new CustomerMenu();
		//fragmentTransaction.replace(android.R.id.content, customerMenu);
		fragmentTransaction.replace(android.R.id.content, customerMenu);
		
		//Add to the back stack
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
