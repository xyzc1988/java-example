package com.etoak.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;


public class McpChangeRequest {
	@XStreamAlias("Prefix")
	private String prefix;//前缀，在CMU处配置
	@XStreamAlias("ObjectType")
	private String objectType;//同步信息类型，content：内容
	@XStreamAlias("OperType")
	private String operType;//操作类型，1：新增 2：变更 3：删除
	@XStreamAlias("CooperateCode ")
	private String cooperateCode ;//合作伙伴码, CPID
	@XStreamAlias("ContentCode")
	private String contentCode;//内容代码 内容ID
	@XStreamAlias("ParentContentCode")
	private String parentContentCode;//父内容代码
	@XStreamAlias("ContentName")
	private String contentName;//内容名称
	@XStreamAlias("ContentControlType")
	private String contentControlType;//内容管控类型, 传3000
	@XStreamAlias("Status")
	private String status;//内容状态1:商用2:暂停3:下线
	@XStreamAlias("SaleType")
	private String saleType;//销售类型100：本101：章102：分册
	@XStreamAlias("Fee")
	private String fee;//价格，单位：单位：厘
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getCooperateCode() {
		return cooperateCode;
	}
	public void setCooperateCode(String cooperateCode) {
		this.cooperateCode = cooperateCode;
	}
	public String getContentCode() {
		return contentCode;
	}
	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}
	public String getParentContentCode() {
		return parentContentCode;
	}
	public void setParentContentCode(String parentContentCode) {
		this.parentContentCode = parentContentCode;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public String getContentControlType() {
		return contentControlType;
	}
	public void setContentControlType(String contentControlType) {
		this.contentControlType = contentControlType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	
}
