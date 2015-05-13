
package com.busungg.youtubetomp3player.activity;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.busungg.youtubetomp3player.R;
import com.busungg.youtubetomp3player.adapter.YoutubeListAdapter;
import com.busungg.youtubetomp3player.httprequesturl.RequestUrl;
import com.busungg.youtubetomp3player.httpresponseurl.ResponseUrl;
import com.busungg.youtubetomp3player.model.YoutubeListModel;
import com.busungg.youtubetomp3player.parser.BaseParser;
import com.busungg.youtubetomp3player.parser.YoutubeListParser;
import com.busungg.youtubetomp3player.view.PullToRefreshView.onPullToRefresh;
import com.busungg.youtubetomp3player.view.YoutubeListView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class YoutubeListActivity extends BaseActivity implements OnClickListener, onPullToRefresh{

	/*private TelephonyManager telephony = null;
	private static String TEST_DEVICE_ID = null; //device id;
	 */
	private static int startIndex = 1;
	private final int maxResult = 10;
	private final String resultType = "jsonc";
	private final int version = 2;

	private EditText searchEditText = null;
	private Button searchButton = null;

	private AdView adView;

	private YoutubeListView youtubeListView = null;
	private View emptyListView = null;
	private ProgressBar progressView = null;

	private YoutubeListAdapter listAdapter = null;
	private YoutubeListParser listParser = null;

	@Override
	public int registerContentView() {
		// TODO Auto-generated method stub
		return R.layout.youtube_list_activity_layout;
	}

	@Override
	public void setting() {
		//adView
		adView = (AdView) this.findViewById(R.id.adView);

		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);

		//에딧 텍스트
		searchEditText = (EditText)findViewById(R.id.search_edittext);

		//서치 버튼
		searchButton = (Button)findViewById(R.id.search_button);
		searchButton.setOnClickListener(this);

		//리스트뷰 설정 
		youtubeListView = (YoutubeListView)findViewById(R.id.youtube_list_view);
		youtubeListView.setRefreshCallback(this);

		emptyListView = findViewById(R.id.empty_list_view);
		youtubeListView.setEmptyView(emptyListView);

		progressView = (ProgressBar)findViewById(R.id.youtube_list_search_progress_view);

		//어뎁터 및 파서 설정
		if(listAdapter == null){
			listAdapter = (YoutubeListAdapter)createAdapter();
		}

		if(listParser == null){
			listParser = (YoutubeListParser)createParser();
		}

		//어뎁터 및 파서를 등록
		listAdapter.setList(listParser.getList());
	}

	@Override
	protected void onDestroy() {

		if(adView != null){
			adView.destroy();
		}
		
		try{
			clearApplicationData(this);
		}catch(Exception e){
			Log.e("Error", e.toString());
		}
		
		super.onDestroy();
	}
	
	public static void clearApplicationData(Context context) {
	    File cache = context.getCacheDir();
	    File appDir = new File(cache.getParent());
	    if (appDir.exists()) {
	        String[] children = appDir.list();
	        for (String s : children) {
	            File f = new File(appDir, s);
	            if(deleteDir(f))
	                Log.i("YoutubeListActibity", String.format("**************** DELETED -> (%s) *******************", f.getAbsolutePath()));
	        }
	    }
	}
	private static boolean deleteDir(File dir) {
	    if (dir != null && dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i = 0; i < children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }
	    return dir.delete();
	}

	@Override
	public BaseAdapter createAdapter() {
		// TODO Auto-generated method stub
		return new YoutubeListAdapter(this);
	}

	@Override
	public BaseParser createParser(){
		return new YoutubeListParser();
	}

	@Override
	public String onRequestGetParameter() {
		return ResponseUrl.youtubeListResponseUrl(getQuery(), startIndex, maxResult, resultType, version);
	}

	@Override
	public String onRequestGetUrl() {
		return RequestUrl.youtubeListRequestUrl();
	}

	@Override
	public void onRequestResult(JSONObject result) {
		// TODO Auto-generated method stub
		listParser.setJsonObject(result);
		listParser.parseJsonData();

		startIndex = ((YoutubeListModel)listParser.getModel()).startIndex + maxResult;

		if(youtubeListView.getAdapter() == null){
			youtubeListView.setAdapter(listAdapter);
		} else {
			listAdapter.notifyDataSetChanged();
		}

		youtubeListView.alertRefresh(false);
		progressView.setVisibility(View.GONE);

		onRequestCancle();
	}

	@Override
	public void onClick(View v) {

		switch(v.getId())
		{
		case R.id.search_button:
		{
			startIndex = 1;
			listAdapter.reset();
			emptyListView.setVisibility(View.GONE);
			progressView.setVisibility(View.VISIBLE);

			onRequestStart();
			break;
		}

		}
	}

	private String getQuery(){

		String query = "";
		String[] searchTextAllArray = null;
		ArrayList<String> searchTextRealArray = new ArrayList<String>();

		searchTextAllArray = searchEditText.getText().toString().split(" ");

		for(int i = 0; i < searchTextAllArray.length; i++){
			if(searchTextAllArray[i].equalsIgnoreCase(" ")){
				continue;
			}
			searchTextRealArray.add(searchTextAllArray[i]);
		}

		for(int i = 0; i < searchTextRealArray.size(); i++){

			query += searchTextRealArray.get(i);

			if(i != searchTextRealArray.size() - 1){
				query += "+";
			}
		}

		return query;
	}

	@Override
	public void onRefresh() {
		youtubeListView.alertRefresh(true);
		onRequestStart();
	}
}
