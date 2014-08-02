package com.smartcare.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
		setContentView(R.layout.main); 
		
	     // ����ViewFlipper
        final ViewFlipper vf = (ViewFlipper) findViewById(R.id.viewFlipper1);

        // ���õ��������
        vf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        // �����ȡ��һ����ͼԪ��
                        vf.showNext();
                        

                        final View l1 = findViewById(R.id.LinearLayoutmain3);
                        final View l2 = findViewById(R.id.LinearLayoutmain2);

                        final Animation fadeOut = AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_out);
                        final Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_in);

                                fadeOut.setAnimationListener(new AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        l1.setVisibility(View.GONE);
                                    }
                                });
                                l1.startAnimation(fadeOut);
                                l2.setVisibility(View.VISIBLE);
                                l2.startAnimation(fadeIn);
                }
        });

        // �������붯��
        vf.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right));
        // �����г�����
        vf.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left));

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
