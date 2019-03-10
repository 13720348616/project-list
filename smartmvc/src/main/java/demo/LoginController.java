package demo;

import base.annotation.RequestMapping;

public class LoginController {
	@RequestMapping("/toLogin.do")
	public String toLogin() {
		System.out.println(
			"LoginController��toLogin����");
		return "login";
	}
	
	@RequestMapping("/login.do")
	public String checkLogin() {
		System.out.println("LoginController"
				+ "��checkLogin����");
		/*
		 * �����ͼ��ǰ�������"redirect:",
		 * DispatcherServlet���ض���
		 */
		return "redirect:toWelcome.do";
	}
	
	@RequestMapping("/toWelcome.do")
	public String toWelcome(){
		return "welcome";
	}
}





