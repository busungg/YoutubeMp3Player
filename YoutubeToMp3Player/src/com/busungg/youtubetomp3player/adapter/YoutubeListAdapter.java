package com.busungg.youtubetomp3player.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.busungg.youtubetomp3player.R;
import com.busungg.youtubetomp3player.model.YoutubeListModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class YoutubeListAdapter extends BaseListAdapter{ 
	
	public YoutubeListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.youtube_list_data_layout, null);
			holder = new ViewHolder();
			
			holder.mThumbnail = (ImageView)convertView.findViewById(R.id.list_thumb_image_view);
			holder.mTitle = (TextView)convertView.findViewById(R.id.list_title_text_view);
			holder.mViewCount = (TextView)convertView.findViewById(R.id.list_viewcount_text_view);
			holder.mUploaded = (TextView)convertView.findViewById(R.id.list_uploaded_text_view);
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		YoutubeListModel.ListItem item = (YoutubeListModel.ListItem)mList.get(position);
		
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.youtube_background)
			.showImageForEmptyUri(R.drawable.youtube_background)
			.build();
		imageLoader.displayImage(item.thumbnail, holder.mThumbnail, options);
		
		holder.mTitle.setText(item.title);
		holder.mViewCount.setText(mContext.getString(R.string.list_viewcount_text) +item.viewCount);
		holder.mUploaded.setText(mContext.getString(R.string.list_upload_text) + item.uploaded);
		
		return convertView;
	}
	
	protected class ViewHolder{
		ImageView mThumbnail = null;
		TextView mTitle  = null;
		TextView mViewCount = null;
		TextView mUploaded = null;
	}
}
