package cn.likai.util.http;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import android.util.Log;

public abstract class AbstractCallback<PRE_RESULT, RESULT> implements ICallback<PRE_RESULT, RESULT> {

	protected String mPath;

	public volatile boolean mIsCancelled;

	@Override
	public final RESULT parse(final HttpURLConnection conn, final OnProgressUpdatedListener updatedListener)
			throws AppException {
		this.checkRequestIsCancelled();
		
		int statusCode = -1;
		InputStream is = null;
		OutputStream out = null;
		try {
			statusCode = conn.getResponseCode();
			if (statusCode < 200 || statusCode > 299) {
				throw new IOException();
			} else {
				is = conn.getInputStream();
				final byte[] buffer = new byte[4096];
				int len;
				final int totalLen = conn.getContentLength();
				int currentLen = 0;
				if (mPath == null) {
					out = new ByteArrayOutputStream();
				} else {
					out = new FileOutputStream(this.mPath);
				}
				while ((len = is.read(buffer)) != -1) {
					this.checkRequestIsCancelled();
					out.write(buffer, 0, len);
					currentLen += len;
					if (updatedListener != null) {
						updatedListener.onProgressUpdated(currentLen, totalLen);
					}
				}
				out.flush();
			}
		} catch (FileNotFoundException e) {
			throw new AppException(e);
		} catch (IOException e) {
			if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED || statusCode == HttpURLConnection.HTTP_FORBIDDEN) {
				throw new AuthFailureError(e);
			} else if (statusCode >= 400 && statusCode <= 499) {
				throw new ClientError("ClientError: responseCode = " + String.valueOf(statusCode), e);
			} else if (statusCode != -1) {
				throw new ServerError("ServerError: responseCode = " + String.valueOf(statusCode), e);
			} else {
				throw new AppException(e);
			}
		} finally {
			// 不可能为空
			conn.disconnect();
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		RESULT result = null;
		if (mPath == null) {
			final String charsetName = "UTF-8";
			final byte[] byteArray = ((ByteArrayOutputStream) out).toByteArray();
			try {
				String resultStr = new String(byteArray, charsetName);
				if (true) {
					Log.i("TAG", resultStr);
				}
				result = bindData(resultStr);
			} catch (UnsupportedEncodingException e) {
				throw new AppException(e);
			}
		} else {
			result = bindData(this.mPath);
		}
		result = postRequest(result);
		return result;
	}

	// 空实现
	@Override
	public PRE_RESULT preRequest() {
		return null;
	}

	// 默认实现
	@Override
	public RESULT postRequest(RESULT result) {
		return result;
	}

	// 空实现
	@Override
	public void onProgressUpdated(int currentLen, int totalLen) {
	}

	@Override
	public final void cancel() {
		this.mIsCancelled = true;
	}

	/**
	 * 子线程中执行: 处理原始返回的数据
	 * 
	 * @param result
	 * @return
	 * @throws ParseResultException
	 */
	protected abstract RESULT bindData(String result) throws ParseResultException;

	private void checkRequestIsCancelled() throws PauseException {
		if (mIsCancelled) {
			throw new PauseException();
		}
	}

}
