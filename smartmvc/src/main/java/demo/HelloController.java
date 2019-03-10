package demo;

import base.annotation.RequestMapping;

/**
 * 处理器：
 * 　　负责业务逻辑的处理。
 */
public class HelloController {
	
	@RequestMapping("/hello.do")
	public String hello() {
		System.out.println(
				"HelloController的hello方法");
		/*
		 * 返回视图名。
		 * DispatcherServlet会按照
		 * WEB-INF + 视图名 + .jsp生成
		 * jsp地址。
		 */
		return "hello";
	}
}





