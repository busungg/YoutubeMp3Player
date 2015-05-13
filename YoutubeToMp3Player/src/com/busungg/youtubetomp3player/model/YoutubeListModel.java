package com.busungg.youtubetomp3player.model;

public class YoutubeListModel implements BaseModel{

	public int startIndex  = 0;

	public static class ListItem implements BaseModel{
		
		public String id = null;
		public String category = null;
		public String thumbnail = null;
		public String title = null;
		public int viewCount = 0;
		public String uploaded = null;
	}
}

