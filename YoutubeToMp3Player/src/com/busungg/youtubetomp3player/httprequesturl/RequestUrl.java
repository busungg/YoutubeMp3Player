package com.busungg.youtubetomp3player.httprequesturl;

import com.busungg.youtubetomp3player.define.Define;

public class RequestUrl {
	
	public static String youtubeListRequestUrl(){
		return Define.YOUTUBE_API_REQUEST;
	}
	
	public static String youtubeDescriptionRequestURL(String videoId){
		return Define.YOUTUBE_API_REQUEST + "/" + videoId;
	}
	
	public static String youtubeMp3ConvertRequestURL(){
		return Define.YOUTUBE_MP3_CONVERT_WEB_URL;
	}
	
}
