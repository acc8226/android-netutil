package cn.likai.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("RiskInfo")
public final class RiskInfo {
	public String MainRiskContNo;
	public String RiskContNo;
	public String RiskCode;
	public String RiskName;
	public String ContState;
	public String RiskValiDate;
	public String InsuPeriod;

	@Override
	public String toString() {
		return "RiskInfo [MainRiskContNo=" + MainRiskContNo + ", RiskContNo=" + RiskContNo + ", RiskCode=" + RiskCode
				+ ", RiskName=" + RiskName + ", ContState=" + ContState + ", RiskValiDate=" + RiskValiDate
				+ ", InsuPeriod=" + InsuPeriod + "]";
	}

}
