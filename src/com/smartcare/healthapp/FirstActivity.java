package com.smartcare.healthapp;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.smartcare.database.DataHandler;
import com.smartcare.healthapp.heartrate.HeartRateMonitor;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
	public static FirstActivity fa;
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

		fa=this;
      
		
		DataHandler d=new DataHandler(getApplicationContext());
		d.open();
		
		//d.insertData( 1.1f, 1, "");
		Cursor cursor=d.returnData();

       // looping through all rows and adding to list
       if (cursor!=null && cursor.moveToFirst()) {
           do {
        	   Log.i("i",cursor.getString(1)); 
        	   Log.i("i",cursor.getString(2));
        	   Log.i("i",cursor.getString(3));
        	   Log.i("i",cursor.getString(4));
           } while (cursor.moveToNext());
       }
		
	     // 定义ViewFlipper
        vf = (ViewFlipper) findViewById(R.id.viewFlipper1);

        // 设置切入动画
        vf.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right));
        // 设置切出动画
        vf.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left));

          timer.schedule(task, 0,2750);

        findViewById(R.id.HeartRateButton).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inform the user the button has been clicked
            	Intent intent = new Intent();
		        intent.setClass(FirstActivity.this, HeartRateMonitor.class);
		        startActivity(intent);
		        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);   
            }
        });
        
        findViewById(R.id.ViewResultButton).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inform the user the button has been clicked
            	Intent intent = new Intent();
		        intent.setClass(FirstActivity.this, ChartActivity.class);
		        startActivity(intent);
		        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);   
            }
        });
         

	}


}
