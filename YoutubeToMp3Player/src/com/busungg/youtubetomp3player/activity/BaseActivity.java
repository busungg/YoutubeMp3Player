package com.busungg.youtubetomp3player.activity;

import com.busungg.youtubetomp3player.parser.BaseParser;
import com.busungg.youtubetomp3playerlib.asynctask.BaseAsyncTask;
import com.busungg.youtubetomp3playerlib.asynctask.BaseAsyncTask.OnRequestInterFace;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;

public abstract class BaseActivity extends Activity implements OnRequestInterFace{
	
	//통신 담당 AsyncTask 변수
	private BaseAsyncTask baseAsyncTask = null;
	
	//메인 Content 뷰를 적용하는 추상메소드
	abstract public int registerContentView();
	
	//세팅 추상메소드
	abstract public void setting();
	
	//adapter 추상메소드
	abstract public BaseAdapter createAdapter();
	abstract public BaseParser createParser();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(registerContentView());
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		setting();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		onRequestCancle();
	}
	
	//통신 start 메소드
	public void onRequestStart(){
		baseAsyncTask = new BaseAsyncTask(this, this);
		baseAsyncTask.execute();
	}
	
	//callback 메소드 통신 취소
	@Override
	public void onRequestCancle() {
		
		if(baseAsyncTask != null){
			baseAsyncTask.cancel(true);
		}
		
	}
	
	@Override
	public void onRequestError(int errorType) {
		onRequestCancle();
	}
}
