package com.fm.jing.view;

import com.fm.jing.GlobalApplication;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class TouchScorllerView extends RelativeLayout {
	
	private Scroller mScroller;
	private GlobalApplication mApplication;
	private Activity mContext;

	private int mLeftEdgeLimit = 0;
	private int mRightEdgeLimit = 0;
	private int mTouchSlop;
	private float mLastDownMotionX;
	private float mLastDownMotionY;
	private float mLastMotionX;
	private int mScreenW, mScreenH;

	private boolean isBeginDrag = false;
	private boolean isChildEnableCache = false;

	public TouchScorllerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public TouchScorllerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public TouchScorllerView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.mContext = (Activity) context;
		mApplication = (GlobalApplication) mContext.getApplication();
		mScreenW = mApplication.getmScreentWidth();
		mScreenH = mApplication.getmScreentHeight();
		mScroller = new Scroller(getContext());
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		mLeftEdgeLimit = (int) (mScreenW * 0.8f);
		mRightEdgeLimit = -mLeftEdgeLimit;
	}

	
	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();

		float x = event.getX();
		float y = event.getY();
		
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			isBeginDrag = false;
			if (mScroller != null) {
				if (!mScroller.isFinished()) {
					mScroller.abortAnimation();
					isBeginDrag = false;
				}
			}
			mLastDownMotionX = x;
			mLastDownMotionY = y;
			mLastMotionX = x;
			Log.i("Futao", "action down = x " + x);
			break;
		case MotionEvent.ACTION_MOVE:
			if(!isBeginDrag){
				int detaX = (int) (mLastDownMotionX - x);
				int detay = (int) (mLastDownMotionY - y);
				if( (Math.abs(detaX) > Math.abs(detay))){
					isBeginDrag = true;
				}
			}
			if(isBeginDrag){
				int scrollX = (int) (mLastMotionX - x) *2/3;
				int lastFinalX = getScrollX();
				Log.i("Futao", "scrollx = " + scrollX + "mLastMotionX = " + mLastMotionX +   " x = " + x  );
				mLastMotionX = x;
				if(lastFinalX == 0 && scrollX > 0 || (lastFinalX + scrollX) >=0){
					isBeginDrag = false;
					break;
				}
				scrollBy(scrollX, 0);
			}
			
			break;
		case MotionEvent.ACTION_UP:
			Log.i("Futao", "up");
			if(isBeginDrag){
			int dx = 0;
				int oldScroll = getScrollX();
				if(mLastDownMotionX -x > 0){ // Ïò×ó»¬¶¯
					if(oldScroll > mRightEdgeLimit *2/3){
						dx = -oldScroll;
					}else{
						dx = mRightEdgeLimit - oldScroll;
					}
				}else{
					if (oldScroll > mRightEdgeLimit / 3){
						dx = -oldScroll;
					}else{
						dx = mRightEdgeLimit - oldScroll;
					}
				}
//				if (oldScroll > mRightEdgeLimit / 3 || oldScroll > mRightEdgeLimit *2/3) { // scroll to left
//					dx = -oldScroll;
//				} else {
//					dx = mRightEdgeLimit - oldScroll;
//				}
				if(dx != 0)
				smoothScrollTo(dx);
				isBeginDrag = false;
			}
			break;
		}
		return true;
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			invalidate();
		} else {
			clearChildrenCache();
		}
	}
	@Override
	public void scrollTo(int x, int y) {
		enableChildrenCache();
		super.scrollTo(x, y);
	}
	public void smoothScrollToLeft() {
		smoothScrollTo(mLeftEdgeLimit);
	}
	public void smoothScrollTo(int dx) {
		if (!mScroller.isFinished())
			return;
		enableChildrenCache();
		mScroller.startScroll(getScrollX(), 0, dx, 0, 250);
		invalidate();
	}

	public void smoothScrollToRight() {
		smoothScrollTo(mRightEdgeLimit);
	}

	private void clearChildrenCache() {
		if (!isChildEnableCache)
			return;
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View layout = (View) getChildAt(i);
			layout.setDrawingCacheEnabled(false);
		}
		isChildEnableCache = false;
	}

	private void enableChildrenCache() {
		if (isChildEnableCache)
			return;
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View layout = (View) getChildAt(i);
			layout.setDrawingCacheEnabled(true);
		}
		isChildEnableCache = true;
	}

	public int getmLeftEdgeLimit() {
		return mLeftEdgeLimit;
	}

	public void setmLeftEdgeLimit(int mLeftEdgeLimit) {
		this.mLeftEdgeLimit = mLeftEdgeLimit;
	}

	public int getmRightEdgeLimit() {
		return mRightEdgeLimit;
	}

	public void setmRightEdgeLimit(int mRightEdgeLimit) {
		this.mRightEdgeLimit = mRightEdgeLimit;
	}

}
