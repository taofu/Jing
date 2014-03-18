package com.fm.jing.view;

import com.fm.jing.FMType;
import com.fm.jing.MainActivity;
import com.fm.jing.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class LeftMenuView extends RelativeLayout{
	
	private static int MESSAGE_ON_TOUCH = 0x100;
	LinearLayout mMenuGroup;
	TextView mExplore;
	TextView mILike;
	TextView mMessage;
	Rect rect = new Rect();
	private View mLastSelectMenu;
	private OnItemClickCallBack mCallBack;
	private View mOnTouchView;
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			if(mOnTouchView != null){
				mOnTouchView.setBackgroundColor(Color.GRAY);
			}
		};
	};
	
	public interface OnItemClickCallBack{
		void onClick(FMType fmType);
	}
	public LeftMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public LeftMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public LeftMenuView(Context context) {
		super(context);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("Futao", "Menu ontouch = " + event.getAction() +"");
		return super.onTouchEvent(event);
	}
	
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mMenuGroup = (LinearLayout) findViewById(R.id.left_menu_parent);
		mExplore = (TextView) findViewById(R.id.left_menu_explore);
		mILike = (TextView) findViewById(R.id.left_menu_i_like);
		mMessage = (TextView) findViewById(R.id.left_menu_message);
		
		mExplore.setBackgroundColor(Color.LTGRAY);
		mLastSelectMenu = mExplore;
		View v;
		for(int i = 0,count = mMenuGroup.getChildCount();i< count ;i++){
			v = mMenuGroup.getChildAt(i);
			v.setOnTouchListener(mItemOnTouchListener);
		}
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}
	
	
	private void updateMenuBackground(View selectView){
		 
		View v;
		for(int i = 0,count = mMenuGroup.getChildCount();i< count ;i++){
			v = mMenuGroup.getChildAt(i);
			if(v == selectView){
				v.setBackgroundColor(Color.LTGRAY);
			}else{
				v.setBackgroundColor(getResources().getColor(R.color.left_menu_itme_bg));
			}
		}
	}
	
	OnClickListener mMenuOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			FMType mType = null;
			switch (v.getId()) {
			case R.id.left_menu_explore:
				mType = FMType.EXPLORE;
				break;
			case R.id.left_menu_i_like:
				mType = FMType.ILIKE;
				break;
			case R.id.left_menu_message:
				mType = FMType.MESSAGE;
				break;
			}
			updateMenuBackground(v);
			if(mCallBack != null && mType != null){
				mCallBack.onClick(mType);
				mLastSelectMenu  = v;
			}
		}
	};
	OnTouchListener mItemOnTouchListener = new OnTouchListener() {
		boolean isOnClick;
		float lastX;
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Log.i("Futao", "onTouch " + event.getAction());
			int action  = event.getAction();
			float x = event.getX();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				lastX = x;
				isOnClick = true;
				if(v != mLastSelectMenu)
					handler.sendEmptyMessageDelayed(MESSAGE_ON_TOUCH, 400);
				mOnTouchView = v;
				//v.setBackgroundColor(Color.GRAY);
				break;
			case MotionEvent.ACTION_MOVE:
				if(Math.abs(x - lastX) > mTouchSlop){
					isOnClick = false;
					if(v != mLastSelectMenu){
						//handler.removeMessages(MESSAGE_ON_TOUCH);
						v.setBackgroundColor(getResources().getColor(R.color.left_menu_itme_bg));
					}
				}else{
					isOnClick = true;
				}
				break;
			case MotionEvent.ACTION_UP:
				if(isOnClick){
					handler.removeMessages(MESSAGE_ON_TOUCH);
					mMenuOnClickListener.onClick(v);
				}
				break;
			case MotionEvent.ACTION_CANCEL:
				isOnClick = false;
				if(v != mLastSelectMenu){
					v.setBackgroundColor(getResources().getColor(R.color.left_menu_itme_bg));
					handler.removeMessages(MESSAGE_ON_TOUCH);
				}
				break;
			}
			return true;
		}
	};
	private int mTouchSlop;
	public void setmCallBack(OnItemClickCallBack mCallBack) {
		this.mCallBack = mCallBack;
	}
}
