package cn.likai.util.http.callback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.text.TextUtils;
import cn.likai.entity.OldServerMessage;
import cn.likai.util.http.ParseResultException;
import cn.likai.util.http.XMLCallback;

public abstract class PermissionCallback extends XMLCallback<OldServerMessage, List<String>> {

	@SuppressWarnings("unchecked")
	@Override
	protected List<String> toFinalResult(OldServerMessage result) throws ParseResultException {
		final Map<String, Object> extra = result.getExtra();
		if (extra == null) {
			final String detail = result.getDetail();
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
		if (!(retData instanceof ArrayList<?>)) {
			throw new ParseResultException("返回数据类型不是list");
		}

		List<String> list = null;
		try {
			// 去重
			final Set<String> repe = new HashSet<String>(32);
			for (Map<String, String> mapItem : (ArrayList<Map<String, String>>) retData) {
				String insName = mapItem.get("INS_NAME");
				if (insName != null) {
					repe.add(insName);
				}
			}
			list = new ArrayList<String>(repe);
		} catch (Exception e) {
			throw new ParseResultException(e);
		}
		return list;

	}

}
