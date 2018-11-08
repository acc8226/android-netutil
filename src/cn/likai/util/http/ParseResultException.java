package cn.likai.util.http;

/**
 * 解析返回数据的错误
 */
@SuppressWarnings("serial")
public class ParseResultException extends AppException {

	public ParseResultException(String string) {
		super(string);
	}

	public ParseResultException(Exception e) {
		super(e);
	}

}
