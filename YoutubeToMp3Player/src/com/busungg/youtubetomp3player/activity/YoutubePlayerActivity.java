package com.busungg.youtubetomp3player.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.busungg.youtubetomp3player.R;
import com.busungg.youtubetomp3player.adapter.YoutubePlayerExpandableListAdapter;
import com.busungg.youtubetomp3player.adapter.YoutubePlayerExpandableListAdapter.YoutubePlayerExpandableClickInterface;
import com.busungg.youtubetomp3player.define.Define;
import com.busungg.youtubetomp3player.httprequesturl.RequestUrl;
import com.busungg.youtubetomp3player.httpresponseurl.ResponseUrl;
import com.busungg.youtubetomp3player.model.YoutubePlayerConvertWebModel;
import com.busungg.youtubetomp3player.parser.YoutubePlayerConvertWebParser;
import com.busungg.youtubetomp3player.parser.YoutubePlayerExpandableListParser;
import com.busungg.youtubetomp3player.view.YoutubeExpandableListView;
import com.busungg.youtubetomp3player.youtube.YouTubeFailureRecoveryActivity;
import com.busungg.youtubetomp3playerlib.asynctask.BaseAsyncTask;
import com.busungg.youtubetomp3playerlib.asynctask.BaseAsyncTask.OnRequestInterFace;
import com.busungg.youtubetomp3playerlib.customalertdialog.CustomAlertDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class YoutubePlayerActivity extends YouTubeFailureRecoveryActivity implements OnRequestInterFace, OnClickListener ,YoutubePlayerExpandableClickInterface{

	private static final int REQUEST_YOUTUBE_DESCRIPTION = 1;
	private static final int REQUEST_DOWN_API = 2;

	private static final int REQUEST_YOUTUBE_CONVERT_OK = 1;
	private static final int REQUEST_YOUTUBE_CONVERT_NO = 2;

	private int request_flag = REQUEST_YOUTUBE_DESCRIPTION;

	private BaseAsyncTask baseAsyncTask = null;

	private AdView adView;
	
	private YouTubePlayerFragment youTubePlayerFragment = null;
	private YoutubeExpandableListView youtubeExpandableListView = null;
	private WebView youtubeConvertWebview = null;
	private WebView youtubeDownloadWebView = null;
	private ProgressBar youtubeExpandableEmptyView = null;
	private Button youtubeExpandableRequestButtonView = null;
	private LinearLayout youtubeConvertProgressView = null;

	private YoutubePlayerExpandableListAdapter youtubeExpandableListAdapter = null;
	private YoutubePlayerExpandableListParser youtubeExpandableListParser = null;

	private YoutubePlayerConvertWebParser youtubePlayerConvertWebParser = null;

	private static TelephonyManager telephony = null;

	private String phoneNumber = null;
	private String fileName = null;
	private String videoId = null;
	private String category = null;
	private String downloadDate = null;

	private boolean convertFinish = false;
	private String mp3DownLoadURL = null;

	public class javascriptInterface{
		public void processHTML(String url){
			mp3DownLoadURL = url;
			youtubeDownloadWebView.loadUrl(mp3DownLoadURL);
		}
	}

	private void getExtras(){
		Intent intent = getIntent();
		videoId = intent.getStringExtra("id");
		category = intent.getStringExtra("category");
		fileName = intent.getStringExtra("title");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.youtube_player_activity_layout);

		getExtras();

		telephony = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		phoneNumber = telephony.getLine1Number();

		adView = (AdView)findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
		
		youTubePlayerFragment =
				(YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
		youTubePlayerFragment.initialize(Define.PUBLISH_API_KEY, this);
		
		youtubeConvertProgressView = (LinearLayout)findViewById(R.id.youtube_convert_progress);

		//youtube empty view, request button
		youtubeExpandableEmptyView = (ProgressBar)findViewById(R.id.youtube_expandable_empty_view);
		youtubeExpandableRequestButtonView = (Button)findViewById(R.id.youtube_expandable_request_button);
		youtubeExpandableRequestButtonView.setOnClickListener(this);

		youtubeExpandableListView = (YoutubeExpandableListView)findViewById(R.id.youtube_expandable_view);
		youtubeExpandableListView.setEmptyView(youtubeExpandableEmptyView);

		youtubeExpandableListAdapter = new YoutubePlayerExpandableListAdapter(this, this);
		youtubeExpandableListParser = new YoutubePlayerExpandableListParser();

		youtubePlayerConvertWebParser = new YoutubePlayerConvertWebParser();

		//Convert Webview 설정
		youtubeConvertWebview = (WebView)findViewById(R.id.youtube_convert_webview);
		youtubeConvertWebview.getSettings().setJavaScriptEnabled(true);
		youtubeConvertWebview.addJavascriptInterface(new javascriptInterface(), "HTMLOUT");
		youtubeConvertWebview.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView webView, String url){
				youtubeConvertWebview.loadUrl(Define.youtube_javascript_fianl_url);
				youtubeConvertWebview.loadUrl(Define.YOUTUBE_JAVASCRIPT_SUBMIT);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Log.e("Error", "error code:" + errorCode + " - " + description);
			}

			@Override
			public void onLoadResource(WebView view, String url){
				if(url.contains("default.jpg") || url.contains("ping.php")){
					convertFinish = true;
					youtubeConvertProgressView.setVisibility(View.GONE);
					
					CustomAlertDialog.show(YoutubePlayerActivity.this, getString(R.string.youtube_download_convert_error_title), 
							getString(R.string.youtube_download_convert_finish_description));
					return;
				}
			}
		});

		//downLoad용 WebView
		youtubeDownloadWebView = (WebView)findViewById(R.id.youtube_download_webview);

		onRequestStart();
	}
	
	@Override
	protected void onDestroy() {
		
		if(adView != null){
			adView.destroy();
		}
		
		super.onDestroy();
	}

	@Override
	public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
			boolean wasRestored) {
		if (!wasRestored) {
			player.loadVideo(videoId);
		}
	}

	@Override
	protected YouTubePlayer.Provider getYouTubePlayerProvider() {
		return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
	}

	public void onRequestStart() {
		baseAsyncTask = new BaseAsyncTask(this, this);
		baseAsyncTask.execute();
	}

	@Override
	public void onRequestCancle() {
		baseAsyncTask.cancel(true);
	}

	@Override
	public void onRequestError(int errorType) {

		if(baseAsyncTask != null){
			baseAsyncTask.cancel(true);
		}

		if(request_flag == REQUEST_YOUTUBE_DESCRIPTION){
			youtubeExpandableEmptyView.setVisibility(View.GONE);
			youtubeExpandableRequestButtonView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public String onRequestGetParameter() {
		if(request_flag == REQUEST_YOUTUBE_DESCRIPTION){
			return ResponseUrl.youtubeDescriptionURL("jsonc", 2);
		} else if(request_flag == REQUEST_DOWN_API){
			return ResponseUrl.youtubeMp3ConvertWebURL(phoneNumber, fileName, videoId, category, downloadDate);
		}

		return null;
	}

	@Override
	public String onRequestGetUrl() {
		if(request_flag == REQUEST_YOUTUBE_DESCRIPTION){
			return RequestUrl.youtubeDescriptionRequestURL(videoId);
		} else if(request_flag == REQUEST_DOWN_API){
			return RequestUrl.youtubeMp3ConvertRequestURL();
		}

		return null;
	}

	@Override
	public void onRequestResult(JSONObject result) {

		if(request_flag == REQUEST_YOUTUBE_DESCRIPTION){
			youtubeExpandableListParser.setJsonObject(result);
			youtubeExpandableListParser.parseJsonData();

			youtubeExpandableListAdapter.setGroupList(youtubeExpandableListParser.getGroupList());
			youtubeExpandableListAdapter.setChildList(youtubeExpandableListParser.getChildList());

			youtubeExpandableListView.setAdapter(youtubeExpandableListAdapter);

			youtubeExpandableListView.expandGroup(0);
		} else if(request_flag == REQUEST_DOWN_API){

			youtubeExpandableEmptyView.setVisibility(View.GONE);

			youtubePlayerConvertWebParser.setJsonObject(result);
			youtubePlayerConvertWebParser.parseJsonData();

			YoutubePlayerConvertWebModel model = (YoutubePlayerConvertWebModel)youtubePlayerConvertWebParser.getModel();

			if(model.requestResult == REQUEST_YOUTUBE_CONVERT_OK){

				if(mp3DownLoadURL == null){
					youtubeConvertWebview.loadUrl(Define.YOUTUBE_JAVASCRIPT_DWONLOAD_URL);
				} else {
					youtubeDownloadWebView.loadUrl(mp3DownLoadURL);
				}
			} else if(model.requestResult == REQUEST_YOUTUBE_CONVERT_NO){
				CustomAlertDialog.show(this, getString(R.string.youtube_download_error_title),getString(R.string.youtube_download_error_description));
			}
		}

		onRequestCancle();
	}


	//Adapter InterFace
	@Override
	public void YoutubeDownloadButtonClick() {

		if(!convertFinish){
			CustomAlertDialog.show(this, getString(R.string.youtube_download_convert_error_title), getString(R.string.youtube_download_convert_error_description));
			return;
		}

		AlertDialog.Builder alertConfirm = new AlertDialog.Builder(this);
		alertConfirm.setMessage(getString(R.string.youtube_download_alert_text)).setCancelable(false)
		.setPositiveButton(getString(R.string.youtube_download_ok_text), 
		new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				youtubeExpandableEmptyView.setVisibility(View.VISIBLE);

				long now = System.currentTimeMillis();
				Date date = new Date(now);
				SimpleDateFormat curDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				downloadDate = curDateFormat.format(date);

				request_flag = REQUEST_DOWN_API;
				onRequestStart();
			}
		}).setNegativeButton(getString(R.string.youtube_download_no_text), 
		new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});

		AlertDialog alert = alertConfirm.create();
		alert.show();
	}

	@Override
	public void YoutubeConvertButtonClick(String youtubeUrl) {
		Define.youtube_javascript_fianl_url = Define.YOUTUBE_JAVASCRIPT_URL_VALUE_FRONT + youtubeUrl + Define.YOUTUBE_JAVASCRIPT_URL_VALUE_BACK;
		
		convertFinish = false;
		youtubeConvertProgressView.setVisibility(View.VISIBLE);
		
		youtubeConvertWebview.loadUrl(Define.YOUTUBE_MP3_URL);
	}

	//Activity onCick
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.youtube_expandable_request_button:
			request_flag = REQUEST_YOUTUBE_DESCRIPTION;
			youtubeExpandableRequestButtonView.setVisibility(View.GONE);
			youtubeExpandableEmptyView.setVisibility(View.VISIBLE);
			onRequestStart();
			break;
		}
	}
}
