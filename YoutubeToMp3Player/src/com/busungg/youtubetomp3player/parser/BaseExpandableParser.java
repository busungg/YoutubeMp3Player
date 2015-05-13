package com.busungg.youtubetomp3player.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.busungg.youtubetomp3player.model.BaseModel;

public abstract class BaseExpandableParser {
	
	protected BaseModel mModel = null;
	protected ArrayList<BaseModel> mGroupList = null;
	protected ArrayList<ArrayList<BaseModel>> mChildListList = null;
	protected ArrayList<BaseModel> mChildList = null;
	protected JSONObject mJsonObject = null;
	
	public abstract void parseJsonListData(JSONArray array);
	public abstract void parseJsonData();
	
	public BaseExpandableParser(){
		mGroupList = new ArrayList<BaseModel>();
		mChildList = new ArrayList<BaseModel>();
		mChildListList = new ArrayList<ArrayList<BaseModel>>();
	}
	
	public void setJsonObject(JSONObject jsonObject){
		mJsonObject = jsonObject;
	}
	
	public BaseModel getModel(){
		return mModel;
	}
	
	public ArrayList<BaseModel> getGroupList(){
		return mGroupList;
	}
	
	public ArrayList<ArrayList<BaseModel>> getChildList(){
		return mChildListList;
	}
}
