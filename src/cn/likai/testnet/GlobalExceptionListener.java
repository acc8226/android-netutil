package cn.likai.testnet;

import cn.likai.util.http.AppException;

/**
 * 处理全局Exception
 */
public interface GlobalExceptionListener {
	boolean handleException(AppException e);
}
