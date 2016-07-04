package com.ddu.test;

import org.junit.Test;

import com.dduyyu.web.util.RealNameVerify;


public class TestRealNameVaild {
	//411123198906307513
	@Test
	public void test(){
		String userId = "1575764";
		String realName = "杜元飞";
		String idNo = "360726198906300035";
		String needConfirm = "Y";
		RealNameVerify s = new RealNameVerify();
		try {
			System.out.println(s.setRealName(userId, realName, idNo, needConfirm));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
