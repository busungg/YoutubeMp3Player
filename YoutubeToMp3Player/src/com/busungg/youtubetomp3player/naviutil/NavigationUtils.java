package com.busungg.youtubetomp3player.naviutil;

import com.busungg.youtubetomp3player.activity.SettingActivity;
import com.busungg.youtubetomp3player.activity.YoutubeListActivity;
import com.busungg.youtubetomp3player.activity.YoutubePlayerActivity;
import com.busungg.youtubetomp3player.model.YoutubeListModel;

import android.content.Context;
import android.content.Intent;

public class NavigationUtils {
	
	public static void goYoutubePlayerActivity(Context context, YoutubeListModel.ListItem model){
		Intent intent = new Intent(context, YoutubePlayerActivity.class);
		intent.putExtra("id", model.id);
		intent.putExtra("category", model.category);
		
		String title = model.title;
		String replaceTitle = null;
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
	    replaceTitle = title.replaceAll(match, " ");
	    replaceTitle = replaceTitle.replace(" ", "");
	    
	    intent.putExtra("title", replaceTitle);
		
		context.startActivity(intent);
	}
	
	public static void goYoutubeListActivity(Context context){
		Intent intent = new Intent(context, YoutubeListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
	
	public static void goSettingActivity(Context context){
		Intent intent = new Intent(context, SettingActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
}
