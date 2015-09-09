package com.example.cacpcitychat;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cacpcitychat.jsonbean.AllDataBean;
import com.example.cacpcitychat.main.R;

public class MsgAdapter extends BaseAdapter {
	private List<AllDataBean> list = new ArrayList<AllDataBean>();
	private LayoutInflater infalter;

	public MsgAdapter(Context contexts) {
		infalter = LayoutInflater.from(contexts);
	}

	public void setData(List<AllDataBean> lists) {
		list = lists;
		notifyDataSetChanged();
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		AllDataBean allBean = (AllDataBean) getItem(position);
		int code = allBean.getDifference();
		
		if (code == 100000 || code == 0) {// 文本类
			convertView = textObj(allBean);

		} else if (code == 305000) {// 火车类
			convertView = trainObj(allBean);

		} else if (code == 306000) {// 飞机类
			convertView = planeObj(allBean);

		} else if (code == 302000) {// 新闻类
			convertView = newsObj(allBean);
		}
		return convertView;
	}

	/** 判断显示谁和隐藏谁 */
	public void showAndHideChat(View convertViews, int showMsgInt,
			Reasource mReasources) {
		Reasource mReasource;
		if (convertViews == null) {
			convertViews = infalter
					.inflate(R.layout.list_show_msg_layout, null);
			mReasource = new Reasource();
			mReasource.tulingText = (TextView) convertViews
					.findViewById(R.id.tuling_msg_text);
			mReasource.writeText = (TextView) convertViews
					.findViewById(R.id.write_msg_text);
			mReasource.tulingImg = (ImageView) convertViews
					.findViewById(R.id.tuling_touxiang_image);
			mReasource.writeImg = (ImageView) convertViews
					.findViewById(R.id.real_touxiang_image);
			convertViews.setTag(mReasource);
		} else {
			mReasource = (Reasource) convertViews.getTag();
		}

		// 显示消息，应该是谁发送
		if (mReasources != null) {
			if (showMsgInt == 1) {
				mReasources.writeText.setVisibility(View.GONE);
				mReasources.writeImg.setVisibility(View.GONE);

			} else if (showMsgInt == 2) {

				mReasources.tulingImg.setVisibility(View.GONE);
				mReasources.tulingText.setVisibility(View.GONE);

			}
		}
		
	}

	/** 处理文类类 */
	public View textObj(AllDataBean bean) {
		View convertView = null;
		Reasource mReasource;
		if (convertView == null) {
			convertView = infalter
					.inflate(R.layout.list_show_msg_layout, null);
			mReasource = new Reasource();
			mReasource.tulingText = (TextView) convertView
					.findViewById(R.id.tuling_msg_text);
			mReasource.writeText = (TextView) convertView
					.findViewById(R.id.write_msg_text);
			mReasource.tulingImg = (ImageView) convertView
					.findViewById(R.id.tuling_touxiang_image);
			mReasource.writeImg = (ImageView) convertView
					.findViewById(R.id.real_touxiang_image);
			convertView.setTag(mReasource);
		} else {
			mReasource = (Reasource) convertView.getTag();
		}
		int nMegInt = bean.getPoint();
		if (nMegInt == 1) {

			mReasource.writeText.setVisibility(View.GONE);
			mReasource.writeImg.setVisibility(View.GONE);

			mReasource.tulingText.setText(bean.getMsg());

		} else if (nMegInt == 2) {
			mReasource.tulingImg.setVisibility(View.GONE);
			mReasource.tulingText.setVisibility(View.GONE);

			mReasource.writeText.setText(bean.getMsg());

		}
		return convertView;
	}

	/** 处理列车类 */
	public View trainObj(AllDataBean bean) {
		View convertView = null;
		Reasource mResrouceTrainLayout;
		if (convertView == null) {
			convertView = infalter.inflate(R.layout.list_train_msg_layout,
					null);
			mResrouceTrainLayout = new Reasource();
			mResrouceTrainLayout.addressTextOne = (TextView) convertView
					.findViewById(R.id.train_start_address_and_end_address_one);
			mResrouceTrainLayout.timeTextOne = (TextView) convertView
					.findViewById(R.id.train_start_time_and_end_time_one);
			mResrouceTrainLayout.trainNumberTex = (TextView) convertView
					.findViewById(R.id.train_number);

			convertView.setTag(mResrouceTrainLayout);
		} else {
			mResrouceTrainLayout = (Reasource) convertView.getTag();
		}
		int nMegInt = bean.getPoint();
		// showAndHideChat(convertViews, nMegInt, mResrouceTrainLayout);
		if (!bean.getTrainnum().equals(" ")) {
			mResrouceTrainLayout.trainNumberTex.setText(bean.getTrainnum()
					+ ":");

		}
		mResrouceTrainLayout.addressTextOne.setText(bean.getStart() + "-"
				+ bean.getTerminal());
		mResrouceTrainLayout.timeTextOne.setText(bean.getStarttime() + "-"
				+ bean.getEndtime());
		return convertView;
	}

	/** 处理飞机类 */
	public View planeObj(AllDataBean bean) {
		Reasource mResrouceTrainLayout;
		View convertView = null;
		if (convertView == null) {
			convertView = infalter.inflate(R.layout.plane_msg_chat_layout,
					null);
			mResrouceTrainLayout = new Reasource();

			mResrouceTrainLayout.planeFlight = (TextView) convertView
					.findViewById(R.id.plane_msg_flight);
			mResrouceTrainLayout.planeStartTime = (TextView) convertView
					.findViewById(R.id.plane_msg_start_time);
			mResrouceTrainLayout.planeEndTime = (TextView) convertView
					.findViewById(R.id.plane_msg_end_time);

			convertView.setTag(mResrouceTrainLayout);
		} else {

			mResrouceTrainLayout = (Reasource) convertView.getTag();
		}
		int nMegInt = bean.getPoint();
		// showAndHideChat(convertViews, nMegInt, mResrouceTrainLayout);
		mResrouceTrainLayout.planeFlight.setText(bean.getFlight());
		mResrouceTrainLayout.planeStartTime.setText(bean.getStarttime() + "-");
		mResrouceTrainLayout.planeEndTime.setText(bean.getEndtime());
		
		return convertView;
	}

	/** 处理新闻类 */
	public View newsObj(AllDataBean bean) {
		Reasource mResrouceTrainLayout;
		View convertView = null;
		if (convertView == null) {
			convertView = infalter
					.inflate(R.layout.list_news_msg_layout, null);
			mResrouceTrainLayout = new Reasource();
			mResrouceTrainLayout.newsContentTxt = (TextView) convertView
					.findViewById(R.id.news_content_msg);

			mResrouceTrainLayout.newsSiteTxT = (TextView) convertView
					.findViewById(R.id.news_msg_address);

			mResrouceTrainLayout.newsImg = (ImageView) convertView
					.findViewById(R.id.chat_news_msg_back_image);

			convertView.setTag(mResrouceTrainLayout);
		} else {
			mResrouceTrainLayout = (Reasource) convertView.getTag();
		}
		int nMegInt = bean.getPoint();
		// showAndHideChat(convertViews, nMegInt, mResrouceTrainLayout);
		String imsId = bean.getNewsImgId();
		
		if (bean.getNewsImg() != null) {
			mResrouceTrainLayout.newsContentTxt.setText(bean
					.getNewsContentTxt());
			mResrouceTrainLayout.newsSiteTxT.setText(bean.getNewsSiteText());
			// mResrouceTrainLayout.newsImg.setImageBitmap(bean.getNewsImg());
		}
		return convertView;
	}

	class Reasource {
		// --------文本类和显示消息的人物显示-----
		public ImageView tulingImg;
		public ImageView writeImg;

		public TextView tulingText;
		public TextView writeText;
		// -----------文本类结束-------

		// -------------列车类的开始----
		public TextView addressTextOne;
		public TextView timeTextOne;
		public TextView trainNumberTex;
		// -----------列车类的结束-------

		// ------------飞机类开始------------
		public TextView planeFlight;
		public TextView planeStartTime;
		public TextView planeEndTime;
		// ------------飞机类结束------------

		// ------------新闻类---------------
		public TextView newsContentTxt;
		public TextView newsSiteTxT;
		public ImageView newsImg;

	}

}
