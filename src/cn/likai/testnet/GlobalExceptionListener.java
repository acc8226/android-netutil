package cn.likai.testnet;

import cn.likai.util.http.AppException;

/**
 * ����ȫ��Exception
 */
public interface GlobalExceptionListener {
	boolean handleException(AppException e);
}
