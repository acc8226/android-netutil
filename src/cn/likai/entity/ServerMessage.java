package cn.likai.entity;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("com.insure.taxdeferredserver.util.Message")
public class ServerMessage implements IReturnInfo {

	/**
	 * ��˶���Ĵ���
	 */
	protected int command;
	/**
	 * ���: ����ԭ��
	 */
	protected String detail;
	/**
	 * ���: ǰ���ֻ᷵�ع���, ���ݴ�ֵȡ��map
	 */
	protected Object value;
	/**
	 * ���: ��˴�����������
	 */
	protected Map<String, Object> extra;

	public ServerMessage() {
	}

	protected ServerMessage(int command, String detail, Object value) {
		this.command = command;
		this.detail = detail;
		this.value = value;
	}

	public Map<String, Object> getExtra() {
		return this.extra;
	}

	public String getDetail() {
		return this.detail;
	}

	public Object getValue() {
		return this.value;
	}

	/**
	 * 
	 * @param name
	 * @param v
	 * @return
	 * @throws NullPointerException
	 *             ���extraΪ��, ��ʱ�ڹ���ʱҪ���Ϸ���hasExtra()
	 */
	public ServerMessage addExtra(String name, Object v) throws NullPointerException {
		this.extra.put(name, v);
		return this;
	}

	/**
	 * ���xml, ʹ����@XStreamAliasע��, �����ڲ����Զ�����
	 * @return
	 */
	public final String toXML() {
		return ServerMessage.toXML(this);
	}

	@Override
	public String toString() {
		return "ServerMessage [command=" + command + ", detail=" + detail + ", value=" + value + ", extra=" + extra
				+ "]";
	}

	public static final class Builder {
		private int command;
		private String detail;
		private Object value;
		private Map<String, Object> extra;

		public Builder command(int command) {
			this.command = command;
			return this;
		}

		public Builder detail(String detail) {
			this.detail = detail;
			return this;
		}

		public Builder value(Object value) {
			this.value = value;
			return this;
		}

		public Builder hasExtra() {
			if (this.extra == null) {
				this.extra = new HashMap<String, Object>();
			}
			return this;
		}

		public Builder noExtra() {
			this.extra = null;
			return this;
		}

		public ServerMessage create() {
			final ServerMessage serverMessage = new ServerMessage(command, detail, value);
			if (extra != null) {
				serverMessage.extra = extra;
			}
			return serverMessage;
		}
	}

	/**
	 * ���xml, ʹ����@XStreamAliasע��, �����ڲ����Զ�����
	 * @return
	 */
	public static String toXML(IReturnInfo returnInfo) {
		final XStream xs = new XStream();
		xs.autodetectAnnotations(true);
		return xs.toXML(returnInfo);
	}

}
