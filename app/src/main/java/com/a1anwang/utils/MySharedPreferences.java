package com.a1anwang.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MySharedPreferences {
	private static Context mContext;
	private SharedPreferences.Editor editor;
	private SharedPreferences preferences;

	private final String PREFERENCE_NAME = "com.comtime.bluecard.utils.MySharedPreferences";

	private static class MySharedPreferencesHold {
		/**
		 * 鍗曚緥瀵硅薄瀹炰緥
		 */
		static final MySharedPreferences INSTANCE = new MySharedPreferences();
	}

	public static MySharedPreferences getInstance(Context context) {
		mContext = context;
		return MySharedPreferencesHold.INSTANCE;
	}

	/**
	 * private鐨勬瀯閫犲嚱鏁扮敤浜庨伩鍏嶅鐣岀洿鎺ヤ娇鐢╪ew鏉ュ疄渚嬪寲瀵硅薄
	 */
	private MySharedPreferences() {
		preferences = mContext.getSharedPreferences(PREFERENCE_NAME, 0);
		editor = preferences.edit();
	}

	/**
	 * readResolve鏂规硶搴斿鍗曚緥瀵硅薄琚簭鍒楀寲鏃跺��
	 */
	private Object readResolve() {
		return getInstance(mContext);
	}
 

	
	public void saveBleDeviceMAC(String mac){
		editor.putString("BleDeviceMAC", mac);
		editor.commit();
	}
	public String getBleDeviceMAC(){
		return preferences.getString("BleDeviceMAC", "");
	}
	
	
	
	public void saveDeviceHasBinded(boolean flag){
		editor.putBoolean ("DeviceHasBinded", flag);
		editor.commit();
	}
	public boolean getDeviceHasBinded(){
		return preferences.getBoolean ("DeviceHasBinded", false);
	}
	

	
	public void saveAccessKey(String mac){
		editor.putString("AccessKey", mac);
		editor.commit();
	}
	public String getAccessKey(){
		return preferences.getString("AccessKey", "");
	}
	
}
