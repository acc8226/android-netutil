package cn.likai.testnet;

import android.net.NetworkInfo;

public interface GlobalCallback {

	/**
	 * Get the device's active network status.
	 */
	NetworkInfo getActiveNetworkInfo();

}