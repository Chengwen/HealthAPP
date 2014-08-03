package com.smartcare.healthapp;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

public class FirstActivity extends Activity {

    Timer timer = new Timer();  
    ViewFlipper vf;
    int currLayout=0;
    
    int backgroundColorLists[]= {
    		R.drawable.gradient_dark_green,
    		R.drawable.gradient_dark_orange,
    		R.drawable.gradient_dark_pink,
    		R.drawable.gradient_dark_purple,
    		R.drawable.gradient_dark_teal,
    };
    
    Handler handler = new Handler(){  
    	  
        public void handleMessage(Message msg) {  
            switch (msg.what) {      
            case 1:      
            	Log.d("dd","timer");

            	// Switch views
                vf.showNext();
               
                final View l1 = findViewById(R.id.LinearLayoutmain1);
                final View l2 = findViewById(R.id.LinearLayoutmain2);
                
                if(currLayout<4)
                {
                	l2.setBackgroundResource(backgroundColorLists[currLayout]);//set current layer as first layer
                	l2.setVisibility(View.VISIBLE);//set visible
                	l1.setBackgroundResource(backgroundColorLists[currLayout+1]);//set next layer
                    currLayout++;
                }
                else //==4
                {	
                	l2.setBackgroundResource(backgroundColorLists[currLayout]);//set current layer as first layer
                	l2.setVisibility(View.VISIBLE);//set visible
                	l1.setBackgroundResource(backgroundColorLists[0]);//set next layer
                	currLayout=0;
                }
           
                 
                final Animation fadeOut = AnimationUtils.loadAnimation(FirstActivity.this,R.anim.fade_out);
                final Animation fadeIn = AnimationUtils.loadAnimation(FirstActivity.this,R.anim.fade_in);

                fadeOut.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        l2.setVisibility(View.GONE);
                    }
                });
                l2.startAnimation(fadeOut);
                l1.setVisibility(View.VISIBLE);
                l1.startAnimation(fadeIn);
    
            	  
                break;      
            }      
            super.handleMessage(msg);  
        }  
          
    };  
    
    
    TimerTask task = new TimerTask(){  
        public void run() {  
        	Log.d("dd","timer2");
            Message message = new Message();      
            message.what = 1;  
            handler.sendMessage(message);    
        }
    };
          
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first_page); 
		
	     // 定义ViewFlipper
        vf = (ViewFlipper) findViewById(R.id.viewFlipper1);

        // 设置切入动画
        vf.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right));
        // 设置切出动画
        vf.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left));

         
        timer.schedule(task, 0,2500);

        findViewById(R.id.join).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inform the user the button has been clicked
            	Intent intent = new Intent();
		        intent.setClass(FirstActivity.this, JoinActivity.class);
		        startActivity(intent);
		        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);   
            }
        });
         

        /*
		setContentView(R.layout.join); 
		Button button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {  
		    @Override
		    public void onClick(View v) {
		        Intent intent = new Intent();
		        intent.setClass(MainActivity.this, PageBActivity.class);
		        startActivity(intent);
		        //设置切换动画，从右边进入，左边退出
		        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);                
		    }
		});
        */
        
	}


}
