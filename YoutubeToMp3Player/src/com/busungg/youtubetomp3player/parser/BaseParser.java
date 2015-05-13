package com.busungg.youtubetomp3player.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.busungg.youtubetomp3player.model.BaseModel;

public abstract class BaseParser {

	protected BaseModel mModel = null;
	protected ArrayList<BaseModel> mList = null;
	protected JSONObject mJsonObject = null;
	
	public abstract void parseJsonListData(JSONArray array);
	public abstract void parseJsonData();
	
	public BaseParser(){
		mList = new ArrayList<BaseModel>();
	}
	
	public void setJsonObject(JSONObject jsonObject){
		mJsonObject = jsonObject;
	}
	
	public BaseModel getModel(){
		return mModel;
	}
	
	public ArrayList<BaseModel> getList(){
		return mList;
	}
}
