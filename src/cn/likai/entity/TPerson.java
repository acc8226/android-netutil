package cn.likai.entity;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Response")
public final class TPerson {
	public String ContNo;
	public String InsNo;
	public String IsAccepted;
	public String ErrorInfo;
	public String AppntName;
	public String AppntSex;
	public String AppntIDTypeName;
	public String AppntIDType;
	public String AppntIDNo;
	public String IDEffStartDate;
	public String IDEffEndDate;
	public String CurrDate;
	public String ManageCom;
	public String AgentName;
	public String AgentCode;
	public String SaleComs;
	public String ManageComName;
	public String SaleOfAgent;
	public String SaleOfAgent2;
	public String SaleOfAgent3;

	public final List<RiskInfo> RiskInfoList = new ArrayList<RiskInfo>();

	@Override
	public String toString() {
		return "ResponseTPerson [ContNo=" + ContNo + ", InsNo=" + InsNo + ", IsAccepted=" + IsAccepted + ", ErrorInfo="
				+ ErrorInfo + ", AppntName=" + AppntName + ", AppntSex=" + AppntSex + ", AppntIDTypeName="
				+ AppntIDTypeName + ", AppntIDType=" + AppntIDType + ", AppntIDNo=" + AppntIDNo + ", IDEffStartDate="
				+ IDEffStartDate + ", IDEffEndDate=" + IDEffEndDate + ", CurrDate=" + CurrDate + ", ManageCom="
				+ ManageCom + ", AgentName=" + AgentName + ", AgentCode=" + AgentCode + ", SaleComs=" + SaleComs
				+ ", ManageComName=" + ManageComName + ", SaleOfAgent=" + SaleOfAgent + ", SaleOfAgent2=" + SaleOfAgent2
				+ ", SaleOfAgent3=" + SaleOfAgent3 + ", RiskInfoList=" + RiskInfoList + "]";
	}

}
