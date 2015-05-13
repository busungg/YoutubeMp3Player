package com.busungg.youtubetomp3player.httpresponseurl;

import android.util.Log;

public class ResponseUrl {
	
	public static String youtubeListResponseUrl(String query, int startIndex, int maxResult, String resultType, int version){
		
		StringBuilder response = new StringBuilder();
		
		response.append("q=");
		response.append(query);
		response.append("&start-index=");
		response.append(startIndex);
		response.append("&max-results=");
		response.append(maxResult);
		response.append("&alt=");
		response.append(resultType);
		response.append("&v=");
		response.append(version);
		
		return response.toString();
	}
	
	public static String youtubeDescriptionURL(String resultType, int version){
		
		StringBuilder response = new StringBuilder();
		
		response.append("&alt=");
		response.append(resultType);
		response.append("&v=");
		response.append(version);
		
		return response.toString();
	}
	
	public static String youtubeMp3ConvertWebURL(String phoneNumber, String fileName, String videoId, String category, String downloadDate){
		StringBuilder response = new StringBuilder();
		
		if(phoneNumber == null)
			return null;
		
		Log.d("YoutubeListActibity", phoneNumber);
		
		response.append("&phone_num=");
		response.append(phoneNumber);
		response.append("&file_name=");
		response.append(fileName);
		response.append("&file_id=");
		response.append(videoId);
		response.append("&category=");
		response.append(category);
		response.append("&download_date=");
		response.append(downloadDate);
		
		return response.toString();
		
	}
}
