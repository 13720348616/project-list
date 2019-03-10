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
	 * 1.读取配置文件的内容。
	 * 利用java反射，创建处理器实例。
	 * 2.创建HandlerMapping实例。
	 * 3.调用HandlerMapping的方法，该方法
	 * 会负责建立请求路径与处理器的对应关系。
	 */
	public void init() throws ServletException {
		//读取配置文件名
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
			//读取配置文件的内容
			Document doc = reader.read(in);
			//找到根节点
			Element root = doc.getRootElement();
			//找到根节点下面的所有子节点
			List<Element> elements = 
					root.elements();
			//遍历所有子节点
			List beans = new ArrayList();
			for(Element ele : elements) {
				//读取class属性值(即处理器类名)
				String className = 
					ele.attributeValue("class");
				System.out.println("className:" 
					+ className);
				//利用java反射，将处理器实例化
				Object bean = 
						Class.forName(className)
						.newInstance();
				//将处理器实例放到一个集合当中
				//，方便管理。
				beans.add(bean);
			}
			System.out.println("beans:" + beans);
			
			//创建HandlerMapping实例
			handlerMapping = new HandlerMapping();
			
			//调用HandlerMapping的方法，该方法负责
			//建立请求路径与处理器的对应关系
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
		//获得请求资源路径
		String uri = 
				request.getRequestURI();
		System.out.println("uri:" + uri);
		//获得应用名
		String contextPath = 
				request.getContextPath();
		System.out.println("contextPath:" + 
				contextPath);
		//截取请求资源路径的一部分(除掉应用名)
		String path = 
			uri.substring(contextPath.length());
		System.out.println("path:" + path);
		
		/*
		 * 调用HandlerMapping的getHandler方法
		 * 获得Handler对象(封装了处理器及方法
		 * 对象)。
		 */
		Handler handler =
				handlerMapping.getHandler(
						path);
		System.out.println("handler:" 
						+ handler);
		
		//利用Handler对象来获得处理器及方法对象
		Method mh = handler.getMh();
		Object obj = handler.getObj();
		Object returnVal = null;
		try {
			//利用java反射来调用处理器的方法
			returnVal = mh.invoke(obj);
			System.out.println("returnVal:" 
					+ returnVal);
			//获得视图名
			String viewName = returnVal.toString();
			/*
			 * 处理视图名:
			 * 默认转发到 
			 * "/WEB-INF/" + 视图名 + ".jsp"。
			 * 如果视图名是以"redirect:"开头，
			 * 则重定向。
			 */
			if(viewName.startsWith("redirect:")) {
				//重定向
				
				String redirectPath = 
						contextPath + "/" 
				+ viewName.substring(
						"redirect:".length());
				
				response.sendRedirect(redirectPath);
				
			}else {
				//转发
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
