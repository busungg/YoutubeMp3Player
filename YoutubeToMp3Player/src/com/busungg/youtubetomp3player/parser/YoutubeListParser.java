package com.busungg.youtubetomp3player.parser;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.busungg.youtubetomp3player.model.YoutubeListModel;

public class YoutubeListParser extends BaseParser{

	private YoutubeListModel model = null;
	private YoutubeListModel.ListItem listModel = null;
	
	@Override
	public void parseJsonListData(JSONArray array) {
		
		if(array == null){
			return;
		}
		
		try {
			for(int i = 0; i < array.length(); i++){
				listModel = new YoutubeListModel.ListItem();
				
				listModel.id = array.getJSONObject(i).optString("id");
				listModel.category = array.getJSONObject(i).optString("category");
				listModel.thumbnail = array.getJSONObject(i).getJSONObject("thumbnail").optString("sqDefault");
				listModel.title = array.getJSONObject(i).optString("title");
				listModel.viewCount = array.getJSONObject(i).optInt("viewCount");
				listModel.uploaded = array.getJSONObject(i).optString("uploaded");
				
				mList.add(listModel);
			}
		} catch (Exception e) {
			Log.e("Error", e.toString());
		}
	}

	@Override
	public void parseJsonData() {
		
		if(mJsonObject == null){
			return;
		}

		model = new YoutubeListModel();
		
		try {
			model.startIndex = mJsonObject.getJSONObject("data").optInt("startIndex");
			parseJsonListData(mJsonObject.getJSONObject("data").getJSONArray("items"));
		} catch (JSONException e) {
			Log.e("Error", e.getMessage());
		}
		
		mModel = model;
	}

}
