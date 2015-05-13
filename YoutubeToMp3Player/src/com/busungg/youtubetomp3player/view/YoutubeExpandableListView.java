package com.busungg.youtubetomp3player.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class YoutubeExpandableListView extends ExpandableListView {

	public YoutubeExpandableListView(Context context) {
		super(context);
		SettingView(context);
	}
	
	public YoutubeExpandableListView(Context context, AttributeSet attrs){
		super(context, attrs);
		SettingView(context);
	}
	
	public YoutubeExpandableListView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		SettingView(context);
	}
	
	private void SettingView(Context context){
		setGroupIndicator(null);
	}
}
