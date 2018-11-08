package cn.likai.util.http;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

import android.text.TextUtils;
import cn.likai.entity.IReturnInfo;

public abstract class XMLCallback<PRE_RESULT extends IReturnInfo, RESULT> extends AbstractCallback<PRE_RESULT, RESULT> {

	protected final XStream mXs = new XStream();

	@SuppressWarnings("unchecked")
	@Override
	protected final RESULT bindData(final String result) throws ParseResultException {
		// ��Ҫ����ĸ��ڵ�, �õ�����PRE_RESULT��processAnnotations()��������
		final ParameterizedType parameterizedType = (ParameterizedType) getClass().getSuperclass()
				.getGenericSuperclass();
		// �ڰ�׿�����������ݵ�ʱ��, autodetectAnnotations���������Ȼ��Ч
		final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		final Class<?> clz0 = (Class<?>) actualTypeArguments[0];
		this.mXs.processAnnotations(clz0);
		final Type type1 = actualTypeArguments[1];
		if (type1 instanceof Class) {
			this.mXs.processAnnotations((Class<?>)type1);
		}
		
		final PRE_RESULT ret;
		try {
			ret = (PRE_RESULT) this.mXs.fromXML(result);
		} catch (Exception e) {
			throw new ParseResultException(e);
		}
		return toFinalResult(ret);
	}

	/**
	 * ���߳���ִ��: ��IReturnInfo��ȡ����������
	 * 
	 * @param result
	 * @return
	 * @throws ParseResultException
	 */
	protected abstract RESULT toFinalResult(PRE_RESULT result) throws ParseResultException;
	
	/**
	 * Ԥ��������
	 * @param result
	 * @return
	 * @throws ParseResultException
	 */
	protected static String processRetData(final IReturnInfo result) throws ParseResultException {
		final String detail = result.getDetail();
		final Object value = result.getValue();

		if (value instanceof String && "N".equals((String) value)) {
			if (!TextUtils.isEmpty(detail)) {
				throw new ParseResultException(detail);
			} else {
				throw new ParseResultException("���������쳣");
			}
		}

		final Map<String, Object> extra = result.getExtra();
		if (extra == null) {
			if (!TextUtils.isEmpty(detail)) {
				throw new ParseResultException(detail);
			} else {
				throw new ParseResultException("���������쳣");
			}
		}

		final Object retData = extra.get("retData");
		if (retData == null) {
			throw new ParseResultException("retData�ֶβ�����");
		}
		if (!(retData instanceof String)) {
			throw new ParseResultException("retData�������Ͳ���String");
		}
		return (String) retData;
	}
}
