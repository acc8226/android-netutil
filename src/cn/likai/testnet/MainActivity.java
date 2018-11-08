package cn.likai.testnet;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import cn.likai.entity.ServerMessage;
import cn.likai.entity.TPerson;
import cn.likai.util.http.AppException;
import cn.likai.util.http.ParseResultException;
import cn.likai.util.http.PauseException;
import cn.likai.util.http.Request;
import cn.likai.util.http.RequestManager;
import cn.likai.util.http.callback.TPersonCallback;

public class MainActivity extends BaseActivity {

	private TextView tv;

	private Request mRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);
	}

	public void clickBtn(View view) {
		tv.setText(null);
		testHttpOnSubThread();
	}

	public void clickEmptyBtn(View view) {
		tv.setText(null);
	}

	public void pauseBtn(View view) {
		RequestManager.getInstance().cancel(this.toString());
	}

	private void testHttpOnSubThread() {
		mRequest = new Request("http://10.5.92.70:8081/nci-insure-pad/syPadServer.do");
		mRequest.setTag(toString());
		mRequest.setGlobalExceptionListener(this);
		mRequest.content = new ServerMessage.Builder().command(1)
				.detail("VersionName_3.7.1&amp;6067c1d5-798a-4f04-a804-8debd0748e70").value("getBankList").hasExtra()
				.create().addExtra("contNo", "886822315827").addExtra("CustomerNo", "").toXML();
		mRequest.setCallback(new TPersonCallback() {

			@Override
			public void onSuccess(TPerson result) {
				tv.setText(result.toString());
			}

			@Override
			public void onFailure(AppException e) {
				// 断网 和 请求不同的情况
				final Throwable cause = e.getCause();
				if (cause instanceof ConnectException || cause instanceof SocketTimeoutException) {
					tv.setText("超时了: " + e.getMessage());
				} else if (e instanceof PauseException) {
					tv.setText("手动暂停: " + e.getMessage());
				} else if (e instanceof ParseResultException) {
					tv.setText("解析报文失败: " + e.getMessage());
				} else {
					// 做其他的事儿
					tv.setText("其他: " + e.getMessage());
				}

			}
		});
		RequestManager.getInstance().performRequest(mRequest);
	}
}
