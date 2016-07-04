package com.dduyyu.web.domain;

import java.io.Serializable;
/**
 * sina基本参数
 *
 */
public class SinaBaseParam implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String url;//请求路径
	private String service;//接口名称
	private String version;//接口版本
	private String request_time;//请求时间
	private String partner_id;//合作者身份ID
	private String _input_charset;//参数编码字符集
	private String sign;//签名
	private String sign_type;//签名方式
	private String sign_version;//签名版本号
	private String encrypt_version;//加密版本号
	private String notify_url;//系统异步回调通知地址
	private String return_url;//页面跳转同步返回页面路径
	private String memo;//备注
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getRequest_time() {
		return request_time;
	}
	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}
	public String getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public String get_input_charset() {
		return _input_charset;
	}
	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign_version() {
		return sign_version;
	}
	public void setSign_version(String sign_version) {
		this.sign_version = sign_version;
	}
	public String getEncrypt_version() {
		return encrypt_version;
	}
	public void setEncrypt_version(String encrypt_version) {
		this.encrypt_version = encrypt_version;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
