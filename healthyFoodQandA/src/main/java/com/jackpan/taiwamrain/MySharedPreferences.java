package com.jackpan.taiwamrain;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

	public static final String NAME = "MydSharedPrefernces";
	public static final String KEY_FIRST_USED = "Key_first_used";
	public static final String KEY_OPEN_GPS = "Key_open_gps";

	//首頁-是否第一次使用public static final String KEY_FIRST_USED = "isFirstUsed";
	public static void saveIsFirstUsed(Context context) {
	   SharedPreferences sp = context.getSharedPreferences(NAME, Activity.MODE_PRIVATE);
	   sp.edit().putBoolean(KEY_FIRST_USED, false).commit();
	}

	public static boolean getIsFirstUsed(Context context) {
	   SharedPreferences sp = context.getSharedPreferences(NAME, Activity.MODE_PRIVATE);
	   return sp.getBoolean(KEY_FIRST_USED, true);
	}
	//判斷是否開啟Gps 
	public static void saveIsGPSState(Context context,boolean bo) {
		   SharedPreferences sp = context.getSharedPreferences(KEY_OPEN_GPS, Activity.MODE_PRIVATE);
		   sp.edit().putBoolean(KEY_OPEN_GPS, bo).commit();
		}
	public static boolean getIsGPSState(Context context) {
		   SharedPreferences sp = context.getSharedPreferences(KEY_OPEN_GPS, Activity.MODE_PRIVATE);
		   boolean bo = sp.getBoolean(KEY_OPEN_GPS, false);
		   return bo;
		}
	
	}
