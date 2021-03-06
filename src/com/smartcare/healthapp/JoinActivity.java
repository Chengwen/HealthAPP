package com.smartcare.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class JoinActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.join); 
		
		 findViewById(R.id.continue1).setOnClickListener( new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                //Inform the user the button has been clicked
	            	Intent intent = new Intent();
			        intent.setClass(JoinActivity.this, AboutActivity.class);
			        startActivity(intent);
			        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);   
	            }
	        });
        
	}


}
