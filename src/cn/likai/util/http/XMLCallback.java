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
		// 需要处理的根节点, 拿到类型PRE_RESULT给processAnnotations()方法处理
		final ParameterizedType parameterizedType = (ParameterizedType) getClass().getSuperclass()
				.getGenericSuperclass();
		// 在安卓解析报文内容的时候, autodetectAnnotations处理输出竟然无效
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
	 * 子线程中执行: 从IReturnInfo中取出所需数据
	 * 
	 * @param result
	 * @return
	 * @throws ParseResultException
	 */
	protected abstract RESULT toFinalResult(PRE_RESULT result) throws ParseResultException;
	
	/**
	 * 预处理数据
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
				throw new ParseResultException("请求数据异常");
			}
		}

		final Map<String, Object> extra = result.getExtra();
		if (extra == null) {
			if (!TextUtils.isEmpty(detail)) {
				throw new ParseResultException(detail);
			} else {
				throw new ParseResultException("请求数据异常");
			}
		}

		final Object retData = extra.get("retData");
		if (retData == null) {
			throw new ParseResultException("retData字段不存在");
		}
		if (!(retData instanceof String)) {
			throw new ParseResultException("retData返回类型不是String");
		}
		return (String) retData;
	}
}
