package com.fm.jing.view;

import com.fm.jing.GlobalApplication;
import com.fm.jing.R;
import com.fm.jing.fragment.BaseFragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.Toast;

public class TouchScorllerView extends RelativeLayout {
	
	
	public static final int STYLE_LEFT_MENU_SCROLL_VERTICAL = 0x1;
	public static final int STYLE_LEFT_MENU_SCROLL_HORIZONTAL = 0x2;
	public static final int STYLE_LEFT_MENU_SCROLL_SCALE = 0x3;
	public static final int STYLE_LEFT_MENU_SCROLL_NONE = 0x4;
	
	private static int mLeftScrollStyle;
	private Scroller mScroller;
	private GlobalApplication mApplication;
	private Activity mContext;
	private LeftMenuView mMenuView;
	private View mShadow;
	
	private BaseFragment mCurrentFragment;
	
	private int mLeftEdgeLimit = 0;
	private int mRightEdgeLimit = 0;
	private int mTouchSlop;
	private float mLastDownMotionX;
	private float mLastDownMotionY;
	private float mLastMotionX;
	private int mScreenW, mScreenH;
	private Button mRightButton;
	private Rect RightButtonRect;
	private boolean isBeginDrag = false;
	private boolean isChildEnableCache = false;
	public int mOverViewWidth = 0;
	
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

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(mOverViewWidth == 0){
			mOverViewWidth = mShadow.getLayoutParams().width;
		}
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(mScreenW + mOverViewWidth, MeasureSpec.EXACTLY);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mRightButton = (Button) findViewById(R.id.scroll);
		mShadow = findViewById(R.id.left_shadow);
		mOverViewWidth = mShadow.getLayoutParams().width;
		/*if(mOverViewWidth != 0)
			mLeftEdgeLimit = mLeftEdgeLimit + mOverViewWidth;*/
	}
	private void init(Context context) {
		this.mContext = (Activity) context;
		mApplication = (GlobalApplication) mContext.getApplication();
		mScreenW = mApplication.getmScreentWidth();
		mScreenH = mApplication.getmScreentHeight();
		mScroller = new Scroller(getContext());
		mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(getContext()));
		mLeftEdgeLimit = (int) (mScreenW * 0.8f);
		mRightEdgeLimit = -mLeftEdgeLimit;
		mLeftScrollStyle = STYLE_LEFT_MENU_SCROLL_SCALE;
	}
	boolean isCancel;
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(getScrollX() ==  (mRightEdgeLimit) && ev.getX() < (mLeftEdgeLimit)){
			isCancel = false;
			mMenuView.dispatchTouchEvent(ev);
		}
		else {
			if(!isCancel){
				MotionEvent event = MotionEvent.obtain(ev);
				event.setAction(MotionEvent.ACTION_CANCEL);
				mMenuView.dispatchTouchEvent(event);
				isCancel = true;
			}
		}
		
		return super.dispatchTouchEvent(ev);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		
		float x = ev.getX();
		float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastDownMotionX = x;
			mLastDownMotionY = y;
			mLastMotionX = x;
			if(getScrollX() == mRightEdgeLimit && x > mLeftEdgeLimit && y > 150){
				return true;
			}
			return false;
		case MotionEvent.ACTION_MOVE:
			int detaX = (int)  (mLastDownMotionX - x);
			int adsDetaX = Math.abs(detaX);
			int detay = (int) Math.abs((mLastDownMotionY - y));
			if (adsDetaX > mTouchSlop && (adsDetaX > detay) && !isScrollChildFragment()) {
				return true;
			}
			
			break;
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		Log.i("Futao", "onTouchEvent " + action);
		float x = event.getX();
		float y = event.getY();
		/*if(getScrollX() == mRightEdgeLimit && x < mLeftEdgeLimit){
			return true;
		}*/
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			isBeginDrag = false;
			if (mScroller != null) {
				if (!mScroller.isFinished()) {
					mScroller.forceFinished(true);
					//Log.e("Futao", "forceFinish " + " oldX = " + getScrollX() + " curX = " + mScroller.getCurrX());
					scrollTo(mScroller.getFinalX(), 0);
					isBeginDrag = false;
					break;
				}
			}
			mLastDownMotionX = x;
			mLastDownMotionY = y;
			mLastMotionX = x;
			
			
			break;
		case MotionEvent.ACTION_MOVE:
			if (!isBeginDrag) {
				int detaX = (int)  (mLastDownMotionX - x);
				int adsDetaX = Math.abs(detaX);
				int detay = (int) Math.abs((mLastDownMotionY - y));
				if (adsDetaX > mTouchSlop && (adsDetaX > detay)) {
					if(detaX < 0) {
						mLastMotionX = x -2 ;
					}else{
						mLastMotionX = x + 2;
					}
					isBeginDrag = true;
				}
			}
			if (isBeginDrag) {
				Log.i("Futao", "isBeginDrag = " + isBeginDrag);
				int scrollX = (int) (mLastMotionX - x) * 2 / 3;
				
				int lastFinalX = getScrollX();
				mLastMotionX = x;
				if (lastFinalX == 0 && scrollX > 0|| (lastFinalX + scrollX) >= mOverViewWidth || (lastFinalX + scrollX ) < mRightEdgeLimit) {
					isBeginDrag = false;
					break;
				}

				if (Math.abs(scrollX) > 100)
					break;
				if(scrollX !=0)
					scrollBy(scrollX, 0);
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int oldScroll = getScrollX();
			if (isBeginDrag || oldScroll != mOverViewWidth || oldScroll != mRightEdgeLimit ) {
				int dx = 0;
				float dis = mLastDownMotionX - x;
				if (dis > 0) { // Ïò×ó»¬¶¯
					if (oldScroll > mRightEdgeLimit * 4 /5) {
						dx = -oldScroll + mOverViewWidth;
					} else {
						dx = (mRightEdgeLimit) - oldScroll;
					}
				} else if(dis < 0){
					if (oldScroll > mRightEdgeLimit / 5) {
						dx = -oldScroll+ mOverViewWidth;
					} else {
						dx = (mRightEdgeLimit) - oldScroll;
					}
				}
				if(dx != 0)
					smoothScrollTo(dx,400);
				isBeginDrag = false;
			}
			break;
		}
		return true;
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			int oldX = getScrollX();
			int oldY = getScrollY();
			int x = mScroller.getCurrX();
			int y = mScroller.getCurrY();
			Log.i("Futao", "computeScroll not finish " + x  + " oldX = " + oldX + " finalX = " + mScroller.getFinalX());

			//if (oldX != x || oldY != y) {
				scrollTo(x, y);
			//}

			invalidate();
		} else {
			Log.i("Futao", "computeScroll finish " + mScroller.isFinished() + " x = " + getScrollX());
			/*if(mScroller.getFinalX() != getScrollX()){
				scrollTo(mScroller.getFinalX(), 0);
			}*/
			//clearChildrenCache();
		}
	}

	@Override
	public void scrollBy(int x, int y) {
		//enableChildrenCache();
		super.scrollBy(x, y);
	}
	@Override
	public void scrollTo(int x, int y) {
		//Log.i("Futao", "scrollTo");
		float percentOpen = (float)Math.abs((x)) / ((float)mLeftEdgeLimit);
		if(Build.VERSION.SDK_INT >= 11){
			boolean layer = percentOpen > 0.0f && percentOpen < 1.0f;
			final int layerType = layer ? View.LAYER_TYPE_NONE : View.LAYER_TYPE_NONE;
			if (layerType != getLayerType()) {
				setLayerType(layerType, null);
			}
		}
		if(mLeftScrollStyle == STYLE_LEFT_MENU_SCROLL_SCALE){
			mMenuView.setScaleX(percentOpen);
		}else if(mLeftScrollStyle == STYLE_LEFT_MENU_SCROLL_VERTICAL){
			int scrollY = (int) (mScreenH * percentOpen);
			scrollY = -mScreenH + scrollY;
			mMenuView.scrollTo(0,scrollY);
		}else if(mLeftScrollStyle == STYLE_LEFT_MENU_SCROLL_HORIZONTAL){
			mMenuView.scrollBy((x - getScrollX()) /2, 0);
		}
		mMenuView.setAlpha(percentOpen);
		super.scrollTo(x, y);
	}
	private void smoothScrollToLeft() {
		smoothScrollTo(mLeftEdgeLimit + mOverViewWidth ,400);
	}

	private void smoothScrollTo(int dx ,int time) {
		if (!mScroller.isFinished()){
			Toast.makeText(getContext(), "return", 1).show();	
			return;
		}
		enableChildrenCache();
		mScroller.startScroll(getScrollX(), 0, dx, 0, time);
		invalidate();
	}

	private void smoothScrollToRight() {
		smoothScrollTo(mRightEdgeLimit - mOverViewWidth,600);
	}
	
	public void smoothScrollToShowOrHideMenu(){
		if(getScrollX() == mOverViewWidth ){
			smoothScrollToRight();
		}else{
			smoothScrollToLeft();
		}
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


	public void setLeftMenu(LeftMenuView menuView, int scrollStyle){
		mLeftScrollStyle = scrollStyle;
		this.mMenuView = menuView;
		if(mMenuView == null){
			return;
		}
		if(mLeftScrollStyle == STYLE_LEFT_MENU_SCROLL_HORIZONTAL){
			mMenuView.scrollTo(mLeftEdgeLimit/2, 0);
			mMenuView.setScaleX(1);
		}else if(mLeftScrollStyle == STYLE_LEFT_MENU_SCROLL_SCALE){
			mMenuView.scrollTo(0, 0);
			
		}else if(mLeftScrollStyle == STYLE_LEFT_MENU_SCROLL_VERTICAL){
			mMenuView.scrollTo(0, -mScreenH);
			mMenuView.setPivotY(0);
			mMenuView.setPivotX(0);
			mMenuView.setScaleX(1);
		}
		mMenuView.setPivotX(0);
		mMenuView.setPivotY(mScreenH/2);
		Log.i("Futao", "getScrollX = " + mMenuView.getScrollX() + " getScrollY = " + mMenuView.getScrollY());
	}
	
	public void setLeftMenuScrollStyle(final int scrollStyle){
		if(getScrollX() == mRightEdgeLimit){
			mLeftScrollStyle = scrollStyle;
		}else{
			setLeftMenu(mMenuView, scrollStyle);
		}
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mScroller.startScroll(0, 0, mOverViewWidth, 0);
		//scrollTo(mOverViewWidth, 0);
	}

	public void setmCurrentFragment(BaseFragment mCurrentFragment) {
		this.mCurrentFragment = mCurrentFragment;
	}
	
	private boolean isScrollChildFragment(){
		if(getScrollX() == mOverViewWidth &&mCurrentFragment != null && mCurrentFragment.getChildFragmentCount() > 0)
			return true;
		return false;
	}
}
