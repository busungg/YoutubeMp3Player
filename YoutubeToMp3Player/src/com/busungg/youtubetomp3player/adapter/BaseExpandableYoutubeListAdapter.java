package com.busungg.youtubetomp3player.adapter;

import java.util.ArrayList;

import com.busungg.youtubetomp3player.model.BaseModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public abstract class BaseExpandableYoutubeListAdapter extends BaseExpandableListAdapter{

	protected Context mContext = null;
	protected LayoutInflater mInflater = null;
	protected ArrayList<BaseModel> mGroupList = null;
	protected ArrayList<ArrayList<BaseModel>> mChildList = null;
	
	public BaseExpandableYoutubeListAdapter(Context context){
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}
	
	public void setGroupList(ArrayList<BaseModel> groupList){
		mGroupList = groupList;
	}
	
	public void setChildList(ArrayList<ArrayList<BaseModel>> childList){
		mChildList = childList;
	}
	
	@Override
	public int getGroupCount() {
		if(mGroupList != null){
			return mGroupList.size();
		}
		
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if(mChildList != null){
			return mChildList.get(groupPosition).size();
		}
		
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroupList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mChildList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		return getGroupItemView(groupPosition,isExpanded, convertView, parent);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		return getChildItemView(groupPosition, childPosition, isLastChild, convertView, parent);
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
	
	public abstract View getGroupItemView(int groupPosition, boolean isExpanded, 
			View convertView, ViewGroup parent);
	
	public abstract View getChildItemView(int groupPosition, int childPosition, 
			boolean isLastChild, View convertView, ViewGroup parent);
}
