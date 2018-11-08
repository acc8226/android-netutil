package cn.likai.util.http;

import java.net.HttpURLConnection;

public interface ICallback<PRE_RESULT, RESULT> {
	void onSuccess(RESULT result);

	void onFailure(AppException e);

	/**
	 * 之前的操作 (在子线程中执行), 如果返回不为null则直接调用 onSuccess 方法
	 * @return
	 */
	PRE_RESULT preRequest();
	
	/**
	 *  (在子线程中执行)
	 * @param conn
	 * @param updatedListener
	 *            可以为null
	 * @return
	 * @throws Exception
	 */
	RESULT parse(HttpURLConnection conn, OnProgressUpdatedListener updatedListener) throws AppException;
	
	/**
	 * 解析完数据的操作 (在子线程中执行), 拿到数据后的最终解析
	 * 
	 * @param result
	 * @return
	 */
	RESULT postRequest(RESULT result);
	
	/**
	 * 
	 * @param currentLen 已完成长度
	 * @param totalLen 总长度
	 */
	void onProgressUpdated(int currentLen, int totalLen);

	void cancel();

	public interface OnProgressUpdatedListener {
		void onProgressUpdated(int currentLen, int totalLen);
	}

}
