package com.busungg.youtubetomp3player.define;

public class Define {
	
	public static final String PUBLISH_API_KEY = "AIzaSyCTEzZ2aCKPSVLftY3pwWL6bQFWprV-9pk";
	public static final String DEVELPOPE_API_KEY = "AIzaSyDAajWJjdnb6v0NBHyAz2-VdNGw7NV8Tcw";
	
	//youtube mp3 convert web url
	public static final String YOUTUBE_MP3_CONVERT_WEB_URL = "http://rlaqnqn.dothome.co.kr/home/dbset";
	
	//youtube mp3 api url
	public static final String YOUTUBE_API_REQUEST = "http://gdata.youtube.com/feeds/api/videos";
	
	//youtube mp3 download url
	public static String youtube_javascript_fianl_url = null;
	public static final String YOUTUBE_MP3_URL = "http://www.youtube-mp3.org/";
	public static final String YOUTUBE_JAVASCRIPT_URL_VALUE_FRONT = "javascript:document.getElementById('youtube-url').value ='";
	public static final String YOUTUBE_JAVASCRIPT_URL_VALUE_BACK = "'";
	public static final String YOUTUBE_JAVASCRIPT_SUBMIT = "javascript:document.getElementById('submit').click()";
	public static final String YOUTUBE_JAVASCRIPT_DWONLOAD_URL = "javascript:window.HTMLOUT.processHTML(" +
			"(function(){" +
			"var data = document.getElementById('dl_link').children;" +
			"for(var i = 0; i < data.length; i++)" +
			"{" +
			"if(data[i].href.length > 63)" +
			"{" +
			"return data[i].href;" +
			"}" +
			"}" +
			"})()" +
			");";
	
	
}
