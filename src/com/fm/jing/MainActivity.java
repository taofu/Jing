package com.fm.jing;

import com.fm.jing.view.TouchScorllerView;

import android.os.Bundle;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private GlobalApplication mApplication;
	private TouchScorllerView mScorllerView;
	private ListView mLeftToolsView;
	private Button mScroll;
	private boolean isRight = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = (GlobalApplication) getApplication();
		initScreenSize();
		setContentView(R.layout.activity_main);
		mScorllerView = (TouchScorllerView) findViewById(R.id.touchSrollerView);
		
		mScroll = (Button) findViewById(R.id.scroll);
		
		mScroll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isRight){
					mScorllerView.smoothScrollToLeft();
					isRight = false;
				}else{
					AnimatorSet animator =  (AnimatorSet)AnimatorInflater.loadAnimator(MainActivity.this, R.anim.sns_push_right_out);
					animator.setTarget(mScorllerView.getChildAt(0));
					animator.setDuration(300);
					//animator.start();
				mScorllerView.smoothScrollToRight();
					isRight = true;
				}
				
			}
		});
		
		
		
	}

	
	
	private void initScreenSize(){
		 DisplayMetrics dm = new DisplayMetrics();
	     getWindowManager().getDefaultDisplay().getMetrics(dm);
	     mApplication.initScreentSize(dm.widthPixels, dm.heightPixels);
	}
	
	private void initView(){
		
	}
}
