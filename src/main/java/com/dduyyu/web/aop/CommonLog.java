package com.dduyyu.web.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.dduyyu.web.util.JsonUtil;

@Aspect
@Component
public class CommonLog {
	protected Logger log = Logger.getLogger(CommonLog.class);

	@Before(value = "execution(* com.dduyyu.web.action..*.*(..))")
	public void doBefore(JoinPoint jPoint) {
		log.info(assemble(jPoint, "doBefore"));
	}

	@After(value = "execution(* com.dduyyu.web.action..*.*(..))")
	public void doAfter(JoinPoint jPoint) {
		log.info(assemble(jPoint, "doAfter"));
	}

	@Around(value = "execution(* com.dduyyu.web.service..*.*(..))")
	public Object doAround(ProceedingJoinPoint p) throws Throwable {
		StringBuffer sb = new StringBuffer("类名:");
		log.info(assemble(p,"doBefore"));
		
		String targetName = p.getTarget().getClass().getName();
		String methodName = p.getSignature().getName();
		sb.append(targetName);
		sb.append(".");
		sb.append(methodName);
		sb.append("()");
		sb.append("--参数:");
		Object result = null;
		result = p.proceed();
		try {
			sb.append("serviceImpl_Result---"+JSON.toJSONString(result));
			sb.append("---serviceImpl_Result---END");
			log.info(sb.toString());
		} catch (Throwable e) {
			log.error("CommonLog.doAround()" + "-参数:" + sb + e.getMessage(),e);
		}
		
		log.info(assemble(p,"doAfter"));
		return result;
	}


	private String assemble(JoinPoint joinPoint, String flag) {
		StringBuffer sb = new StringBuffer("类名:");
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		sb.append(targetName);
		sb.append(".");
		sb.append(methodName);
		sb.append("--参数:");
		sb.append("()");
		Map<?, ?> inputParamMap = null;
		try {
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			ServletRequestAttributes sra = (ServletRequestAttributes) ra;
			HttpServletRequest request = sra.getRequest();
			// 获取输入参数
			inputParamMap = request.getParameterMap();
		} catch (Exception e) {
			log.error(
					"CommonLog.assemble(JoinPoint joinPoint, String flag)" + "-参数:"
							+ JsonUtil.getJsonStringFromMap(inputParamMap)
							+ e.getMessage());
		}

		sb.append(JsonUtil.getJsonStringFromMap(inputParamMap));

		if ("doBefore".equals(flag)) {
			sb.append("--START");
		} else if ("doAfter".equals(flag)) {
			sb.append("--END");
		}
		return sb.toString();
	}
	
	private String assemble(ProceedingJoinPoint p, String flag) {
		StringBuffer sb = new StringBuffer();
		sb.append("类名");
		String targetName = p.getTarget().getClass().getName();
		String methodName = p.getSignature().getName();
		sb.append(targetName);
		sb.append(".");
		sb.append(methodName);
		sb.append("()");
		sb.append("--参数:");
		try {
			for (Object obj : p.getArgs()) {
				sb.append(JSON.toJSONString(obj));
			}
		} catch (Throwable e) {
			log.error("CommonLog.assemble(ProceedingJoinPoint p, String flag)" + "-参数:" + sb + e.getMessage());
		}
		
		if ("doBefore".equals(flag)) {
			sb.append("--START");
		} else if ("doAfter".equals(flag)) {
			sb.append("--END");
		}
		
		return sb.toString();
	}
}
