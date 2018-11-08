package cn.likai.testnet;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import cn.likai.util.http.AppException;
import cn.likai.util.http.AuthFailureError;
import cn.likai.util.http.RequestManager;

public class BaseActivity extends Activity implements GlobalCallback, GlobalExceptionListener {

	@Override
	public NetworkInfo getActiveNetworkInfo() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo;
	}

	@Override
	public boolean handleException(AppException e) {
		if (e instanceof AuthFailureError) {
			Toast.makeText(getApplicationContext(), "AuthFailureError", Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}

	@Override
	protected void onStop() {
		super.onStop();
		RequestManager.getInstance().cancel(this.toString());
	}

}
