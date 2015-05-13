package com.busungg.youtubetomp3player.view;

import com.busungg.youtubetomp3player.R;
import com.busungg.youtubetomp3player.activity.YoutubeListActivity;
import com.busungg.youtubetomp3player.naviutil.NavigationUtils;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class CheckMenuView extends LinearLayout implements OnClickListener{
	
	private Context mContext = null;
	private LayoutInflater inflater = null;
	
	private View checkMenuRealView = null;
	private Button youtubeListButton = null;
	private Button settingButton = null;
	
	public CheckMenuView(Context context) {
		super(context);
		mContext = context;
		SettingView(context);
	}
	
	public CheckMenuView(Context context, AttributeSet attrs){
		super(context, attrs);
		mContext = context;
		SettingView(context);
	}
	
	public CheckMenuView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		mContext = context;
		SettingView(context);
	}
	
	private void SettingView(Context context){
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		checkMenuRealView = inflater.inflate(R.layout.check_menu_layout, null);
		LinearLayout.LayoutParams prams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		
		addView(checkMenuRealView, prams);
		
		youtubeListButton = (Button)checkMenuRealView.findViewById(R.id.check_menu_youtubelist_button);
		youtubeListButton.setOnClickListener(this);
		
		settingButton = (Button)checkMenuRealView.findViewById(R.id.check_menu_setting_button);
		settingButton.setOnClickListener(this);
		
		if((Activity)context instanceof YoutubeListActivity){
			youtubeListButton.setSelected(true);
			youtubeListButton.setClickable(false);
		} else {
			settingButton.setSelected(true);
			settingButton.setClickable(false);
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.check_menu_youtubelist_button:
			((Activity)mContext).finish();
			break;
			
		case R.id.check_menu_setting_button:
			NavigationUtils.goSettingActivity(mContext);
			break;
		}
	}
}
