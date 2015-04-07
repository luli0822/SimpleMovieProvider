package com.ll.android.simplemovieprovider;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class MoviePostApplication extends Application {

	private static final String TAG = "MoviePostApplication";
	private static MoviePostApplication mAppInstance;	
	
	public MoviePostApplication()
	{
		mAppInstance = this;
	}
	
	public static MoviePostApplication getInstance()
	{
		return mAppInstance;
	}
	
	public static Context getContext()
	{
		return mAppInstance;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		doPackaging();

	}
	
	private void doPackaging()
	{
		String pkgName = null;
		String appName = null;
		String appVer = null;
		try
		{
			PackageManager pkgMgr = getPackageManager();
			pkgName = getPackageName();
			ApplicationInfo appInfo = getApplicationInfo();
			appName = pkgMgr.getApplicationLabel(appInfo).toString();
			appVer = pkgMgr.getPackageInfo(pkgName, 0).versionName;
		}
		catch(Exception e)
		{
			Log.e(TAG, 
				"Caught exception getting app name from PackageManager", e);
		}
		
	}	
}
