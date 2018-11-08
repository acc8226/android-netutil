package cn.likai.util.http;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

import android.os.AsyncTask;

public class RequestAsyncTask extends AsyncTask<Void, Integer, Object> {

	private final Request mRequest;
	
	public RequestAsyncTask(Request request){
		this.mRequest = request;
	}
	
	@Override
	protected Object doInBackground(Void... params) {
		Object ret;
		if (mRequest.callback != null) {
			ret = mRequest.callback.preRequest();
			if (ret != null) {
				return ret;
			}
		}
		ret = request(0);
		return ret;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		mRequest.callback.onProgressUpdated(values[0], values[1]);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		if (result instanceof AppException) {
			if (mRequest.mGlobalExceptionListener == null
					|| !mRequest.mGlobalExceptionListener.handleException((AppException) result)) {
				mRequest.callback.onFailure((AppException) result);
			}
		} else {
			mRequest.callback.onSuccess(result);
		}
	}

	private Object request(int retryTime) {
		Object retObj;
		try {
			HttpURLConnection conn = HttpUrlConnectionUtil.connect(mRequest);

			ICallback.OnProgressUpdatedListener listener;
			if (mRequest.enable) {
				listener = new ICallback.OnProgressUpdatedListener() {
					@Override
					public void onProgressUpdated(int currentLen, int totalLen) {
						publishProgress(currentLen, totalLen);
					}
				};
			} else {
				listener = null;
			}
			retObj = mRequest.callback.parse(conn, listener);
		} catch (AppException e) {
			// 判断e是否是超时的e
			if (e.getCause() instanceof SocketTimeoutException || e.getCause() instanceof ConnectException) {
				if (retryTime < mRequest.maxRetryCount) {
					retObj = request(++retryTime);
				} else {
					retObj = e;
				}
			} else {
				retObj = e;
			}
		}
		return retObj;
	}



}
