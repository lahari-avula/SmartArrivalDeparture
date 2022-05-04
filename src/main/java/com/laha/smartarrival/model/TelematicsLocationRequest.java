package com.laha.smartarrival.model;

public class TelematicsLocationRequest {
	private String accountId;
	private Location location;
	private String messageType;

	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TelematicsLocationRequest [accountId=");
		builder.append(accountId);
		builder.append(", location=");
		builder.append(location);
		builder.append(", messageType=");
		builder.append(messageType);
		builder.append("]");
		return builder.toString();
	}

}
