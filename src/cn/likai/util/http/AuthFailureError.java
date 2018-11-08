package cn.likai.util.http;

@SuppressWarnings("serial")
public class AuthFailureError extends AppException {

    public AuthFailureError(String message, Exception reason) {
        super(message, reason);
    }

	public AuthFailureError(Exception e) {
		super(e);
	}

}
