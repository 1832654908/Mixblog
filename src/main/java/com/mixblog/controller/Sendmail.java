package com.mixblog.controller;

import com.mixblog.pojo.MailUtil;

public class Sendmail {
	private static final long serialVersionUID = 1L;
	 
	public static void main(String[] args) {
		String text="11111";
		MailUtil.sendMail("592583156@qq.com", "����ldtutu.com",text);
		/*
		 * System.out.println("���ͳɹ�1"); MailUtil.sendMail("1364411370@qq.com",
		 * "����ldtutu.com",text); System.out.println("���ͳɹ�2");
		 * MailUtil.sendMail("604219226@qq.com", "����ldtutu.com",text);
		 * System.out.println("���ͳɹ�3"); MailUtil.sendMail("704926823@qq.com",
		 * "����ldtutu.com",text); System.out.println("���ͳɹ�4");
		 * MailUtil.sendMail("1285190509@qq.com", "����ldtutu.com",text);
		 * System.out.println("���ͳɹ�5"); MailUtil.sendMail("2129239066@qq.com",
		 * "����ldtutu.com",text); System.out.println("���ͳɹ�6");
		 * MailUtil.sendMail("974515741@qq.com", "����ldtutu.com",text);
		 * System.out.println("���ͳɹ�7"); MailUtil.sendMail("639788020@qq.com",
		 * "����ldtutu.com",text); System.out.println("���ͳɹ�8");
		 * MailUtil.sendMail("1554581497@qq.com", "����ldtutu.com",text);
		 * System.out.println("���ͳɹ�9"); MailUtil.sendMail("1203571831@qq.com",
		 * "����ldtutu.com",text); System.out.println("���ͳɹ�10");
		 */
		MailUtil.sendMail("1832654908@qq.com", "����ldtutu.com",text);
		System.out.println("���ͳɹ�11");
		
	}
}
