package com.fm.jing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fm.jing.fragment.BaseFragment;
import com.fm.jing.fragment.ExploreFragment;
import com.fm.jing.fragment.ILikeFragment;
import com.fm.jing.fragment.MessageFragment;
import com.fm.jing.view.LeftMenuView;
import com.fm.jing.view.LeftMenuView.OnItemClickCallBack;
import com.fm.jing.view.TouchScorllerView;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {

	public static final int FRAGMENT_EXPLORE = 0x1000;

	private GlobalApplication mApplication;
	private TouchScorllerView mScorllerView;
	private LeftMenuView mMenuView;
	private Button mScroll;
	private ListView mLeftList;

	private FragmentManager mFManager;

	private FMType mCurrentFragment;
	
	public static boolean isAllowAnimation ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = (GlobalApplication) getApplication();
		initApplicationParams();
		setContentView(R.layout.activity_main);
		initView();
		showFragment(FMType.EXPLORE);
	}

	private void initApplicationParams() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mApplication.initScreentSize(dm.widthPixels, dm.heightPixels);

		mFManager = getSupportFragmentManager();

	}

	private void initView() {
		mScorllerView = (TouchScorllerView) findViewById(R.id.touchSrollerView);
		mMenuView = (LeftMenuView) findViewById(R.id.leftMenuView);
		mScroll = (Button) findViewById(R.id.scroll);

		mScorllerView.setLeftMenu(mMenuView,
				TouchScorllerView.STYLE_LEFT_MENU_SCROLL_SCALE);
		mScroll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mScorllerView.smoothScrollToShowOrHideMenu();
			}
		});
		mMenuView.setmCallBack(new OnItemClickCallBack() {

			@Override
			public void onClick(final FMType fmType) {
				if (fmType != null) {
					mScorllerView.postDelayed(new Runnable() {

						@Override
						public void run() {
							showFragment(fmType);
						}
					}, 200);
				}
			}
		});
		mLeftList = (ListView) findViewById(R.id.left_list);
		String arr[] = new String[] { "Item1", "Item1", "Item1", "Item1",
				"Item1", "Item1", "Item1", "Item1", "Item1", "Item1", "Item1" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arr);
		mLeftList.setAdapter(adapter);

		mLeftList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MainActivity.this, position + "", 1).show();
			}
		});
	}

	public void showFragment(FMType fmType) {
		isAllowAnimation = false;
		if (mCurrentFragment == fmType)
			return;
		BaseFragment fragment = null;
		final FragmentTransaction transaction = mFManager.beginTransaction();
		if (mCurrentFragment != null)
			transaction.detach(mFManager
					.findFragmentByTag(mCurrentFragment.tag));

		switch (fmType) {
		case EXPLORE:
			fragment = (BaseFragment) mFManager.findFragmentByTag(fmType.tag);
			if (fragment == null) {
				fragment = new ExploreFragment();
				transaction.add(R.id.fragment_container, fragment, fmType.tag);
			} else {
				transaction.attach(fragment);
			}
			break;
		case ILIKE:
			fragment = (BaseFragment) mFManager.findFragmentByTag(fmType.tag);
			if (fragment == null) {
				fragment = new ILikeFragment();
				transaction.add(R.id.fragment_container, fragment, fmType.tag);
			} else {
				transaction.attach(fragment);
			}
			break;
		case MESSAGE:
			fragment = (BaseFragment) mFManager.findFragmentByTag(fmType.tag);
			if (fragment == null) {
				fragment = new MessageFragment();
				transaction.add(R.id.fragment_container, fragment, fmType.tag);
			} else {
				transaction.attach(fragment);
			}
			break;
		}
		fragment.mActionEvent = BaseFragment.ACTION_MENU;
		transaction.commit();
		if (mCurrentFragment != null){
			mScorllerView.smoothScrollToShowOrHideMenu();
		}
		mCurrentFragment = fmType;
		mScorllerView.setmCurrentFragment(fragment);

	}

	public void showAnimation(final FragmentTransaction transaction){
		TranslateAnimation animation = new TranslateAnimation(0, 300, 0, 0);
		animation.setRepeatMode(Animation.REVERSE);
		animation.setRepeatCount(1);
		animation.setDuration(500);
		mScorllerView.setAnimation(animation);
		animation.start();
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				mScorllerView.clearAnimation();
			}
		});
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "Scale");
		menu.add(0, 2, 1, "Horizontal");
		menu.add(0, 3, 2, "Vertical");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			mScorllerView
					.setLeftMenuScrollStyle(TouchScorllerView.STYLE_LEFT_MENU_SCROLL_SCALE);
		} else if (item.getItemId() == 2) {
			mScorllerView
					.setLeftMenuScrollStyle(TouchScorllerView.STYLE_LEFT_MENU_SCROLL_HORIZONTAL);
		} else {
			mScorllerView
					.setLeftMenuScrollStyle(TouchScorllerView.STYLE_LEFT_MENU_SCROLL_VERTICAL);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(mScorllerView.getScrollX() != mScorllerView.mOverViewWidth){
				finish();
				return true;
			}
			BaseFragment fragment = (BaseFragment) mFManager.findFragmentByTag(mCurrentFragment.tag);
			if(fragment.onBack()){
				return true;
			}else{
				if (mScorllerView.getScrollX() ==  mScorllerView.mOverViewWidth) {
					mScorllerView.smoothScrollToShowOrHideMenu();
					return true;
				}else{
					finish();
					return true;
				}
			}
			/*if (mFManager.getBackStackEntryCount() > 0) {
				mFManager.popBackStackImmediate();
				return true;
			} else {
				if (mScorllerView.getScrollX() == 0) {
					mScorllerView.smoothScrollToShowOrHideMenu();
					return true;
				}
			}*/
			
		}
		return super.onKeyDown(keyCode, event);
	}
}
