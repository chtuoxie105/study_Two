package com.example.cacpcitychat.httptool;


import android.app.Application;

public class MyAplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		RequestManager.init(this);
	}
}
