package com.jackpan.taiwamrain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import Appkey.MyAdKey;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;



import com.adlocus.PushAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import com.jackpan.findchurch.R;
import com.jackpan.taiwamrain.ui.SimpleDialog;
import com.widget.VersionChecker;
//import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends Activity implements android.location.LocationListener{
	private ListView petlist;
	// private ArrayAdapter<String>petadp;
	// private List<String>listpet =new ArrayList<String>();
	HashMap<String, ArrayList<ResultData>> mKind;
	HashMap<String, ArrayList<String>> mCity;
	private ArrayList<ResultData> mAllData = new ArrayList<ResultData>();
	private TextView numtext;
	private MyAdapter mAdapter;
	private ArrayAdapter<String> mAdapter2= null;
	private boolean isCencel = false;
	private ProgressDialog progressDialog;
//	private InterstitialVideoReq mInterstitialVideoReq;
	private Spinner mSpinner,mSpinner2;
	private Date myDate;
	private InterstitialAd interstitial;
	private EditText mEdittext;
	private Button idButton;
	private boolean isFirstUse = true;
	private String jsonUrl;
	private Bundle bundle;
	private String num ;
	private String storeName;
	private LocationManager locationMgr;
	private String Longitude;
	private String Latitude ;
	private double latList,lonList;
	private TextView mDaytex;
	private Calendar  calendar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// //開啟全螢幕
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// //設定隱藏APP標題
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		this.locationMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 螢幕一直亮
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// 不彈跳出鍵盤
		progressDialog = ProgressDialog.show(MainActivity.this, "讀取中",
				"目前資料量比較龐大，請耐心等候！！", false, false,
				new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				//
				isCencel = true;
				finish();
			}
		});
		 calendar = Calendar.getInstance();

//		VersionChecker.checkOnce(this, new VersionChecker.DoneAdapter() {
//
//			@Override
//			public void onHasNewVersion() {
//
//				SimpleDialog.showAlert(getSupportFragmentManager(), getString(R.string.ur_gen_alert_update_app), new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//
//						startActivity(VersionChecker.openMartketIntent());
//
//					}
//				});
//			}
//
//
//		});
		Intent promotionIntent = new Intent(this, MainActivity.class);
		PushAd.enablePush(this, MyAdKey.AdLoucskey, promotionIntent );
//				PushAd.test(this);
				boolean isFirstUse = MySharedPreferences.getIsFirstUsed(this);
				if (isFirstUse) {
					new AlertDialog.Builder(this)
							.setTitle("歡迎使用本程式！！")
							.setMessage(getResources().getString(R.string.title_name))
							.setPositiveButton("確定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,int which) {
											MySharedPreferences.saveIsFirstUsed(MainActivity.this);
											dialog.dismiss();
										}
									}).show();
				}

		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(MyAdKey.AdmobinterstitialBannerId);
		interstitial.loadAd(adRequest);
		petlist = (ListView) findViewById(R.id.listView1);

		mSpinner = (Spinner) findViewById(R.id.spinner);
		mSpinner2 = (Spinner) findViewById(R.id.spinner2);
		mDaytex = (TextView) findViewById(R.id.daytex);

		petlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent petint = new Intent(MainActivity.this, TwoActivity.class);
				petint.putExtra("json",
						new Gson().toJson(mAdapter.mDatas.get(position)));
				petint.putExtra("Longitude", Longitude);
				petint.putExtra("Latitude", Latitude);
				petint.putExtra("latList", latList);
				
				petint.putExtra("lonList", lonList);
//				Log.e("Jack","latlon:"+mLat+","+mLon);
				startActivity(petint);

			}
		});
		mAdapter = new MyAdapter(mAllData);
//		mAdapter2 = new MyAdapter(new ArrayList<ResultData>());
		petlist.setAdapter(mAdapter);
		LoadNetAsyncTask loadNetAsyncTask = new LoadNetAsyncTask();
		loadNetAsyncTask.execute(MyAdKey.jsondata);

		myDate = new Date();

	}

	@Override
	protected void onResume() {	
		super.onResume();	
				mAdapter.updateData(mAllData);
		// 取得位置提供者，不下條件，讓系統決定最適用者，true 表示生效的 provider
//		
		String provider = this.locationMgr.getBestProvider(new Criteria(), true);
		if (provider == null) {
			Log.e("Jack","沒有 location provider 可以使用");
			return;
			}
			Log.e("Jack","取得 provider - " + provider);
			this.locationMgr.requestLocationUpdates(provider, 0, 0, this);
			Location location = this.locationMgr.getLastKnownLocation(provider);
			if (location == null) {
				Log.e("Jack","未取過 location");
				  location=this.locationMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				  this.onLocationChanged(location);
			return;
			}
			Log.e("Jack","取得上次的 location");
			this.onLocationChanged(location);
	}
	@Override
	protected void onPause() {
	super.onPause();
	Log.e("jack","removeUpdates...");
	this.locationMgr.removeUpdates(this);
	}
	public class LoadNetAsyncTask extends
	AsyncTask<String, Void, ArrayList<ResultData>> {

		@Override
		protected void onPostExecute(final ArrayList<ResultData> result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (result == null) {
				new AlertDialog.Builder(MainActivity.this)
				.setTitle("出錯囉!!")
				.setMessage("很抱歉，系統暫時無法提供服務。請您稍後再試～")
				.setPositiveButton("確定",
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						MainActivity.this.finish();
					}
				}).show();
				return;

			}


			final ArrayList<String> kindStrings = new ArrayList<String>(mCity.keySet());
			//			 final ArrayList<String> TowmshipStrings = new
			//			 ArrayList<String>(mTownship.keySet());
			Log.e("Jack", kindStrings.size()+"");
			String id = kindStrings.toString().substring(0,kindStrings.toString().length());
		
			kindStrings.add(0, "無");
			//			 TowmshipStrings.add(0,"無");
			ArrayAdapter<String> animalKindSpinner = new
					ArrayAdapter<String>(MainActivity.this,R.layout.myspinnerlayout,kindStrings);
			mAdapter2 = new ArrayAdapter<String>(MainActivity.this,R.layout.myspinnerlayout, new ArrayList<String>());


			animalKindSpinner.setDropDownViewResource(R.layout.myspinnerlayout);
			mAdapter2.setDropDownViewResource(R.layout.myspinnerlayout);
			mSpinner.setAdapter(animalKindSpinner);
			mSpinner2.setAdapter(mAdapter2);


			mSpinner.setOnItemSelectedListener(new
					AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int
						position, long id) {
					if (position == 0) {
						mAdapter.updateData(mAllData);
						mSpinner2.setVisibility(View.GONE);
					} else {
						selectSpinner(kindStrings.get(position));
						mSpinner2.setVisibility(View.VISIBLE);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					mAdapter.updateData(mAllData);
				}
			});
			mSpinner2.setOnItemSelectedListener(new
					AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int
						position, long id) {

					String city = (String) mSpinner.getSelectedItem();
					String township = (String) mSpinner2.getSelectedItem();
					Log.e("Jack","city"+city);
					selectSpinner2(city+","+township);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
//					mAdapter2.updateData(mAllData);
				}
			});
			mAllData = result;
			mAdapter.updateData(mAllData);
		}


		@Override
		protected ArrayList<ResultData> doInBackground(String... params) {
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
			try {
				URL url = new URL(params[0]);
				HttpURLConnection httpUrlCon = (HttpURLConnection) url
						.openConnection();
				httpUrlCon.setConnectTimeout(20000);// 連線
				httpUrlCon.setReadTimeout(20000);// 讀取
				InputStream inputStream = httpUrlCon.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				br = new BufferedReader(inputStreamReader);
				String value = null;

				while ((value = br.readLine()) != null) {
					sb.append(value);
				}
				String result = sb.toString();

				ArrayList<ResultData> allData = new ArrayList<ResultData>();
				mKind = new HashMap<String, ArrayList<ResultData>>();//city
				mCity = new HashMap<String,ArrayList<String>>();
				
				try {

//					JSONArray jsonarry = new JSONArray(result.replaceAll("\\},\\s?\\{", ","));
					JSONArray jsonarry2 = new JSONArray(result);
//					JSONObject o = new JSONObject(result);
//				    JSONObject resultObj = o.getJSONObject("result");
//				    JSONArray jsonArray = resultObj.getJSONArray("records");
//					Log.e("Jack", "jsonarry:" + jsonarry.length());
					
	
						for(int i = 0 ; i<jsonarry2.length();i++){
//							JSONArray jsonarry2 =jsonarry.getJSONArray(i);
//							for( int j =0 ; j<jsonarry2.length() ; j++){
//					
//							JSONObject jsonObject = jsonarry.getJSONObject(i);
//						
							JSONObject jsonObject = jsonarry2.getJSONObject(i);
							Log.e("Jack", jsonObject.toString());
					
							
							Gson gson = new Gson();
							final ResultData data = gson.fromJson(jsonObject.toString(),
									ResultData.class);
//							
//							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//							String strDate = sdf.format(myDate);
//							Date d1 = null;
//
//							Date d2 = null;
//							
//							try {
//								d1  =  sdf.parse(strDate);
//								 d2 = sdf.parse(data.date);
//								
//							} catch (ParseException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							Log.e("Jack", d1+"");
//							Log.e("Jack", d2+"");
//							if(d2.before(d1)) continue;
					
							
							String key =data.CHR_Area+","+data.CHR_Name;
							ArrayList<ResultData> animalKind = mKind.get(key);
							if (animalKind == null) {
								animalKind = new ArrayList<ResultData>();

							}
							mKind.put(key, animalKind);
								
							animalKind.add(data);
							
							
							ArrayList<String> towmShip = mCity.get(data.CHR_Area);
							if (towmShip == null) {
								towmShip = new ArrayList<String>();

							}
							mCity.put(data.CHR_Area, towmShip);
							if(!towmShip.contains(data.CHR_Name)) towmShip.add(data.CHR_Name);
							
							  float listdistance = 0;
				                if (Latitude != null && Longitude != null) {
				                	MyApi.mGecoder(MainActivity.this, data.CHR_Address);
				                	
				                    Location crntLocation = new Location("");
				                    crntLocation.setLatitude(Double.parseDouble(Latitude));
				                    crntLocation.setLongitude(Double.parseDouble(Longitude));
				                    Location newLocation = new Location("");
				                    newLocation.setLatitude(MyApi.getGeocoderlat());
				                    newLocation.setLongitude(MyApi.getGeocoderlon());
				                        listdistance = crntLocation.distanceTo(newLocation); // in m
//				                    listdistance = listdistance / 1000;//km
				                       data.KmList = (int) listdistance;
				                    
				                }
//				              
				                Log.e("Jack","KmList"+data.KmList+"");
//				                Log.e("Jack","AVAILABLECAR"+data.AVAILABLECAR);
				
							allData.add(data);
						Collections.sort(allData);
//						
				
//							}
						
					}

				} catch (JSONException e) {
					Log.e("Jack", "JSONException:" + e);
				}

				return allData;
			} catch (MalformedURLException e) {
				Log.e("test", "MalformedURLException:" + e);
			} catch (IOException e) {
				Log.e("test", "IOException:" + e);
			} finally {

				try {
					if (br != null)
						br.close();
				} catch (IOException e) {
					Log.e("Howard", "IOException:" + e);
				}
			}
			Log.d("test", "JSON:" + sb);

			return null;

		}

	}

	public void EditSelect(String id) {
		ArrayList<ResultData> idList = mKind.get(id);
		mAdapter.updateData(idList);

	}

	@SuppressLint("NewApi")
	public void selectSpinner(String kinds) {
		ArrayList<String> kindList = mCity.get(kinds);
		Log.e("Jack", "kindList:"+kindList.toString());
		mAdapter2.clear();
//		for (ResultData resultData : kindList) {
//			Log.e("Jack",resultData.Township.toString());
//			
//		}
		mAdapter2.addAll(kindList);
		
	}
	public void selectSpinner2(String kinds) {
		
		ArrayList<ResultData> kindList = mKind.get(kinds);
		mAdapter.updateData(kindList);

	}

	public class MyAdapter extends BaseAdapter {
		private ArrayList<ResultData> mDatas;

		public MyAdapter(ArrayList<ResultData> datas) {
			mDatas = datas;
		}

		public void updateData(ArrayList<ResultData> datas) {
			mDatas = datas;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			Log.e("Jack", mDatas.size()+"");
	
			return mDatas.size();
		}

		@Override
		public Object getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = LayoutInflater.from(MainActivity.this).inflate(
						R.layout.myptdaylayout, null);
			ResultData data = mDatas.get(position);
			
			// TextView textname = (TextView)
			// convertView.findViewById(R.id.name);
			// TextView list = (TextView)
			// convertView.findViewById(R.id.txtengname);
			// TextView time= (TextView) convertView.findViewById(R.id.bigtext);
			//
			// textname.setText("類型:"+ data.animal_kind);
			// list.setText("體型:"+data.animal_bodytype);
			// time.setText("年紀:"+data.animal_age);
			//
			// ImageView imageView = (ImageView)
			// convertView.findViewById(R.id.photoimg);
			// // loadImage(data.album_file, img);
			// //
			// Glide.with(MainActivity.this).load(data.album_file).into(imageView);
			//
			// Glide.with(MainActivity.this)
			// .load(data.album_file)
			// .centerCrop()
			// .placeholder(R.drawable.nophoto)
			// .crossFade()
			// .into(imageView);

			TextView cityName = (TextView) convertView
					.findViewById(R.id.nametext);
			TextView dayText = (TextView) convertView
					.findViewById(R.id.daytext);
			TextView endTextView  =(TextView)convertView. findViewById(R.id.daytext2);
			TextView numberTextView  =(TextView)convertView. findViewById(R.id.numbertext);

			endTextView.setText("地址:" +data.CHR_Address);
			cityName.setText("行政區:" + data.CHR_Area);
			dayText.setText("教會堂所名稱:" + data.CHR_Name);

			mDaytex.setVisibility(View.GONE);
			//			if(data.班次21.equals("null")){
			//				data.班次21="_";
			//			}
//			if(data.Type()){
//				String typeStr ="即時動態停車場";
//				cityName.setText("區域:" + data.AREA+"\n\n"+typeStr);
//				cityName.setTextColor(Color.RED);
//			}else{
//				cityName.setTextColor(Color.BLACK);
//				cityName.setText("區域:" + data.AREA);
//			}
			
//			int lv = Integer.parseInt(data.Now);
//			if( lv >=40){
//				endTextView.setTextColor(Color.BLUE);
//				endTextView.setText("日累積雨量:" + data.Now+"mm"+"_大雨特報");
//				
//			}
//			
			if(data.KmList==0)numberTextView.setVisibility(View.GONE);
			else 
				if(data.KmList<=999)numberTextView.setText("距離約:" + new DecimalFormat("0.0").format(data.KmList)+"m");
				else numberTextView.setText("距離約:"+new DecimalFormat("0.00").format(data.KmList/1000.00)+"km");
			Log.e("JacK", data.KmList+"");
	
//			numberTextView.setVisibility(View.GONE);
			return convertView;

		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK)) { // 確定按下退出鍵

			ConfirmExit(); // 呼叫ConfirmExit()函數

			return true;

		}

		return super.onKeyDown(keyCode, event);

	}

	public void ConfirmExit() {

		AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this); // 創建訊息方塊

		ad.setTitle("離開");

		ad.setMessage("確定要離開?");

		ad.setPositiveButton("是", new DialogInterface.OnClickListener() { // 按"是",則退出應用程式

			public void onClick(DialogInterface dialog, int i) {
				MainActivity.this.finish();// 關閉activity
				interstitial.show();

			}

		});

		ad.setNegativeButton("否", new DialogInterface.OnClickListener() { // 按"否",則不執行任何操作

			public void onClick(DialogInterface dialog, int i) {

			}

		});

		ad.show();// 顯示訊息視窗

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	public void resignKeyboard(Activity activity)
	{
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm == null) return;
		View focusView = activity.getCurrentFocus();
		if (focusView == null) return;
		imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.e("Jack", "onLocationChanged...");
		String msg = "經度: " + location.getLongitude() + ", 緯度: "
		+ location.getLatitude();
		Log.e("Jack", msg);
		Latitude =Double.toString(location.getLatitude());
		Longitude = Double.toString(location.getLongitude());
	}
	
	 
}