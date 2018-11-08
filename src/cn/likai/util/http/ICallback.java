package cn.likai.util.http;

import java.net.HttpURLConnection;

public interface ICallback<PRE_RESULT, RESULT> {
	void onSuccess(RESULT result);

	void onFailure(AppException e);

	/**
	 * ֮ǰ�Ĳ��� (�����߳���ִ��), ������ز�Ϊnull��ֱ�ӵ��� onSuccess ����
	 * @return
	 */
	PRE_RESULT preRequest();
	
	/**
	 *  (�����߳���ִ��)
	 * @param conn
	 * @param updatedListener
	 *            ����Ϊnull
	 * @return
	 * @throws Exception
	 */
	RESULT parse(HttpURLConnection conn, OnProgressUpdatedListener updatedListener) throws AppException;
	
	/**
	 * ���������ݵĲ��� (�����߳���ִ��), �õ����ݺ�����ս���
	 * 
	 * @param result
	 * @return
	 */
	RESULT postRequest(RESULT result);
	
	/**
	 * 
	 * @param currentLen ����ɳ���
	 * @param totalLen �ܳ���
	 */
	void onProgressUpdated(int currentLen, int totalLen);

	void cancel();

	public interface OnProgressUpdatedListener {
		void onProgressUpdated(int currentLen, int totalLen);
	}

}
