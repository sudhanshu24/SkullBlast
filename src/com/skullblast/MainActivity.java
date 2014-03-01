package com.skullblast;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	//----------------variables-----------------
	private static final String TAG = MainActivity.class.getSimpleName();
	
	//----------------methods-------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//requesting to turn the title off
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//making game full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//setContentView(R.layout.activity_main);
		//setting MainGamePanel as the view instead of R
		setContentView(new MainGamePanel(this));
		Log.d(TAG, "View added.");
	}
	
	@Override
	protected void onDestroy(){
		Log.d(TAG, "Destroying..");
		super.onDestroy();
	}
	
	@Override
	protected void onStop(){
		Log.d(TAG, "Stopping..");
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
