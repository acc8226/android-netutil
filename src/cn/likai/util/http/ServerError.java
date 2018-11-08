package cn.likai.util.http;

/**
 * ·şÎñÆ÷´íÎó
 */
@SuppressWarnings("serial")
public class ServerError extends AppException {

	public ServerError(String message, Exception reason) {
		super(message, reason);
	}

}
