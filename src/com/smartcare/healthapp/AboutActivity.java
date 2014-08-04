package com.smartcare.healthapp;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
public class AboutActivity extends Activity{
	int mYear,mMonth,mDay;
	DatePickerDialog datePicker;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.about); 

        mYear = 1990;
        mMonth = 5;
        mDay = 1;
		datePicker = new DatePickerDialog(AboutActivity.this, null,mYear, mMonth, mDay);

		datePicker.setTitle(R.string.textView_birthday);   
		findViewById(R.id.birthday).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				datePicker.show();
			}
		});
		
		
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
