package com.dduyyu.web.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dduyyu.web.domain.SetRealName;
import com.dduyyu.web.domain.SinaBaseParam;


public class RealNameVerify {
	private static final Log log = LogFactory.getLog(RealNameVerify.class);
	
	/*
	 * 构建基本参数（sina）
	 */
	public Object constructSinaBaseParam(String service, String signType,
			String charset, Object obj) throws Exception {
		// 必须参数判断
		if (StringUtils.isBlank(service) || StringUtils.isBlank(signType)
				|| StringUtils.isBlank(charset)) {
			log.error(" 构建sina基本参数发生异常：参数为空");
			return null;
		}
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String timeString = dataFormat.format(date);

		SinaBaseParam baseParam = (SinaBaseParam) obj;

		baseParam.setService(service);// 接口名称
		baseParam.setVersion("1.0");// 接口版本
		baseParam.setRequest_time(timeString);// 请求时间
		baseParam.setPartner_id(SinaHostingEnvUtil.getValue("partner_id"));// 合作者身份ID
		baseParam.set_input_charset(charset);// 参数编码字符集
		baseParam.setSign_type(signType);// 签名方式

		return obj;
	}
	
	//实名认证
	public Map<String, String> setRealName(String userId, String realName,
			String idNo, String needConfirm) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();

		// 必须参数检查
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(realName)
				|| StringUtils.isBlank(idNo)
				|| StringUtils.isBlank(needConfirm)) {
			resultMap.put("code", "sysException");
			resultMap.put("message", "必须参数缺失【设置sina实名信息】");
			log.error("必须参数缺失【设置sina实名信息】");
			return resultMap;
		}

		String charset = SinaHostingEnvUtil.getValue("charset");
		String signType = SinaHostingEnvUtil.getValue("sign_type");
		String url = SinaHostingEnvUtil.getValue("member_url");

		// 构建基本参数
		SetRealName setRealName = (SetRealName) constructSinaBaseParam(
				"set_real_name", signType, charset, new SetRealName());

		if (setRealName == null) {
			resultMap.put("code", "sysException");
			resultMap.put("message", "构建基本参数异常【设置sina实名信息】");
			log.error("构建基本参数异常【设置sina实名信息】");
			return resultMap;
		}

		// 获取RSA加密key
		String encrypt = SinaHostingEnvUtil.getValue("rsa_publickey");
		if (StringUtils.isBlank(encrypt)) {
			resultMap.put("code", "sysException");
			resultMap.put("message", "获取RSAkey异常【设置sina实名信息】");
			log.error("获取RSAkey异常【设置sina实名信息】");
			return resultMap;
		}

		// 认证内容加密
		byte[] real_name_byte = RSA.encryptByPublicKey(
				realName.getBytes("utf-8"), encrypt);
		byte[] cert_no_byte = RSA.encryptByPublicKey(idNo.getBytes(), encrypt);

		// 设置业务参数
		setRealName.setIdentity_id(userId);
		setRealName.setIdentity_type("UID");
		setRealName.setReal_name(Base64.encode(real_name_byte));
		setRealName.setCert_type("IC");
		setRealName.setCert_no(Base64.encode(cert_no_byte));
		setRealName.setNeed_confirm(needConfirm);

		// 构建签名串
		String content = Tools.createLinkString(
				BeanUtil.transBean2Map(setRealName, true), false, true);

		String signKey = "";
		if ("MD5".equalsIgnoreCase(signType)) {
			signKey = SinaHostingEnvUtil.getValue("sign_key_md5");
		} else {
			signKey = SinaHostingEnvUtil.getValue("sign_key_rsa_private");
		}
		
		String check_signKey = "";
		if ("MD5".equalsIgnoreCase(signType)) {
			check_signKey = SinaHostingEnvUtil.getValue("sign_key_md5");
		} else {
			check_signKey = SinaHostingEnvUtil.getValue("sign_key_rsa_public");
		}

		String sign = SignUtil.sign(content, signType, signKey, charset);
		setRealName.setSign(URLEncoder.encode(sign, charset));

		// 构建创建激活会员的请求参数
		String param = Tools.createLinkString(
				BeanUtil.transBean2Map(setRealName, false), true, false);

		log.info("设置sina实名信息时发送的请求参数串：" + param);

		// 发送请求
		String result = URLDecoder.decode(CallServiceUtil.sendPost(url, param),
				charset);
		if (StringUtils.isBlank(result)) {
			resultMap.put("code", "BusinessException");
			resultMap.put("message", "设置实名信息发送 POST请求出现异常");
			log.error("发送 POST请求出现异常【设置sina实名信息】");
			return resultMap;
		}

		// 解析请求结果
		Map<String, String> contentMap = GsonUtil.fronJson2Map(result);

		String sign_result = contentMap.get("sign").toString();// 签名
		String sign_type_result = contentMap.get("sign_type").toString();// 签名方式
		String input_charset_result = contentMap.get("_input_charset")
				.toString();// 参数编码字符集
		String response_code = contentMap.get("response_code").toString();// 响应码
		String response_message = contentMap.get("response_message").toString();// 响应信息

		String like_result = Tools.createLinkString(contentMap, false, true);
		if (SignUtil.Check_sign(like_result.toString(), sign_result,
				sign_type_result, check_signKey, input_charset_result)) {
			log.info("【设置sina实名信息】响应码=" + response_code + "响应信息="
					+ response_message);
			
			if("APPLY_SUCCESS".equals(response_code)){
				resultMap.put("code", "00");
				resultMap.put("message", "");
			}else{
				resultMap.put("code", "BusinessException");
				resultMap.put("message", response_message);
			}
			return resultMap;
		} else {
			resultMap.put("code", "sysException");
			resultMap.put("message", "验签失败【设置sina实名信息】");
			log.error("验签失败【设置sina实名信息】");
			return resultMap;
		}
	}
	
}
