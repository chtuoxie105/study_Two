package com.example.cacpcitychat.jsonbean;

import android.graphics.Bitmap;

public class AllDataBean {
	//---------区分哪种类别的------------
	private int difference;
	
	//------------文本的--------
	private int point;
	private String msg;
	//--------------火车的--------
	private String urls;
	private String trainnum;
	private String start;
	private String terminal;
	private String icon;
	private String detailurl;
	
	//---------列车、飞机:公有的---------------
	private String starttime;
	private String endtime;
	
	//----------飞机的-----------------
	private String flight;
	
	//----------新闻-------------------
	private Bitmap newsImg;
	private String newsContentTxt;
	private String newsSiteText;
	private String newsImgId;
	
	
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
	}
	public int getDifference() {
		return difference;
	}
	public void setDifference(int difference) {
		this.difference = difference;
	}
	
	public String getTrainnum() {
		return trainnum;
	}
	public void setTrainnum(String trainnum) {
		this.trainnum = trainnum;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getDetailurl() {
		return detailurl;
	}
	public void setDetailurl(String detailurl) {
		this.detailurl = detailurl;
	}
	
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUrls() {
		return urls;
	}
	public void setUrls(String urls) {
		this.urls = urls;
	}
	public Bitmap getNewsImg() {
		return newsImg;
	}
	public void setNewsImg(Bitmap newsImg) {
		this.newsImg = newsImg;
	}
	public String getNewsImgId() {
		return newsImgId;
	}
	public void setNewsImgId(String newsImgId) {
		this.newsImgId = newsImgId;
	}
	public String getNewsContentTxt() {
		return newsContentTxt;
	}
	public void setNewsContentTxt(String newsContentTxt) {
		this.newsContentTxt = newsContentTxt;
	}
	public String getNewsSiteText() {
		return newsSiteText;
	}
	public void setNewsSiteText(String newsSiteText) {
		this.newsSiteText = newsSiteText;
	}
}
