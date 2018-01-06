package com.smartlab.tsu.test;

import java.io.Serializable;

public class DataInfoModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private int sensor;
	
	private long num;
	
	private String time;
	
	private int data;
	
	public DataInfoModel() {
		super();
	}

	public DataInfoModel(int sensor, long num, String time, int data) {
		super();
		this.sensor = sensor;
		this.num = num;
		this.time = time;
		this.data = data;
	}

	public int getSensor() {
		return sensor;
	}

	public void setSensor(int sensor) {
		this.sensor = sensor;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DataInfoModel [sensor=" + sensor + ", num=" + num + ", time=" + time + ", data=" + data + "]";
	}

}
