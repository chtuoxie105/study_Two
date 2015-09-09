package com.example.cacpcitychat.httptool;

import com.example.cacpcitychat.main.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class ShowWebView extends Activity {
	private WebView mWebView;

	// http://www.12308.com/
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_webview_layout);

		mWebView = (WebView) findViewById(R.id.show_click_webview);

		Intent intent = getIntent();
		int n = intent.getIntExtra("difference_class", 0);

	}
}
