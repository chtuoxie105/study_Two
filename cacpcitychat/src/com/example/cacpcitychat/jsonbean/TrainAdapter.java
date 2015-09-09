package com.example.cacpcitychat.jsonbean;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cacpcitychat.main.R;

public class TrainAdapter extends BaseAdapter {
	private List<AllDataBean> trainList = new ArrayList<AllDataBean>();
	private LayoutInflater infalter;
	private Context context;

	public TrainAdapter(Context contexts) {
		infalter = LayoutInflater.from(contexts);
		context = contexts;
	}

	public void setData(List<AllDataBean> trainLists) {
		trainList = trainLists;
		notifyDataSetChanged();
	}

	public int getCount() {
		return trainList.size();
	}

	public Object getItem(int position) {
		return trainList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ResrouceTrainLayout mResrouceTrainLayout;
		if (convertView == null) {
			convertView = infalter
					.inflate(R.layout.list_train_msg_layout, null);
			mResrouceTrainLayout = new ResrouceTrainLayout();
			mResrouceTrainLayout.addressTextOne = (TextView) convertView
					.findViewById(R.id.train_start_address_and_end_address_one);
			mResrouceTrainLayout.timeTextOne = (TextView) convertView
					.findViewById(R.id.train_start_time_and_end_time_one);
			mResrouceTrainLayout.trainNumberTex = (TextView) convertView
					.findViewById(R.id.train_number);

			convertView.setTag(mResrouceTrainLayout);
		} else {
			mResrouceTrainLayout = (ResrouceTrainLayout) convertView.getTag();
		}
		AllDataBean mTrainBean = (AllDataBean) getItem(position);
		Log.i("11", mTrainBean.getTrainnum());
		if (!mTrainBean.getTrainnum().equals(" ")) {
			mResrouceTrainLayout.trainNumberTex.setText(mTrainBean
					.getTrainnum() + ":");
			
		}
		mResrouceTrainLayout.addressTextOne.setText(mTrainBean.getStart() + "-"
				+ mTrainBean.getTerminal());
		mResrouceTrainLayout.timeTextOne.setText(mTrainBean.getStarttime()
				+ "-" + mTrainBean.getEndtime());
		
		return convertView;
	}

	class ResrouceTrainLayout {
		public TextView addressTextOne;
		public TextView timeTextOne;
		public TextView trainNumberTex;
	}
}
