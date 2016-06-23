package com.dduyyu.web.util;

import java.util.Random;

public class ValidCode {
	private static final String SOURCE_CODE = "23456789abcdefghjklmnopqrstuvwxyz";
	
	public static String generateValidCode(){
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			sb.append(SOURCE_CODE.charAt(random.nextInt(SOURCE_CODE.length())));
		}
		return sb.toString().toLowerCase();
	}
	
	public static void main(String[] args) {
		System.out.println(generateValidCode());
	}
}
