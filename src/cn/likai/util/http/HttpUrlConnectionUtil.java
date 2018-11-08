package cn.likai.util.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUrlConnectionUtil {

	/**
	 * 建立Http连接
	 * 
	 * @param request
	 * @return
	 * @throws AppException
	 */
	public static HttpURLConnection connect(final Request request) throws AppException {
		final HttpURLConnection conn;
		switch (request.method) {
		case POST:
		case PUT:
			conn = post(request);
			break;
		case GET:
		case DELETE:
		default:
			conn = get(request);
			break;
		}
		return conn;
	}

	private static HttpURLConnection get(final Request request) throws AppException {
		checkRequestIsCancelled(request);

		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(request.url).openConnection();
			conn.setRequestMethod(request.method.name());
		} catch (IOException e) {
			if (conn != null) {
				conn.disconnect();
			}
			throw new AppException(e);
		}

		conn.setConnectTimeout(10 * 1000);
		conn.setReadTimeout(10 * 1000);

		addHeader(conn, request.headers);

		checkRequestIsCancelled(request);
		return conn;
	}

	private static HttpURLConnection post(final Request request) throws AppException {
		if (request.content == null) {
			throw new AppException(request.method.name() + "方式携带的content为空");
		}
		checkRequestIsCancelled(request);

		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(request.url).openConnection();
			conn.setRequestMethod(request.method.name());
			conn.setConnectTimeout(10 * 1000);
			conn.setReadTimeout(10 * 1000);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "utf-8");

			addHeader(conn, request.headers);

			checkRequestIsCancelled(request);
			OutputStream os = conn.getOutputStream();
			os.write(request.content.getBytes("UTF-8"));
		} catch (IOException e) {
			if (conn != null) {
				conn.disconnect();
			}
			throw new AppException(e);
		}
		
		checkRequestIsCancelled(request);
		return conn;
	}

	private static void checkRequestIsCancelled(final Request request) throws PauseException {
		if (request.mIsCancelled) {
			throw new PauseException();
		}
	}

	private static void addHeader(final HttpURLConnection conn, final Map<String, String> headers) {
		if (headers != null) {
			for (Map.Entry<String, String> item : headers.entrySet()) {
				conn.addRequestProperty(item.getKey(), item.getValue());
			}
		}
	}

	private HttpUrlConnectionUtil() {
	}

}
