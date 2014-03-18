package com.fm.jing.fragment;

import android.support.v4.app.Fragment;
import android.view.animation.Animation;

public class ChildFragment extends Fragment{
	
	protected BaseFragment mParent;

	public void setmParent(BaseFragment mParent) {
		this.mParent = mParent;
	}
	
	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		
		if(mParent != null && mParent.mActionEvent == BaseFragment.ACTION_MENU){
			Animation animation = new Animation() {
			};
			animation.setDuration(0);
			return animation;
		}
		return super.onCreateAnimation(transit, enter, nextAnim);
	}
}
