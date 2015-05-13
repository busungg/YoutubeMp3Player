package com.busungg.youtubetomp3player.model;

public class YoutubeDescModel implements BaseModel{
	
	public static class GroupModel implements BaseModel{
		public String title = null;
		public String uploaded = null;
	}
	
	public static class ChildCategory implements BaseModel{
		public String player = null;
		public String category = null;
	}
	
	public static class ChildDescription implements BaseModel{
		public String description = null;
	}
}
