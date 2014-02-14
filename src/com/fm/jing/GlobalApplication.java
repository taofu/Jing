package com.fm.jing;

import android.app.Application;

public class GlobalApplication extends Application{
	
	private int mScreentWidth;
	private int mScreentHeight;
	
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		
	}
	
	
	public void initScreentSize(int w,int h){
		this.mScreentWidth = w;
		this.mScreentHeight = h;
		
	}


	public int getmScreentWidth() {
		return mScreentWidth;
	}


	public void setmScreentWidth(int mScreentWidth) {
		this.mScreentWidth = mScreentWidth;
	}


	public int getmScreentHeight() {
		return mScreentHeight;
	}


	public void setmScreentHeight(int mScreentHeight) {
		this.mScreentHeight = mScreentHeight;
	}
}
