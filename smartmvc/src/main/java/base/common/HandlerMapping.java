package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.annotation.RequestMapping;

/**
 * 映射处理器：
 * 	　负责建立请求路径与处理器的对应关系。
 *
 */
public class HandlerMapping {
	
	/*
	 * handlerMap用于存放请求路径与处理器的
	 * 对应关系。
	 * 注：
	 *   key就是请求路径。
	 *   value就是处理器与方法对象的封装,即
	 *   Handler对象。
	 *   
	 */
	private Map<String,Handler> handlerMap = 
			new HashMap<String,Handler>();
	
	/**
	 * 依据请求路径，返回Handler对象(
	 * 该对象封装了处理器及方法对象)。
	 */
	public Handler getHandler(String path) {
		return handlerMap.get(path);
	}

	/**
	 * 遍历list集合(里面存放有所有处理器实例),
	 * 利用java反射，读取@RequestMapping注解
	 * 中的请求路径，然后以请求路径作为key,
	 * 以处理器实例及方法对象的封装(Handler对象)
	 * 作为value,将这个对应关系存放到handlerMap
	 * 里面。
	 */
	public void process(List beans) {
		for(Object bean :beans) {
			//获得class对象
			Class clazz = bean.getClass();
			//获得所有方法
			Method[] methods = 
					clazz.getDeclaredMethods();
			//对所有方法进行遍历
			for(Method mh : methods) {
				//获得加在方法前的@RequestMapping
				RequestMapping rm = 
					mh.getDeclaredAnnotation(
						RequestMapping.class);
				//获得请求路径
				String path = rm.value();
				//将对应关系存放到handlerMap
				handlerMap.put(path, 
						new Handler(mh,bean));
			}
		}
		System.out.println("handlerMap:" 
		+ handlerMap);
		
	}

}
