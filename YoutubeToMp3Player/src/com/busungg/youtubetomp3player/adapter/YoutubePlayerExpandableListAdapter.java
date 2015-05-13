package com.busungg.youtubetomp3player.adapter;

import com.busungg.youtubetomp3player.R;
import com.busungg.youtubetomp3player.model.BaseModel;
import com.busungg.youtubetomp3player.model.YoutubeDescModel;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class YoutubePlayerExpandableListAdapter extends BaseExpandableYoutubeListAdapter implements OnClickListener{

	public interface YoutubePlayerExpandableClickInterface{
		
		public void YoutubeConvertButtonClick(String youtubeUrl);
		public void YoutubeDownloadButtonClick();
	}
	
	private YoutubePlayerExpandableClickInterface mCallback = null;
	
	public YoutubePlayerExpandableListAdapter(Context context) {
		super(context);
	}
	
	public YoutubePlayerExpandableListAdapter(Context context, YoutubePlayerExpandableClickInterface callback){
		super(context);
		mCallback = callback;
	}

	@Override
	public View getGroupItemView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		ViewGroupHolder groupHolder = null;
		
		if(convertView == null){
			groupHolder = new ViewGroupHolder();
			convertView = mInflater.inflate(R.layout.youtube_expandablelist_group_layout, null);
			
			groupHolder.mTitle = (TextView)convertView.findViewById(R.id.expandablelist_group_title);
			groupHolder.mUploaded = (TextView)convertView.findViewById(R.id.expandablelist_group_uploaded);
			
			convertView.setTag(groupHolder);
		} else {
			groupHolder = (ViewGroupHolder) convertView.getTag();
		}
		
		YoutubeDescModel.GroupModel model = (YoutubeDescModel.GroupModel)mGroupList.get(groupPosition);
		
		groupHolder.mTitle.setText(model.title);
		groupHolder.mUploaded.setText(mContext.getString(R.string.expandable_upload_text) + model.uploaded);
		
		return convertView;
	}

	@Override
	public View getChildItemView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		BaseModel model = mChildList.get(groupPosition).get(childPosition);
		
		if(convertView == null){
			if(model instanceof YoutubeDescModel.ChildCategory){
				
				ViewChildCategoryHolder categoryHolder = new ViewChildCategoryHolder();
				convertView = mInflater.inflate(R.layout.youtube_expandablelist_child_category_layout, null);
				
				categoryHolder.mCategory = (TextView)convertView.findViewById(R.id.expandablelist_child_category);
				categoryHolder.mConvert = (Button)convertView.findViewById(R.id.expandablelist_child_convert);
				categoryHolder.mConvert.setOnClickListener(this);
				
				categoryHolder.mDownload = (Button)convertView.findViewById(R.id.expandablelist_child_download);
				categoryHolder.mDownload.setOnClickListener(this);
				
				YoutubeDescModel.ChildCategory categoryModel = (YoutubeDescModel.ChildCategory)model;
				
				categoryHolder.mCategory.setText(mContext.getString(R.string.expandable_category_text) + categoryModel.category);
				categoryHolder.mConvert.setBackgroundResource(R.drawable.ic_action_repeat);
				categoryHolder.mDownload.setBackgroundResource(R.drawable.ic_action_download);
				
			} else {
				
				ViewChildDescriptionHolder descriptionHolder = new ViewChildDescriptionHolder();
				convertView = mInflater.inflate(R.layout.youtube_expandablelist_child_desc_layout, null);
				
				descriptionHolder.mDescription = (TextView)convertView.findViewById(R.id.expandablelist_child_description);
				
				YoutubeDescModel.ChildDescription descriptionModel = (YoutubeDescModel.ChildDescription)model;
				
				descriptionHolder.mDescription.setText(descriptionModel.description);
			}
		}
		
		return convertView;
	}
	
	protected class ViewGroupHolder{
		public TextView mTitle;
		public TextView mUploaded;
	}
	
	protected class ViewChildCategoryHolder{
		public TextView mCategory;
		public Button mConvert;
		public Button mDownload;
	}
	
	protected class ViewChildDescriptionHolder{
		public TextView mDescription;
	}

	@Override
	public void onClick(View v) {
		
		YoutubeDescModel.ChildCategory categoryModel = (YoutubeDescModel.ChildCategory)mChildList.get(0).get(0);
		
		switch(v.getId()){
		
		case R.id.expandablelist_child_convert:
			mCallback.YoutubeConvertButtonClick(categoryModel.player);
			break;
		
		case R.id.expandablelist_child_download:
			mCallback.YoutubeDownloadButtonClick();
			break;
		}
	}
}
