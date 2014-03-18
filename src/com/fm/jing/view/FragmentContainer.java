package com.fm.jing.view;

import com.fm.jing.fragment.BaseFragment;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

public class FragmentContainer extends FrameLayout{

	public static final String TAG = "FragmentContainer";
	
	private BaseFragment mCurrentFragment;
	
	private float mLastDownX,mLastDownY;

	private int mTouchSlop;
	public FragmentContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public FragmentContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop =ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(getContext()));
	}

	public FragmentContainer(Context context) {
		super(context);
	}

	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		Log.i(TAG, "onInterceptTouchEvent = "+ ev.getAction());
		float x = ev.getX();
		float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastDownX = x;
			mLastDownY = y;
			break;

		case MotionEvent.ACTION_MOVE:
			if(Math.abs(x - mLastDownX)  > mTouchSlop){
				return true;
			}
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i(TAG, "onTouchEvent = "+ event.getAction());
		int action = event.getAction();
		
		float x = event.getX();
		float y = event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastDownX = x;
			mLastDownY = y;
			break;

		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			
			if(x - mLastDownX > 120){
				mCurrentFragment.onBack();
			}
			break;
		}
		return true;
	}

	public void setmCurrentFragment(BaseFragment mCurrentFragment) {
		this.mCurrentFragment = mCurrentFragment;
	}
	
	
}
