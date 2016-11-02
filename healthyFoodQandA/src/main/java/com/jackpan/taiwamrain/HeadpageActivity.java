package com.jackpan.taiwamrain;

import java.util.Timer;
import java.util.TimerTask;

import Appkey.MyAdKey;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adlocus.PushAd;
import com.jackpan.findchurch.R;


		import com.igexin.sdk.PushManager;
public class HeadpageActivity extends Activity {

	private boolean change = false;
	private Button but1;
	public ProgressDialog myDialog;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 開啟全螢幕	
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 設定隱藏APP標題
		setContentView(R.layout.activity_headpage);
//		
		PushManager.getInstance().initialize(this.getApplicationContext());
//		
//		//取得系統定位服務
//		LocationManager status = (LocationManager) (this.getSystemService(Context.LOCATION_SERVICE));
//		if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) 
//		
//		{
//			
//			Log.e("Jack","有開啟定位服務");
////			MySharedPreferences.saveIsGPSState(HeadpageActivity.this,true);
//		} else {
////			Log.e("Jack","請開啟定位服務");
////			Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
//			new AlertDialog.Builder(this)
//			.setTitle("開啟GPS")
//			.setMessage("開啟GPS能使用導航功能，是否要開啟導航功能的功能！")
//			.setPositiveButton("是，請開啟", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
////					MySharedPreferences.saveIsGPSState(HeadpageActivity.this,true);//開啟設定頁面
//				}
//			})
//			.setNegativeButton("不用了，謝謝", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//
//					
//				}
//			})
//			.show();
//		
//		}
		ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networInfo = conManager.getActiveNetworkInfo();       

		if (networInfo == null || !networInfo.isAvailable()){ 
			new AlertDialog.Builder(HeadpageActivity.this)
			.setTitle(getString(R.string.Network_status))
			.setMessage(getString(R.string.no_network))
			.setCancelable(false)
			.setPositiveButton(getString(R.string.setting), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent settintIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
					startActivity(settintIntent);

				}
			})
			.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					HeadpageActivity.this.finish();
				}
			})
			.show();

		}else{
			final ProgressDialog progressDialog =ProgressDialog.show(HeadpageActivity.this, getString(R.string.Network_in),getString(R.string.waitting));
			final Handler handler = new Handler();
			final Runnable runnable = new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new AlertDialog.Builder(HeadpageActivity.this);

					progressDialog.dismiss();

				}
			};	
			

			Thread thread = new Thread(){

				@Override
				public void run() {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					handler.post(runnable);
				}
			};
			thread.start();	

		}

		final TextView test = (TextView) findViewById(R.id.textView1);
		Timer timer = new Timer();

		TimerTask task = new TimerTask() {
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						if (change) {
							change = false;
							test.setTextColor(Color.TRANSPARENT); // 讓文字透明
						} else {
							change = true;
							test.setTextColor(Color.DKGRAY);
						}
					}
				});
			}
		};
		timer.schedule(task, 1, 800); // 參數分別是(timer需要做什麼事情，執行後多久開始執行，閃爍速度多快)
		GetButtonView();
		setButtonEvent();
	}

	public void GetButtonView() {
		but1 = (Button) findViewById(R.id.button1);
	}

	public void setButtonEvent() {
		but1.setOnClickListener(buttonListener);
	}

	private Button.OnClickListener buttonListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
//			
//			Intent intent = new Intent();
//			intent.setClass(HeadpageActivity.this, MainActivity.class);
//			startActivity(intent);
//			finish();
			myDialog = ProgressDialog.show(HeadpageActivity.this, "請稍後片刻....",
					"正在載入中", true);
			new Thread() {
				public void run() {
					try {
						sleep(1000);
						Intent intent = new Intent();
						intent.setClass(HeadpageActivity.this, MainActivity.class);// Testone跳到Testtwo這個Activity
						startActivity(intent);
						finish();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {

						myDialog.dismiss();
					}
				}
			}.start();

		}

	};

}
