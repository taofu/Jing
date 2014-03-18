package com.fm.jing.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.fm.jing.R;

public class MessageFragment extends BaseFragment{

	public static int index = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_message, null);
		if(savedInstanceState == null){
			BaseFragment fragment = (BaseFragment) getChildFragmentManager().findFragmentByTag(0+"");
			if(fragment == null){
			FragmentTransaction f = getChildFragmentManager().beginTransaction();
			MessageFragmentChild fragmentChild = new MessageFragmentChild();
			Bundle bundle  = new Bundle();
			bundle.putInt("index", index );
			bundle.putInt("color",getColor());
			fragmentChild.setArguments(bundle);
			f.add(R.id.fragment_child_container_message, fragmentChild,index +"");
			f.commit();
			index ++;
			}
		}
		v.findViewById(R.id.start_next_child).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction f = getChildFragmentManager().beginTransaction();
				f.setCustomAnimations(R.anim.sns_push_right_in, R.anim.sns_push_left_out, R.anim.sns_push_left_in, R.anim.sns_push_right_out);
				MessageFragmentChild fragmentChild = new MessageFragmentChild();
				Bundle bundle  = new Bundle();
				bundle.putInt("index", index);
				bundle.putInt("color",getColor());
				fragmentChild.setArguments(bundle);
				f.replace(R.id.fragment_child_container_message, fragmentChild,index +"");;
				f.addToBackStack(null);
				f.commit();
				index ++;
			}
		});
		
		return v;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		index = 0;
	}
}
