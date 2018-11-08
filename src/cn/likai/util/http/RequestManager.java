package cn.likai.util.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestManager {
	private final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	private final int CORE_POOL_SIZE = CPU_COUNT + 1;

	// FIXME list会不停增长, 所以暂不加入该数据结构
	private final Map<String, List<Request>> mCacheRequest = new HashMap<String, List<Request>>();
	private final ExecutorService mExecutors = Executors.newFixedThreadPool(CORE_POOL_SIZE);

	private RequestManager() {
	}

	private static final RequestManager sInstance = new RequestManager();

	public static RequestManager getInstance() {
		return sInstance;
	}

	public void performRequest(Request request) {
		request.executeOnExecutor(mExecutors);

		// if (!mCacheRequest.containsKey(request.getTag())) {
		// List<Request> list = new ArrayList<Request>();
		// mCacheRequest.put(request.getTag(), list);
		// }
		// mCacheRequest.get(request.getTag()).add(request);
	}

	public void cancel(String tag) {
		this.cancel(tag, false);
	}

	/**
	 * 
	 * @param tag
	 * @param mayInterruptIfRunning
	 *            false有onError回调, true如果request在排队就直接销毁
	 */
	public void cancel(String tag, boolean mayInterruptIfRunning) {
		if (mCacheRequest.containsKey(tag)) {
			List<Request> requests = mCacheRequest.get(tag);
			for (Request request : requests) {
				if (request.getTag().equals(tag)) {
					request.cancel(mayInterruptIfRunning);
				}
			}
		}
	}

	public void cancelAll() {
		for (List<Request> requests : mCacheRequest.values()) {
			for (Request request : requests) {
				request.cancel(true);
			}
		}
	}

}
