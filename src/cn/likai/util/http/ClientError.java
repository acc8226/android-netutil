package cn.likai.util.http;

/**
 * 客户端4xx类型错误
 */
@SuppressWarnings("serial")
public class ClientError extends AppException {

	public ClientError(String message, Exception reason) {
        super(message, reason);
	}

}
