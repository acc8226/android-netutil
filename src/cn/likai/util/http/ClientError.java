package cn.likai.util.http;

/**
 * �ͻ���4xx���ʹ���
 */
@SuppressWarnings("serial")
public class ClientError extends AppException {

	public ClientError(String message, Exception reason) {
        super(message, reason);
	}

}
