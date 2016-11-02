package com.jackpan.taiwamrain;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MyApi {
	private static double lon0 = 121 * Math.PI / 180;
	 private static  double k0 = 0.9999;
	 private static int dx = 250000;
	  private static int dy = 0;
	  private static double a = 6378137.0;
	  private static double b = 6356752.3142451;
	  private static double e = 1 - Math.pow(b, 2) / Math.pow(a, 2);
	  private  static double e2 = (1 - Math.pow(b, 2) / Math.pow(a, 2)) / (Math.pow(b, 2) / Math.pow(a, 2));
	  private static double mLat;
	  private static double mLon;
	  private static double mLatitude;
	  private static double mLongitude;
	  //
	  private static double Geocoderlat,Geocoderlon;
	  private static Bitmap bitmap = null;
	  
	  
	 public static String cal_TWD97_To_lonlat(double x, double y)
	  {
	    x -= dx;
	    y -= dy;

	    // Calculate the Meridional Arc
	    double M = y / k0;

	    // Calculate Footprint Latitude
	    double mu = M / (a * (1.0 - e / 4.0 - 3 * Math.pow(e, 2) / 64.0 - 5 * Math.pow(e, 3) / 256.0));
	    double e1 = (1.0 - Math.sqrt(1.0 - e)) / (1.0 + Math.sqrt(1.0 - e));

	    double J1 = (3 * e1 / 2 - 27 * Math.pow(e1, 3) / 32.0);
	    double J2 = (21 * Math.pow(e1, 2) / 16 - 55 * Math.pow(e1, 4) / 32.0);
	    double J3 = (151 * Math.pow(e1, 3) / 96.0);
	    double J4 = (1097 * Math.pow(e1, 4) / 512.0);

	    double fp = mu + J1 * Math.sin(2 * mu) + J2 * Math.sin(4 * mu) + J3 * Math.sin(6 * mu) + J4 * Math.sin(8 * mu);

	    // Calculate Latitude and Longitude
	    double C1 = e2 * Math.pow(Math.cos(fp), 2);
	    double T1 = Math.pow(Math.tan(fp), 2);
	    double R1 = a * (1 - e) / Math.pow((1 - e * Math.pow(Math.sin(fp), 2)), (3.0 / 2.0));
	    double N1 = a / Math.pow((1 - e * Math.pow(Math.sin(fp), 2)), 0.5);

	    double D = x / (N1 * k0);

	    // 計算緯度
	    double Q1 = N1 * Math.tan(fp) / R1;
	    double Q2 = (Math.pow(D, 2) / 2.0);
	    double Q3 = (5 + 3 * T1 + 10 * C1 - 4 * Math.pow(C1, 2) - 9 * e2) * Math.pow(D, 4) / 24.0;
	    double Q4 = (61 + 90 * T1 + 298 * C1 + 45 * Math.pow(T1, 2) - 3 * Math.pow(C1, 2) - 252 * e2) * Math.pow(D, 6) / 720.0;
	    double lat = fp - Q1 * (Q2 - Q3 + Q4);

	    // 計算經度
	    double Q5 = D;
	    double Q6 = (1 + 2 * T1 + C1) * Math.pow(D, 3) / 6;
	    double Q7 = (5 - 2 * C1 + 28 * T1 - 3 * Math.pow(C1, 2) + 8 * e2 + 24 * Math.pow(T1, 2)) * Math.pow(D, 5) / 120.0;
	    double lon = lon0 + (Q5 - Q6 + Q7) / Math.cos(fp);

	    lat = (lat * 180) / Math.PI; //緯度
	    lon = (lon * 180) / Math.PI; //經度
	    String lonlat = lon + "," + lat;
	    mLat = lat;
	    mLon = lon;
//	    Log.e("Jack",lonlat);
	    return lonlat;
	  }
	 public static double showLat(){	 
		 return mLat; 
		 
	 }
	 public static double showLon(){	 
		 return mLon; 
		 
	 }
	 
	 //網路下載圖片
		public static  void loadImage(final Activity activity,final String path, final ImageView imageView) {

			new Thread() {

				@Override
				public void run() {

					try {
						URL imageUrl = new URL(path);
						HttpURLConnection httpCon = (HttpURLConnection) imageUrl
								.openConnection();
						InputStream imageStr = httpCon.getInputStream();
						bitmap = decodeSampledBitmapFromResourceMemOpt(imageStr,
								640, 640);


						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								imageView.setImageBitmap(bitmap);
							}
						});

					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						Log.e("Howard", "MalformedURLException:" + e);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						Log.e("Howard", "IOException:" + e);
					}

				}

			}.start();

		}
		
		
		public static int calculateInSampleSize(BitmapFactory.Options options,
				int reqWidth, int reqHeight) {
			// Raw height and width of image
			final int height = options.outHeight;
			final int width = options.outWidth;
			int inSampleSize = 1;

			if (height > reqHeight || width > reqWidth) {

				final int halfHeight = height / 2;
				final int halfWidth = width / 2;

				// Calculate the largest inSampleSize value that is a power of 2 and
				// keeps both
				// height and width larger than the requested height and width.
				while ((halfHeight / inSampleSize) > reqHeight
						&& (halfWidth / inSampleSize) > reqWidth) {
					inSampleSize *= 2;
				}
			}

			return inSampleSize;
		}

		public static Bitmap decodeSampledBitmapFromResourceMemOpt(
				InputStream inputStream, int reqWidth, int reqHeight) {

			byte[] byteArr = new byte[0];
			byte[] buffer = new byte[1024];
			int len;
			int count = 0;

			try {
				while ((len = inputStream.read(buffer)) > -1) {
					if (len != 0) {
						if (count + len > byteArr.length) {
							byte[] newbuf = new byte[(count + len) * 2];
							System.arraycopy(byteArr, 0, newbuf, 0, count);
							byteArr = newbuf;
						}

						System.arraycopy(buffer, 0, byteArr, count, len);
						count += len;
					}
				}

				final BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeByteArray(byteArr, 0, count, options);

				options.inSampleSize = calculateInSampleSize(options, reqWidth,
						reqHeight);
				options.inPurgeable = true;
				options.inInputShareable = true;
				options.inJustDecodeBounds = false;
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;

				// int[] pids = { android.os.Process.myPid() };
				// MemoryInfo myMemInfo = mAM.getProcessMemoryInfo(pids)[0];
				// Log.e(TAG, "dalvikPss (decoding) = " + myMemInfo.dalvikPss);

				return BitmapFactory.decodeByteArray(byteArr, 0, count, options);

			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}
		// 地址轉經緯度
		public static void AddChangLatLon(Context context , String addStr){
			Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
			
	        try {
				List<Address> addressLocation = geoCoder.getFromLocationName(addStr, 1);
				  if(addressLocation.size()<=0){
				  
				  }else{
					  mLatitude = addressLocation.get(0).getLatitude();
					  mLongitude = addressLocation.get(0).getLongitude();                
					  
				  }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public static double getMlatitude(){
			return mLatitude;
		}
		public static double getMlongitude(){
			return mLongitude;
		}
	//  地址轉經緯度
		public static void mGecoder(Context context , String addStr){
			
			Geocoder geoCoder = new Geocoder(context, Locale.getDefault());

            List<Address> addressLocation;
			try {
				addressLocation = geoCoder.getFromLocationName(addStr, 1);
			      if(addressLocation.size()<=0){
		                Geocoderlat = 34.058621;
		               Geocoderlon =-118.246938;
		                Log.d("Jack", String.valueOf(Geocoderlat) + "," + String.valueOf(Geocoderlon));

		            }else{

		            	Geocoderlat = addressLocation.get(0).getLatitude();
		            	Geocoderlon = addressLocation.get(0).getLongitude();
		                Log.d("Jack", String.valueOf(Geocoderlat) + "," + String.valueOf(Geocoderlon));
		               

		            }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      

			
		}	
		public static double getGeocoderlat()
		{
			return Geocoderlat;
		}
		public static double getGeocoderlon()
		{
			return Geocoderlon;
		}
}
