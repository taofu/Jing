package com.fm.jing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.fm.jing.MainActivity;
import com.fm.jing.R;

public class ExploreFragmentChild extends ChildFragment{

	private int index ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_child, null);
		TextView textView = (TextView) view.findViewById(R.id.chid_title);
		Bundle bundle = getArguments();
		
		if(bundle != null){
			 index = bundle.getInt("index");
			int color = bundle.getInt("color");
			view.setBackgroundColor(color);
			textView.setText("explore child fragment " + index);
		}
		
		view.findViewById(R.id.start_next_child).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mParent.startChildFragment(11);
			}
		});
		
		ListView mLeftList = (ListView)view. findViewById(R.id.child_list);
		String arr[] = new String[] { "Item1", "Item1", "Item1", "Item1",
				"Item1", "Item1", "Item1", "Item1", "Item1", "Item1", "Item1" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, arr);
		mLeftList.setAdapter(adapter);
		mLeftList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getActivity(), position + "", 1).show();
			}
		});
		return view;
	}
	
	/*@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		
		if(mParent.mActionEvent == BaseFragment.ACTION_MENU){
			Animation animation = new Animation() {
			};
			animation.setDuration(0);
			return animation;
		}
		
		if(index == 0){
			if(enter && !MainActivity.isAllowAnimation){
				Animation animation = new Animation() {
				};
				animation.setDuration(0);
				return animation;
			}else if(!enter){
				Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.sns_push_left_out);
		        return a;
			}else if (enter && MainActivity.isAllowAnimation){
				Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.sns_push_left_in);
		        return a;
			}
		}else if(index == (ExploreFragment.index -1)){
			if(enter && !MainActivity.isAllowAnimation){
				Animation animation = new Animation() {
				};
				animation.setDuration(0);
				return animation;
			}else if(!enter){
				Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.sns_push_right_out);
		        return a;
			}else if (enter && MainActivity.isAllowAnimation){
				Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.sns_push_right_in);
		        return a;
			}
		}
		return super.onCreateAnimation(transit, enter, nextAnim);
	}*/

}
