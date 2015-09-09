package com.example.cacpcitychat.httptool;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class ChatHttpGetToolJSON {
	private BitMapToChat mBitMapToChat;
	public static ChatHttpGetToolJSON mChatHttpGetToolJSON = null;
	public static ChatHttpGetToolJSON getIntance() {
		
		if (mChatHttpGetToolJSON == null) {
			mChatHttpGetToolJSON = new ChatHttpGetToolJSON();
		}
		
		return mChatHttpGetToolJSON;
	}	
	
	public String getHttpData(String urls, HashMap<String, String> msgMap) throws MyException {
		try {
			Log.i("11","崩溃前>>>1");
			HttpClient httpClient = new DefaultHttpClient();
			Log.i("11","崩溃前>>>2");

			// 组装GET请求参数
			if (urls.indexOf("?") < 0) {
				urls = urls + "?";
			}
			Log.i("11","崩溃前>>>3");
			Set<String> set = msgMap.keySet();
			Iterator<String> itertor = set.iterator();
			while (itertor.hasNext()) {
				String key = itertor.next();
				String content = msgMap.get(key);
				urls = urls + "&" + key + "=" + content;
			}
			Log.i("11","崩溃前>>>4");
			HttpGet httpGet = new HttpGet(urls);
			Log.i("11","httpClient>>>"+httpClient);
			Log.i("11","httpGet>>>"+httpGet);
			Log.i("11","urls>>>"+urls);
			Log.i("11","连接前");
			
			HttpResponse response = httpClient.execute(httpGet);
			
			Log.i("11","连接完成");
			Log.i("11","连接完成"+response);
			
			int n = response.getStatusLine().getStatusCode();
			if (n == HttpStatus.SC_OK) {
				String line = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				return line;
			}

		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
			throw new MyException("网络连接异常!");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("网络连接异常!");
		}

		return null;
	}
	
	/**定义接口回调，返回图片*/
	public interface BitMapToChat{
		/**接口回调方法:返回图片异步获取的图片，用于新闻图片*/
		public void backBitMapToChat(Bitmap bitmap);
	}
	private Executor mExecutor;
	
	
	/**开启异步线程去网络获取图片*/
	public void httpGetJSONMap(String urlStr,BitMapToChat mBitMapToChats) {
		mBitMapToChat = mBitMapToChats;
		new AsyncTask<String, Void, Bitmap>() {
			protected Bitmap doInBackground(String... params) {
				Log.i("11", "放地址前"+params[0]);
				Bitmap bitmap = getImageForhttp(params[0]);
				Log.i("11","加工完返回");
				mBitMapToChat.backBitMapToChat(bitmap);
				return bitmap;
			}
			protected void onPostExecute(Bitmap result) {
				Log.i("11","异步返回的图片"+result);
//				mBitMapToChat.backBitMapToChat(result);
			}
		}.execute(urlStr);
		
	}

	/**
	 * 从网络上获得图片
	 * 
	 */

	public Bitmap getImageForhttp(String s) {
		try {
			URL url = new URL(s);
			InputStream input = url.openStream();
			Bitmap bitmap = BitmapFactory.decodeStream(input);
			return bitmap;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
