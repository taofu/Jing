package com.fm.jing.fragment;

import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fm.jing.R;

public class MessageFragmentChild extends BaseFragment{

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_message_child, null);
		TextView textView = (TextView) view.findViewById(R.id.child_title);
		Bundle bundle = getArguments();
		
		if(bundle != null){
			int index = bundle.getInt("index");
			int color = bundle.getInt("color");
			view.setBackgroundColor(color);
			textView.setText("message child fragment " + index);
		}
		
		return view;
	}
	
}
