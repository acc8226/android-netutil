package cn.likai.util.http;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import cn.likai.testnet.GlobalExceptionListener;

public final class Request {
	public String url;
	/**
	 * 要写入的内容
	 */
	public String content;
	public Map<String, String> headers;
	public RequestMethod method;

	@SuppressWarnings("rawtypes")
	public ICallback callback;

	/**
	 * 是否需要支持加载进度更新的回调
	 */
	public boolean enable = false;

	/**
	 * 直接重试感觉意义不大, 间隔时间段的重试才靠谱
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

	// 枚举类
	public enum RequestMethod {
		GET, POST, PUT, DELETE
	}

	public void setCallback(ICallback<?, ?> iCallback) {
		this.callback = iCallback;
	}

	/**
	 * 是否需要支持加载进度更新的回调
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
	 * 取消请求的方法, 此处实现没有使用Asynctask.cancel()实现, 因为Asynctask在不同版本有差异
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
