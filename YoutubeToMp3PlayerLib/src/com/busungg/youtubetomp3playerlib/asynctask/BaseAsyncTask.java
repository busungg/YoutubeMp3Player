package com.busungg.youtubetomp3playerlib.asynctask;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import com.busungg.youtubetomp3playerlib.customalertdialog.CustomAlertDialog;
import com.busungg.youtubetomp3playerlib.httpclient.BaseHttpClient;
import com.busungg.youtubetomp3playerlib.httpclient.BaseHttpResponse;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class BaseAsyncTask extends AsyncTask<String, Integer, JSONObject>{

	private String mTitle = "ERROR";
	protected Context mContext;
	protected String mUrl;
	protected String mParameter;
	
	private HttpResponse response;
	
	private OnRequestInterFace mCallback;
	
	public interface OnRequestInterFace
	{
		void onRequestError(int errorType);
		void onRequestCancle();
		String onRequestGetUrl();
		String onRequestGetParameter();
		void onRequestResult(JSONObject result);
	}
	
	public BaseAsyncTask(Context context, OnRequestInterFace callback) {
		mContext = context;
		mCallback = callback;
	}
	
	public BaseAsyncTask(Context context, OnRequestInterFace callback ,String url, String parameter){
		this(context, callback);
		mUrl = url;
		mParameter = parameter;
	}

	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        if(mCallback == null){
        	Log.e("Error", "Callback is null");
        	CustomAlertDialog.show(mContext, mTitle, "Callback is null");
        	return;
        }
        
        if(mContext == null){
        	Log.e("Error", "Context is null");
        	CustomAlertDialog.show(mContext, mTitle, "Context is null");
        	return;
        } else {
        	if(!BaseHttpClient.getInstance().checkConnectivity(mContext)){
        		CustomAlertDialog.show(mContext, mTitle, "Please check your WIFI or LTE");
        		return;
        	}
        }
    } 
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
    }
	
	@Override
	protected JSONObject doInBackground(String... params) {
		
		response = BaseHttpClient.getInstance().doGet(mCallback.onRequestGetUrl(), mCallback.onRequestGetParameter());
		//response = BaseHttpClient.getInstance().doPost(mCallback.onRequestGetUrl(), mCallback.onRequestGetParameter());
		
		if(response != null){
			return BaseHttpResponse.getInstance().convertResponseToString(response);
			
		} else {
			mCallback.onRequestError(0);
			mCallback.onRequestCancle();
		}
		
		return null;
	}
	
	@Override
    protected void onPostExecute(JSONObject result) {
		
		try{
			if(result != null){
				mCallback.onRequestResult(result);
			} else {
				CustomAlertDialog.show(mContext, mTitle, "JSONObject Result Is Null");
			}
		}catch(Exception e){
			CustomAlertDialog.show(mContext, mTitle, "JSON Parser Error");
		}
    }
     
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
