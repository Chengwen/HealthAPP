package com.smartcare.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PageBActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pageb); 

		Button button = (Button)findViewById(R.id.button2);
		button.setOnClickListener(new View.OnClickListener() {  
		    @Override
		    public void onClick(View v) {
		        Intent intent = new Intent();
		        intent.setClass(PageBActivity.this, MainActivity.class);
		        startActivity(intent);
		        //设置切换动画，从右边进入，左边退出
		        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);                
		    }
		});
	}



}
