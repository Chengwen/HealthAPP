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

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
		setContentView(R.layout.join); 
        //�����л����������ұ߽��룬����˳�
            

        /*
		Button button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {  
		    @Override
		    public void onClick(View v) {
		        Intent intent = new Intent();
		        intent.setClass(MainActivity.this, PageBActivity.class);
		        startActivity(intent);
		        //�����л����������ұ߽��룬����˳�
		        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);                
		    }
		});
        */
        
	}


}
