package com.jackpan.taiwamrain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import Appkey.MyAdKey;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.jackpan.findchurch.R;


public class TwoActivity extends Activity {
	private TextView textview, textview2, textview3, textview4, textview5,
			textview6, textview7, textview8, textview9, textview10, textview11,
			textview12, textview13, textview14, textview15, textview16,
			textview17, textview18, textview19, textview20, textview21
			, textview22, textview23, textview24, textview25, textview26,textview81;
	private ImageView img;
	private Button btn;
	private boolean isCencel = false;
	private ProgressDialog progressDialog;
	private String name;
	private Bitmap bitmap = null;
	private String mArea;
	private InterstitialAd interstitial;
	private WebView mWebVirw;
	private   double latitude ,longitude;
	private Button googlbtn;
	private ResultData data;
	private boolean isThread = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //開啟全螢幕
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 //設定隱藏APP標題
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_two);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		textview = (TextView) findViewById(R.id.textView1);
		textview2 = (TextView) findViewById(R.id.textView2);
		textview3 = (TextView) findViewById(R.id.textView3);
		textview4 = (TextView) findViewById(R.id.textView4);
		textview5 = (TextView) findViewById(R.id.textView5);
		textview6 = (TextView) findViewById(R.id.textView6);

		textview7 = (TextView) findViewById(R.id.textView7);
		textview8 = (TextView) findViewById(R.id.textView8);
		textview81 = (TextView) findViewById(R.id.textView81);
		textview9 = (TextView) findViewById(R.id.textView9);
		textview10 = (TextView) findViewById(R.id.textView10);
		textview11 = (TextView) findViewById(R.id.textView11);
		textview12 = (TextView) findViewById(R.id.textView12);
		textview13 = (TextView) findViewById(R.id.textView13);
		textview14 = (TextView) findViewById(R.id.textView14);
		textview15 = (TextView) findViewById(R.id.textView15);
		textview16 = (TextView) findViewById(R.id.textView16);
		textview17 = (TextView) findViewById(R.id.textView17);
		textview18 = (TextView) findViewById(R.id.textView18);
		textview19 = (TextView) findViewById(R.id.textView19);
		textview20 = (TextView) findViewById(R.id.textView20);
		textview21 = (TextView) findViewById(R.id.textView21);
		textview22 = (TextView) findViewById(R.id.textView22);
		textview23 = (TextView) findViewById(R.id.textView23);
		textview24 = (TextView) findViewById(R.id.textView24);
		textview25 = (TextView) findViewById(R.id.textView25);
		textview26 = (TextView) findViewById(R.id.textView26);
		googlbtn = (Button) findViewById(R.id.button1);
//		googlbtn.setVisibility(View.GONE);
//		 textview4.setVisibility(View.GONE);
//		 textview5.setVisibility(View.GONE);
//		 textview6.setVisibility(View.GONE);
//		 textview7.setVisibility(View.GONE);
		 textview8.setVisibility(View.GONE);
		 textview81.setVisibility(View.GONE);
		 textview9.setVisibility(View.GONE);
		 textview10.setVisibility(View.GONE);
		 textview11.setVisibility(View.GONE);
		 textview12.setVisibility(View.GONE);
		textview13.setVisibility(View.GONE);
		textview14.setVisibility(View.GONE);
		textview15.setVisibility(View.GONE);
		textview16.setVisibility(View.GONE);
		textview17.setVisibility(View.GONE);
		textview18.setVisibility(View.GONE);
		textview19.setVisibility(View.GONE);
		textview20.setVisibility(View.GONE);
		textview21.setVisibility(View.GONE);
		textview22.setVisibility(View.GONE);
		textview23.setVisibility(View.GONE);
		textview24.setVisibility(View.GONE);
		textview25.setVisibility(View.GONE);
		textview26.setVisibility(View.GONE);
		img = (ImageView) findViewById(R.id.pageimg);
		img.setVisibility(View.GONE);
	
		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
//			loadIntent();
//		LoadNetAsyncTask loadNetAsyncTask2 = new LoadNetAsyncTask();
//		loadNetAsyncTask2.execute(MyAdKey.jsondata2);
	
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(MyAdKey.AdmobinterstitialBannerId);
		interstitial.loadAd(adRequest);
		

	}

//	private void loadIntent() {
//		String json = getIntent().getStringExtra("json");
//		final String lat = getIntent().getStringExtra("Latitude");
//		final String lon = getIntent().getStringExtra	("Longitude");
////		final String latList = getIntent().getStringExtra("latList");
////		final double latList = getIntent().getDoubleExtra("latList", 0);
//////		final String lonList = getIntent().getStringExtra("lonList");
////		final double lonList = getIntent().getDoubleExtra("lonList", 0);
//
////		Log.e("Jack","lat"+Double.parseDouble(latList)+""+"lon"+Double.parseDouble(lonList));
//		 data = new Gson().fromJson(json, ResultData.class);
//		// loadImage(data.album_file, img);
////
//		textview.setText("流水號:" + data.CHR_ID);
//		textview2.setText("教堂名稱:" + data.CHR_Name);
//
//
//
//
//		textview3.setText("所在區域:" + data.CHR_Area);
//		textview4.setText("地址:" + data.CHR_Address);
//		textview5.setText("負責人:" + data.CHR_Charge);
//
//		textview6.setText("電話:" + data.CHR_Phone);
//		textview6.setTextColor(Color.RED);
//		textview6.setTextSize(24);
//
//		textview6.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if(data.CHR_Phone.equals("")) {
//					Toast.makeText(getApplicationContext(), "無電話資訊！！", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				 Intent myIntentDial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+data.CHR_Phone));
//			      startActivity(myIntentDial);
//
//			}
//		});
//
//		textview7.setText("建造日期:" + data.CHR_BuildTime);
//
////		textview9.setText("停車場（腳踏車）總車架數 :" + data.TOTALBIKE);
////		textview10.setText("停車場收費資訊:" + data.PAYEX);
////		if(data.TEL.equals("")) textview11.setVisibility(View.GONE);
////		textview11.setText("停車場電話:" + data.TEL);
////		textview11.setTextSize(24);
////		textview11.setTextColor(Color.RED);
////
////		textview11.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////
////			        Intent myIntentDial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+data.TEL));
////			        startActivity(myIntentDial);
////
////			}
////		});
////		MyApi.cal_TWD97_To_lonlat(Double.parseDouble(data.TW97X), Double.parseDouble(data.TW97Y));
//////
////
//////        boolean isGpsOpen = MySharedPreferences.getIsGPSState(TwoActivity.this);
//////
//		MyApi.mGecoder(getApplicationContext(), data.CHR_Address);
//        googlbtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				   String startLat =lat;
//	                String startLng = lon;
//	                String endLat = String.valueOf(MyApi.getGeocoderlat());
//	                String endLng = String.valueOf(MyApi.getGeocoderlon());
//	                Uri uri = Uri.parse("http://maps.google.com/maps? f=d&saddr=" + startLat + "%20" + startLng + "&daddr=" + endLat + "%20" + endLng + "&hl=tw");
//	                Intent it = new Intent(Intent.ACTION_VIEW, uri);
//	                startActivity(it);
//			}
//		});
//
//
////
////
//
//
//
//
//	}
	
	@Override
	protected void onPause() {
				super.onPause();
				isThread=false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}
	

	@Override
	protected void onResume() {
		interstitial.show();
		super.onResume();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK)) { // 確定按下退出鍵
			
			isThread=false;
			this.finish();
			return true;

		}

		return super.onKeyDown(keyCode, event);

	}

	WebViewClient mWebViewClient = new WebViewClient() {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	};
//	private class LoadNetAsyncTask extends
//	AsyncTask<String, Void, ArrayList<ResultData>> {
//
//		@Override
//		protected void onPostExecute(final ArrayList<ResultData> result) {
//			super.onPostExecute(result);
//
//		}
//
//
//		@Override
//		protected ArrayList<ResultData> doInBackground(String... params) {
//			BufferedReader br = null;
//			StringBuilder sb = new StringBuilder();
//			try {
//				URL url = new URL(params[0]);
//				HttpURLConnection httpUrlCon = (HttpURLConnection) url
//						.openConnection();
//				httpUrlCon.setConnectTimeout(20000);// 連線
//				httpUrlCon.setReadTimeout(20000);// 讀取
//
//				InputStream inputStream = httpUrlCon.getInputStream();
//				InputStreamReader inputStreamReader = new InputStreamReader(
//						inputStream);
//				br = new BufferedReader(inputStreamReader);
//				String value = null;
//
//				while ((value = br.readLine()) != null) {
//					sb.append(value);
//				}
//				String result = sb.toString();
//
//				ArrayList<ResultData> allData = new ArrayList<ResultData>();
//	
//				try {
//			
//			
//					JSONArray jsonarry = new JSONArray(result);
//					for (int i = 0; i < jsonarry.length(); i++) {
//						JSONObject jsonObject = jsonarry.getJSONObject(i);
//						Gson gson = new Gson();
//						
//						final ResultData data2 = gson.fromJson(jsonObject.toString(),
//								ResultData.class);
//				
//						if(data.ID.equals(data2.ID)){
//							Log.e("Jack", data.ID);
//							Log.e("Jack", data2.ID);
//							if(data2.AVAILABLECAR.equals("-9"))
//							{
//								 continue;
//								
//							}else 
//							
//							{	runOnUiThread(new Runnable() {
//								public void run() {
//								textview8.setVisibility(View.VISIBLE);
//								textview8.setTextSize(28);
//								textview8.setTextColor(Color.BLUE);
//								textview8.setText("剩餘車位:" + data2.AVAILABLECAR);
//								Log.e("Jack", data2.AVAILABLECAR);
//								
//								new Thread(){
//									
//									public void run() {
//										while(isThread){
//											Calendar cal = Calendar.getInstance();
//											int a = cal.get(Calendar.SECOND);
//											if(a>=30) a=a-30;
//											final String etime = String.valueOf(30-a);
//											
//											runOnUiThread(new Runnable() {
//												public void run() {
//												textview81.setVisibility(View.VISIBLE);
//												textview81.setText("更新倒數:" + etime );
//							
//												}
//											});
//											if(a%30==0){
//												runOnUiThread(new Runnable() {
//													public void run() {
//														LoadNetAsyncTask loadNetAsyncTask2 = new LoadNetAsyncTask();
//														loadNetAsyncTask2.execute(MyAdKey.jsondata2);
//								
//													}
//												});
//											}
//											try {
//												Thread.sleep(100);
//												
//								
//											} catch (InterruptedException e) {
//												// TODO Auto-generated catch block
//												e.printStackTrace();
//											}
//											
//										}
//										
//									};
//									
//								}.start();
//								if(!isThread) Thread.interrupted();
////								new CountDownTimer(30000, 1000) {
////
////									 @Override
////
////									 public void onTick(long millisUntilFinished) {
////									                //倒數秒數中要做的事
////										 textview81.setText("倒數時間:" + new SimpleDateFormat("s").format(millisUntilFinished));
////									 }
////
////									 @Override
////									 public void onFinish() {
////											runOnUiThread(new Runnable() {
////												public void run() {
////												textview8.setVisibility(View.VISIBLE);
////												textview8.setTextSize(28);
////												textview8.setTextColor(Color.BLUE);
////												textview8.setText("剩餘（汽車）車位數:" + data2.AVAILABLECAR);
////							
////												}
////											});
////											 try {
////												Thread.sleep(3000);
////											} catch (InterruptedException e) {
////												// TODO Auto-generated catch block
////												e.printStackTrace();
////											}
////										 new CountDownTimer(30000, 1000) {
////
////											 @Override
////
////											 public void onTick(long millisUntilFinished) {
////											                //倒數秒數中要做的事
////												 textview81.setText("倒數時間:" + new SimpleDateFormat("s").format(millisUntilFinished));
////											 }
////
////											 @Override
////											 public void onFinish() {
////											                 //倒數完成後要做的事
////													runOnUiThread(new Runnable() {
////														public void run() {
////														textview8.setVisibility(View.VISIBLE);
////														textview8.setTextSize(28);
////														textview8.setTextColor(Color.BLUE);
////														textview8.setText("剩餘（汽車）車位數:" + data2.AVAILABLECAR);
////									
////														}
////													});
////											 }
////											}.start();
////									 }
////									}.start();	
//								}
//							});
//						
//							}
//							
//						}
//						
//					
//
//
//						allData.add(data2);
//						
//					}
//
//				} catch (JSONException e) {
//					Log.e("Jack", "JSONException:" + e);
//				}
//
//				return allData;
//			} catch (MalformedURLException e) {
//				Log.e("test", "MalformedURLException:" + e);
//			} catch (IOException e) {
//				Log.e("test", "IOException:" + e);
//			} finally {
//
//				try {
//					if (br != null)
//						br.close();
//				} catch (IOException e) {
//					Log.e("Howard", "IOException:" + e);
//				}
//			}
//			Log.d("test", "JSON:" + sb);
//
//			return null;
//
//		}
//
//	}
	 
}
