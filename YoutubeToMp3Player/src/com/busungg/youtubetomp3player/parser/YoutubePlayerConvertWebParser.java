package com.busungg.youtubetomp3player.parser;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.busungg.youtubetomp3player.model.YoutubePlayerConvertWebModel;

public class YoutubePlayerConvertWebParser extends BaseParser{

	private YoutubePlayerConvertWebModel model = null;
	
	@Override
	public void parseJsonListData(JSONArray array) {
	}

	@Override
	public void parseJsonData() {
		
		if(mJsonObject == null){
			return;
		}

		model = new YoutubePlayerConvertWebModel();
		
		try {
			model.requestResult = mJsonObject.optInt("result");
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
		
		mModel = model;
	}
}
