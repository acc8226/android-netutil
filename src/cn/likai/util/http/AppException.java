package cn.likai.util.http;

@SuppressWarnings("serial")
public class AppException extends Exception{
	
	public AppException(){}

	public AppException(String detailMessage) {
		super(detailMessage);
	}

	public AppException(Throwable throwable) {
		super(throwable);
	}

	public AppException(String message, Exception reason) {
		super(message, reason);
	}
	
}
