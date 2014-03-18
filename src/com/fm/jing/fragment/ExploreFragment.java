package com.fm.jing.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.fm.jing.MainActivity;
import com.fm.jing.R;
import com.fm.jing.view.FragmentContainer;

public class ExploreFragment extends BaseFragment{
	public static int index = 0;
	private FragmentContainer mContainer;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_explore, null);
		mContainer = (FragmentContainer) v.findViewById(R.id.fragment_child_container_explore);
		mContainer.setmCurrentFragment(this);
		if(savedInstanceState == null){
			if(index == 0){
				startChildFragment(index);
			}
		}
		return v;
	}
	
	
	public void startChildFragment(int type ){
		FragmentTransaction f = getChildFragmentManager().beginTransaction();
		f.setCustomAnimations(R.anim.sns_push_right_in, R.anim.sns_push_left_out, R.anim.sns_push_left_in, R.anim.sns_push_right_out);
		ExploreFragmentChild fragmentChild = new ExploreFragmentChild();
		fragmentChild.setmParent(this);
		Bundle bundle  = new Bundle();
		bundle.putInt("index", index );
		bundle.putInt("color",getColor());
		fragmentChild.setArguments(bundle);
		f.replace(R.id.fragment_child_container_explore, fragmentChild);
		if(index != 0){
			mActionEvent = ACTION_SELF;
			MainActivity.isAllowAnimation = true;
			f.addToBackStack(null);
		}
		f.commit();
		index ++;
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		index = 0;
	}
	
	@Override
	public boolean onBack() {
		if(super.onBack()){
			return true;
		}
		return false;
	}
}
