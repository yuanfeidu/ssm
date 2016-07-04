package com.dduyyu.web.domain;

import java.io.Serializable;
/**
 * 设置实名信息(sina)
 *
 */
public class SetRealName extends SinaBaseParam implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String identity_id;//用户标识信息
	private String identity_type;//用户标识类型
	private String real_name;//真实姓名
	private String cert_type;//证件类型
	private String cert_no;//证件号码
	private String need_confirm;//是否认证
	private String extend_param;//扩展信息
	
	public String getIdentity_id() {
		return identity_id;
	}
	public void setIdentity_id(String identity_id) {
		this.identity_id = identity_id;
	}
	public String getIdentity_type() {
		return identity_type;
	}
	public void setIdentity_type(String identity_type) {
		this.identity_type = identity_type;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getCert_type() {
		return cert_type;
	}
	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public String getNeed_confirm() {
		return need_confirm;
	}
	public void setNeed_confirm(String need_confirm) {
		this.need_confirm = need_confirm;
	}
	public String getExtend_param() {
		return extend_param;
	}
	public void setExtend_param(String extend_param) {
		this.extend_param = extend_param;
	}
}
