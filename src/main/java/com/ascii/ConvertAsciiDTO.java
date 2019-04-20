package com.ascii;

public class ConvertAsciiDTO {
	private String logInfo;
	private String logString;
	
	public String getLogInfo() {
		return logInfo;
	}
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	public String getLogString() {
		return logString;
	}
	public void setLogString(String logString) {
		this.logString = logString;
	}
	
	@Override
	public String toString() {
		return "ConvertAsciiDTO [logInfo=" + logInfo + ", logString=" + logString + "]";
	}
}
