package com.laha.smartarrival.model;

public class SmartADResponse {
	private RemoteControl data;
	private String status;
	@Override
	public String toString() {
		return "SmartADResponse [data=" + data + ", status=" + status + "]";
	}
	public RemoteControl getData() {
		return data;
	}
	public void setData(RemoteControl data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
