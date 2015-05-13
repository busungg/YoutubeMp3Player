package com.busungg.youtubetomp3playerlib.asynctask;

import org.json.JSONObject;

import com.busungg.youtubetomp3playerlib.R;
import com.busungg.youtubetomp3playerlib.asynctask.BaseAsyncTask.OnRequestInterFace;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;

public class MainActivity extends Activity implements OnRequestInterFace{

	private BaseAsyncTask baseAsncyTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		baseAsncyTask = new BaseAsyncTask(this, this);
		baseAsncyTask.execute();
	}

	@Override
	public void onRequestError(int errorType) {
	}

	@Override
	public void onRequestCancle() {
		baseAsncyTask.cancel(true);
	}

	@Override
	public String onRequestGetUrl() {
		return "http://gdata.youtube.com/feeds/api/videos?q=eminem+rapgod&max-results=3&alt=jsonc&v=2";
	}

	@Override
	public String onRequestGetParameter() {
		return null;
	}

	@Override
	public void onRequestResult(JSONObject result) {
		try{
			Log.d("HttpResponse", result.toString());
			Log.d("HttpResponse", "TotalItems = " + result.getJSONObject("data").optInt("totalItems"));
		}catch(Exception e){
			Log.e("Error", "Error JsonObject");
		}
	}
}
