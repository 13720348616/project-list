package demo;

import base.annotation.RequestMapping;

/**
 * ��������
 * ��������ҵ���߼��Ĵ���
 */
public class HelloController {
	
	@RequestMapping("/hello.do")
	public String hello() {
		System.out.println(
				"HelloController��hello����");
		/*
		 * ������ͼ����
		 * DispatcherServlet�ᰴ��
		 * WEB-INF + ��ͼ�� + .jsp����
		 * jsp��ַ��
		 */
		return "hello";
	}
}





