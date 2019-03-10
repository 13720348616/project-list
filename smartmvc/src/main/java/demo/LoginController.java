package demo;

import base.annotation.RequestMapping;

public class LoginController {
	@RequestMapping("/toLogin.do")
	public String toLogin() {
		System.out.println(
			"LoginController的toLogin方法");
		return "login";
	}
	
	@RequestMapping("/login.do")
	public String checkLogin() {
		System.out.println("LoginController"
				+ "的checkLogin方法");
		/*
		 * 如果视图名前面添加有"redirect:",
		 * DispatcherServlet会重定向。
		 */
		return "redirect:toWelcome.do";
	}
	
	@RequestMapping("/toWelcome.do")
	public String toWelcome(){
		return "welcome";
	}
}





