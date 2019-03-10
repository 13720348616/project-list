package base.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import base.common.Handler;
import base.common.HandlerMapping;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HandlerMapping handlerMapping;
	
	@Override
	/**
	 * 1.��ȡ�����ļ������ݡ�
	 * ����java���䣬����������ʵ����
	 * 2.����HandlerMappingʵ����
	 * 3.����HandlerMapping�ķ������÷���
	 * �Ḻ��������·���봦�����Ķ�Ӧ��ϵ��
	 */
	public void init() throws ServletException {
		//��ȡ�����ļ���
		String fileName = 
				getServletConfig()
				.getInitParameter(
						"configLocation");
		SAXReader reader = 
				new SAXReader();
		InputStream in = 
				getClass().getClassLoader()
				.getResourceAsStream(fileName);
		try {
			//��ȡ�����ļ�������
			Document doc = reader.read(in);
			//�ҵ����ڵ�
			Element root = doc.getRootElement();
			//�ҵ����ڵ�����������ӽڵ�
			List<Element> elements = 
					root.elements();
			//���������ӽڵ�
			List beans = new ArrayList();
			for(Element ele : elements) {
				//��ȡclass����ֵ(������������)
				String className = 
					ele.attributeValue("class");
				System.out.println("className:" 
					+ className);
				//����java���䣬��������ʵ����
				Object bean = 
						Class.forName(className)
						.newInstance();
				//��������ʵ���ŵ�һ�����ϵ���
				//���������
				beans.add(bean);
			}
			System.out.println("beans:" + beans);
			
			//����HandlerMappingʵ��
			handlerMapping = new HandlerMapping();
			
			//����HandlerMapping�ķ������÷�������
			//��������·���봦�����Ķ�Ӧ��ϵ
			handlerMapping.process(beans);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}



	protected void service(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException,
					IOException {
		//���������Դ·��
		String uri = 
				request.getRequestURI();
		System.out.println("uri:" + uri);
		//���Ӧ����
		String contextPath = 
				request.getContextPath();
		System.out.println("contextPath:" + 
				contextPath);
		//��ȡ������Դ·����һ����(����Ӧ����)
		String path = 
			uri.substring(contextPath.length());
		System.out.println("path:" + path);
		
		/*
		 * ����HandlerMapping��getHandler����
		 * ���Handler����(��װ�˴�����������
		 * ����)��
		 */
		Handler handler =
				handlerMapping.getHandler(
						path);
		System.out.println("handler:" 
						+ handler);
		
		//����Handler��������ô���������������
		Method mh = handler.getMh();
		Object obj = handler.getObj();
		Object returnVal = null;
		try {
			//����java���������ô������ķ���
			returnVal = mh.invoke(obj);
			System.out.println("returnVal:" 
					+ returnVal);
			//�����ͼ��
			String viewName = returnVal.toString();
			/*
			 * ������ͼ��:
			 * Ĭ��ת���� 
			 * "/WEB-INF/" + ��ͼ�� + ".jsp"��
			 * �����ͼ������"redirect:"��ͷ��
			 * ���ض���
			 */
			if(viewName.startsWith("redirect:")) {
				//�ض���
				
				String redirectPath = 
						contextPath + "/" 
				+ viewName.substring(
						"redirect:".length());
				
				response.sendRedirect(redirectPath);
				
			}else {
				//ת��
				String jspPath = 
						"/WEB-INF/" + viewName + ".jsp";
				request.getRequestDispatcher(jspPath)
				.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		
	}

}
