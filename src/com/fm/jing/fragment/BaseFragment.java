package com.fm.jing.fragment;

import java.util.Random;

import com.fm.jing.GlobalApplication;
import com.fm.jing.MainActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment{
	
	public static final int ACTION_MENU = 1;
	public static final int ACTION_SELF = 2;
	public static final int ACTION_BACK = 3;
	public int mActionEvent;
	protected MainActivity mActivity;
	protected GlobalApplication mApplication;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = (MainActivity) activity;
		mApplication = (GlobalApplication) mActivity.getApplication();
	}
	
	
	public void startChildFragment(int type){
		mActionEvent = ACTION_SELF;
	}

	public boolean  onBack() {
		if(getChildFragmentCount() >0){
			mActionEvent = ACTION_BACK;
			getChildFragmentManager().popBackStack();
			return true;
		}
		return false;
	}
	public int getChildFragmentCount(){
		return getChildFragmentManager().getBackStackEntryCount();
	}
	public int getColor(){
		int r = ((new Random().nextInt(128) )+ 128);
		int g =  ((new Random().nextInt(128) )+ 128);
		int b =  ((new Random().nextInt(128) )+ 128);
		int color = Color.rgb(r, g, b);
		return color;
	}
}
