package com.busungg.youtubetomp3player.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.busungg.youtubetomp3player.R;
import com.busungg.youtubetomp3player.model.YoutubeListModel;
import com.busungg.youtubetomp3player.naviutil.NavigationUtils;

public class YoutubeListView extends PullToRefreshView implements OnItemClickListener{

	public YoutubeListView(Context context){
		super(context);
		mContext = context;
		SettingView(context);
	}
	
	public YoutubeListView(Context context, AttributeSet attrs){
		super(context, attrs);
		mContext = context;
		SettingView(context);
	}
	
	public YoutubeListView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		mContext = context;
		SettingView(context);
	}
	
	private void SettingView(Context context){
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		footerView = inflater.inflate(R.layout.youtube_list_footer_layout, null);
		alertRefresh(false);
		addFooterView(footerView, null, false);
		
		setOnItemClickListener(this);
		setOnScrollListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		if(view.equals(footerView)){
			return;
		}
		
		NavigationUtils.goYoutubePlayerActivity(mContext, ((YoutubeListModel.ListItem)getAdapter().getItem(position)));
	}
}
