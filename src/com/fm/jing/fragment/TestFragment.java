package com.fm.jing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fm.jing.R;

public class TestFragment extends BaseFragment{

	private TextView textView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_test, null);
		textView = (TextView) v.findViewById(R.id.tv_test);
		String tv = getArguments().getString("tv");
		textView.setText(tv);
		return v;
	}
	
	
	
}
