package cn.likai.util.http;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import cn.likai.testnet.GlobalExceptionListener;

public final class Request {
	public String url;
	/**
	 * Ҫд�������
	 */
	public String content;
	public Map<String, String> headers;
	public RequestMethod method;

	@SuppressWarnings("rawtypes")
	public ICallback callback;

	/**
	 * �Ƿ���Ҫ֧�ּ��ؽ��ȸ��µĻص�
	 */
	public boolean enable = false;

	/**
	 * ֱ�����Ըо����岻��, ���ʱ��ε����Բſ���
	 */
	public int maxRetryCount;

	public GlobalExceptionListener mGlobalExceptionListener;

	public volatile boolean mIsCancelled;

	private String mTag;

	private RequestAsyncTask mRequestTask;

	public Request() {
		this(null, RequestMethod.POST);
	}
	
	public Request(String url) {
		this(url, RequestMethod.POST);
	}

	public Request(String url, RequestMethod method) {
		this.url = url;
		this.method = method;
	}

	// ö����
	public enum RequestMethod {
		GET, POST, PUT, DELETE
	}

	public void setCallback(ICallback<?, ?> iCallback) {
		this.callback = iCallback;
	}

	/**
	 * �Ƿ���Ҫ֧�ּ��ؽ��ȸ��µĻص�
	 * 
	 * @param enable
	 */
	public void enableProgressUpdated(boolean enable) {
		this.enable = enable;
	}

	public void setGlobalExceptionListener(GlobalExceptionListener globalException) {
		mGlobalExceptionListener = globalException;
	}

	/**
	 * ȡ������ķ���, �˴�ʵ��û��ʹ��Asynctask.cancel()ʵ��, ��ΪAsynctask�ڲ�ͬ�汾�в���
	 */
	public void cancel(boolean mayInterruptIfRunning) {
		this.mIsCancelled = true;
		this.callback.cancel();
		if (mayInterruptIfRunning && mRequestTask != null) {
			this.mRequestTask.cancel(mayInterruptIfRunning);
		}
	}

	public void setTag(String tag) {
		mTag = tag;
	}

	public String getTag() {
		return mTag;
	}

	public void executeOnExecutor(ExecutorService mExecutors) {
		mRequestTask = new RequestAsyncTask(this);
		mRequestTask.executeOnExecutor(mExecutors);
	}

}
