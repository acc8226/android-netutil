package cn.likai.util.http;

public abstract class StringCallback extends AbstractCallback<String, String>{

	@Override
	protected final String bindData(String result) {
		return result;
	}
}
