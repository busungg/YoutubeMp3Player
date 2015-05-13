package com.busungg.youtubetomp3playerlib.httpclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import android.util.Log;

public class BaseHttpResponse {
	
	private static BaseHttpResponse baseHttpResponseInstance = null;
	
	private BaseHttpResponse(){}
	
	public static BaseHttpResponse getInstance(){
		baseHttpResponseInstance = new BaseHttpResponse();
		
		if(baseHttpResponseInstance != null){
			return baseHttpResponseInstance;
		}
		
		return null;
	}

	public JSONObject convertResponseToString(HttpResponse response) {
		try
		{
			String line = null;
			StringBuilder responseString = new StringBuilder();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			while((line = br.readLine()) != null) {
				responseString.append(line);
			}
			
			br.close();
			
			JSONObject jsonObject = new JSONObject(responseString.toString());
			
			return jsonObject;
		}
		catch (Exception e)
		{
			Log.e("Error", "convert error");
		}

		return null;
	}

}
