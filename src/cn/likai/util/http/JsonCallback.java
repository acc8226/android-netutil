package cn.likai.util.http;

public abstract class JsonCallback<RESULT> extends AbstractCallback<RESULT, RESULT>{

	// Ŀǰ��gsonʵ��, ������Ҫ
	@Override
	protected final RESULT bindData(String result) {
		/*final ParameterizedType parameterizedType = (ParameterizedType)getClass().getGenericSuperclass();
		final Type type = parameterizedType.getActualTypeArguments()[0];
		return new Gson().fromJson(result, type);*/
		return null;
	}
}
