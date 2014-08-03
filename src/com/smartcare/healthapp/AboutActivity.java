package com.smartcare.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class AboutActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.about); 
		
		 /*findViewById(R.id.continue1).setOnClickListener( new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                //Inform the user the button has been clicked
	            	Intent intent = new Intent();
			        intent.setClass(JoinActivity.this, AboutActivity.class);
			        startActivity(intent);
			        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);   
	            }
	        });*/
        
	}

}
