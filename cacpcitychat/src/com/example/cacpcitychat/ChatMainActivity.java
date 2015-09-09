package com.example.cacpcitychat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.example.cacpcitychat.httptool.ChatHttpGetToolJSON;
import com.example.cacpcitychat.httptool.MyException;
import com.example.cacpcitychat.httptool.RequestManager;
import com.example.cacpcitychat.jsonbean.AllDataBean;
import com.example.cacpcitychat.main.R;

public class ChatMainActivity extends Activity implements OnClickListener {

	public ChatHttpGetToolJSON mChatHttpGetToolJSON;
	public String url = "http://www.tuling123.com/openapi/api";
	HashMap<String, String> mapData = new HashMap<String, String>();

	private ListView mShowChatMsgListV;
	private Button mSendMsgBtn;
	private EditText mWriteMsgEditText;

	private MsgAdapter mMsgAdapter;
	private List<AllDataBean> allListBean = new ArrayList<AllDataBean>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chat_main);

		mChatHttpGetToolJSON = ChatHttpGetToolJSON.getIntance();

		mShowChatMsgListV = (ListView) findViewById(R.id.cacpcitychat_msg_content_listview);
		mSendMsgBtn = (Button) findViewById(R.id.cacpcitychat_send_message_btn);
		mWriteMsgEditText = (EditText) findViewById(R.id.cacpcitychat_write_message_edit_text);
		mMsgAdapter = new MsgAdapter(this);
		mShowChatMsgListV.setAdapter(mMsgAdapter);

		mSendMsgBtn.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v.getId() == R.id.cacpcitychat_send_message_btn) {
			boolean httpUse = ispectHttpUse();
			if (httpUse == true) {
				if (!mWriteMsgEditText.getText().toString().equals("")) {
					mapData.put("key", "e7b5991437d8c9e8d5017cb8a15f3493");

					// 这个是放到异步线程中取数据的将要发送的消息放到
					// mapData.put("info", "明天成都到北京的飞机");
					mapData.put("info", mWriteMsgEditText.getText().toString());

					creatThrea(url, mapData);

					// JsonString(url);
					// 这个是：放到适配器的
					AllDataBean bean = new AllDataBean();
					bean.setPoint(2);
					bean.setMsg(mWriteMsgEditText.getText().toString());

					allListBean.add(bean);
					mMsgAdapter.setData(allListBean);
					mShowChatMsgListV.setSelection(mShowChatMsgListV
							.getBottom());

					mWriteMsgEditText.setText("");

				} else {
					Toast.makeText(ChatMainActivity.this, "不能发送空消息", 0).show();
				}
			} else {
				Toast.makeText(ChatMainActivity.this, "当前网络不可用", 0).show();
			}
		}
	}

	public void JsonString(String url) {
		StringRequest request = new StringRequest(Method.GET, url,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						getJSon(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(
								ChatMainActivity.this,
								VolleyErrorHelper.getMessage(error,
										ChatMainActivity.this), 0).show();
					}
				});
		RequestManager.getRequestQueue().add(request);
	}

	/**
	 * 异步线程，并发送消息，解析返回的JSON格式信息，并添加到适配器，显示
	 * 
	 * @param urlAddress
	 * @param map
	 */
	public void creatThrea(final String urlAddress,
			final HashMap<String, String> map) {
		new AsyncTask<String, Void, String>() {
			protected String doInBackground(String... params) {
				String urlHttp = params[0];
				String jsonStr = null;
				try {
					jsonStr = mChatHttpGetToolJSON.getHttpData(urlHttp, map);
				} catch (MyException e) {
					e.printStackTrace();
				}
				return jsonStr;
			}

			// 明天成都到北京的飞机
			protected void onPostExecute(String result) {
				if (!result.equals("") && result != null) {
					try {
						JSONObject jsonObject = new JSONObject(result);
						int JSONCode = jsonObject.getInt("code");

						if (JSONCode == 100000) {

							textClassChat(jsonObject);// 文本类

						} else if (JSONCode == 306000) {
							planeClassChat(jsonObject);// 飞机类
						} else if (JSONCode == 305000) {

							trainClassChat(jsonObject);// 列车 、火车类

						} else if (JSONCode == 20000) {

							chainingClassChat(jsonObject);// 链接类

						} else if (JSONCode == 302000) {

							chainingClassChat(jsonObject);// 新闻

						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}

		}.execute(urlAddress);
	}

	public void getJSon(String result) {
		if (!result.equals("")) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				int JSONCode = jsonObject.getInt("code");
				if (JSONCode == 100000) {
					textClassChat(jsonObject);// 文本类
				} else if (JSONCode == 306000) {
					planeClassChat(jsonObject);// 飞机类
				} else if (JSONCode == 305000) {
					trainClassChat(jsonObject);// 列车 、火车类
				} else if (JSONCode == 20000) {
					chainingClassChat(jsonObject);// 链接类
				} else if (JSONCode == 302000) {
					chainingClassChat(jsonObject);// 新闻
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 链接的网址
	 */
	public void chainingClassChat(JSONObject jsonObjects) throws JSONException {
		String JSONText = jsonObjects.getString("text");
		String JSONUrl = jsonObjects.getString("url");

		JSONArray list = jsonObjects.getJSONArray("list");
		int n = list.length();
		AllDataBean bean = new AllDataBean();

		bean.setDifference(20000);
		bean.setPoint(1);
		bean.setMsg(JSONText);
		bean.setUrls(JSONUrl);
		allListBean.add(bean);

		mMsgAdapter.setData(allListBean);
	}

	public void ceshi() {
		AllDataBean planeBean = new AllDataBean();

		planeBean.setPoint(1);
		planeBean.setDifference(306000);
		planeBean.setFlight("flight");
		planeBean.setStarttime("starttime");
		planeBean.setEndtime("endtime");

		allListBean.add(planeBean);

		mShowChatMsgListV.setAdapter(mMsgAdapter);
		mMsgAdapter.setData(allListBean);
	}

	/**
	 * 文本类
	 * 
	 * @param jsonObjects
	 * @throws JSONException
	 */
	public void textClassChat(JSONObject jsonObjects) throws JSONException {
		String JSONText = jsonObjects.getString("text");

		AllDataBean bean = new AllDataBean();
		bean.setPoint(1);
		bean.setDifference(100000);
		bean.setMsg(JSONText);

		allListBean.add(bean);
		mMsgAdapter.setData(allListBean);
	}

	/**
	 * 新闻类
	 * 
	 * @param jsonObjects
	 * @throws JSONException
	 */
	public void newsClassChat(JSONObject jsonObjects) throws JSONException {
		JSONArray jsonList = jsonObjects.getJSONArray("list");
		int n = jsonList.length();
		for (int i = 0; i < n; i++) {
			JSONObject jsonNews = jsonList.getJSONObject(i);
			final AllDataBean newsBean = new AllDataBean();
			newsBean.setPoint(1);
			newsBean.setDifference(302000);

			newsBean.setNewsContentTxt(jsonNews.getString("article"));
			newsBean.setNewsSiteText(jsonNews.getString("source"));
			newsBean.setNewsImgId(jsonNews.getString("icon"));

			String imsStr = jsonNews.getString("icon");

			// 放入图片地址，采用异步回调机制，返回图片
			mChatHttpGetToolJSON.httpGetJSONMap(imsStr,
					new ChatHttpGetToolJSON.BitMapToChat() {
						public void backBitMapToChat(Bitmap bitmap) {
							newsBean.setNewsImg(bitmap);
						}
					});

			allListBean.add(newsBean);
		}
		mMsgAdapter.setData(allListBean);
	}
	/**
	 * 火车类
	 */
	public void trainClassChat(JSONObject jsonObjects) throws JSONException {
		JSONArray jsonList = jsonObjects.getJSONArray("list");
		int n = jsonList.length();
		for (int i = 0; i < n; i++) {
			JSONObject jsonTrain = jsonList.getJSONObject(i);

			AllDataBean trainBean = new AllDataBean();
			trainBean.setDifference(305000);

			trainBean.setPoint(1);
			trainBean.setStart(jsonTrain.getString("start"));
			trainBean.setTerminal(jsonTrain.getString("terminal"));
			trainBean.setStarttime(jsonTrain.getString("starttime"));
			trainBean.setEndtime(jsonTrain.getString("endtime"));
			trainBean.setDetailurl(jsonTrain.getString("detailurl"));
			trainBean.setTrainnum(jsonTrain.getString("trainnum"));

			allListBean.add(trainBean);
		}
		mMsgAdapter.setData(allListBean);

	}

	/**
	 * 飞机查询
	 */
	public void planeClassChat(JSONObject jsonObjects) throws JSONException {
		JSONArray planeListJSON = jsonObjects.getJSONArray("list");
		int n = planeListJSON.length();
		for (int i = 0; i < n; i++) {
			JSONObject obj = planeListJSON.getJSONObject(i);

			AllDataBean planeBean = new AllDataBean();

			planeBean.setPoint(1);
			planeBean.setDifference(306000);
			planeBean.setFlight(obj.getString("flight"));
			planeBean.setStarttime(obj.getString("starttime"));
			planeBean.setEndtime(obj.getString("endtime"));

			allListBean.add(planeBean);
		}

		mShowChatMsgListV.setAdapter(mMsgAdapter);
		mMsgAdapter.setData(allListBean);
	}

	/**
	 * 先判断当前网络是否可用
	 * 
	 */
	public boolean ispectHttpUse() {

		ConnectivityManager manger = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = manger.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		}
		return false;
	}
}
