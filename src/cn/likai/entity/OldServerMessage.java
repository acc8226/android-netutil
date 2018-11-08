package cn.likai.entity;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("com.insure.client.util.Message")
public class OldServerMessage extends ServerMessage {

	public OldServerMessage() {
		super();
	}

	protected OldServerMessage(int command, String detail, Object value) {
		this.command = command;
		this.detail = detail;
		this.value = value;
	}
	
	/**
	 * 
	 * @param name
	 * @param v
	 * @return
	 * @throws NullPointerException 如果extra为空, 此时在构造时要加上方法hasExtra()
	 */
	public OldServerMessage addExtra(String name, Object v) throws NullPointerException{
		return (OldServerMessage) super.addExtra(name, v);
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

		public OldServerMessage create() {
			final OldServerMessage message = new OldServerMessage(command, detail, value);
			if (extra != null) {
				message.extra = extra;
			}
			return message;
		}
	}

	@Override
	public String toString() {
		return "IMessage [command=" + command + ", detail=" + detail + ", value=" + value + ", extra=" + extra + "]";
	}

}
