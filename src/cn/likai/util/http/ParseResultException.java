package cn.likai.util.http;

/**
 * �����������ݵĴ���
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
