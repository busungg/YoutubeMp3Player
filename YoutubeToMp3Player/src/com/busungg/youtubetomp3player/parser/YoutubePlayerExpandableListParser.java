package com.busungg.youtubetomp3player.parser;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.busungg.youtubetomp3player.model.YoutubeDescModel;

public class YoutubePlayerExpandableListParser extends BaseExpandableParser{
	
	private YoutubeDescModel.GroupModel groupModel = null;
	private YoutubeDescModel.ChildCategory categoryModel = null;
	private YoutubeDescModel.ChildDescription descriptionModel = null;
	
	@Override
	public void parseJsonListData(JSONArray array) {}

	@Override
	public void parseJsonData() {
		
		if(mJsonObject == null){
			return;
		}
		
		groupModel = new YoutubeDescModel.GroupModel();
		categoryModel = new YoutubeDescModel.ChildCategory();
		descriptionModel = new YoutubeDescModel.ChildDescription();
		
		try {
			groupModel.title = mJsonObject.getJSONObject("data").optString("title");
			groupModel.uploaded = mJsonObject.getJSONObject("data").optString("uploaded");
			
			categoryModel.category = mJsonObject.getJSONObject("data").optString("category");
			categoryModel.player = mJsonObject.getJSONObject("data").getJSONObject("player").optString("default");
			
			descriptionModel.description = mJsonObject.getJSONObject("data").optString("description");
			
			mGroupList.add(groupModel);
			
			mChildList.add(categoryModel);
			mChildList.add(descriptionModel);
			
			mChildListList.add(mChildList);
			
		} catch (JSONException e) {
			Log.e("Error", e.getMessage());
		}
	}

}
