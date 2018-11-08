package cn.likai.util.http.callback;

import cn.likai.entity.TPerson;
import cn.likai.util.http.ParseResultException;
import cn.likai.util.http.XMLCallback;
import cn.likai.entity.ServerMessage;

public abstract class TPersonCallback extends XMLCallback<ServerMessage, TPerson> {

	@Override
	protected TPerson toFinalResult(final ServerMessage result) throws ParseResultException {
		final String retData = processRetData(result);
		
		final TPerson r;
		try {
			r = (TPerson) super.mXs.fromXML(retData);
		} catch (Exception e) {
			throw new ParseResultException(e);
		}
		return r;

	}
}
