package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.annotation.RequestMapping;

/**
 * ӳ�䴦������
 * 	������������·���봦�����Ķ�Ӧ��ϵ��
 *
 */
public class HandlerMapping {
	
	/*
	 * handlerMap���ڴ������·���봦������
	 * ��Ӧ��ϵ��
	 * ע��
	 *   key��������·����
	 *   value���Ǵ������뷽������ķ�װ,��
	 *   Handler����
	 *   
	 */
	private Map<String,Handler> handlerMap = 
			new HashMap<String,Handler>();
	
	/**
	 * ��������·��������Handler����(
	 * �ö����װ�˴���������������)��
	 */
	public Handler getHandler(String path) {
		return handlerMap.get(path);
	}

	/**
	 * ����list����(�����������д�����ʵ��),
	 * ����java���䣬��ȡ@RequestMappingע��
	 * �е�����·����Ȼ��������·����Ϊkey,
	 * �Դ�����ʵ������������ķ�װ(Handler����)
	 * ��Ϊvalue,�������Ӧ��ϵ��ŵ�handlerMap
	 * ���档
	 */
	public void process(List beans) {
		for(Object bean :beans) {
			//���class����
			Class clazz = bean.getClass();
			//������з���
			Method[] methods = 
					clazz.getDeclaredMethods();
			//�����з������б���
			for(Method mh : methods) {
				//��ü��ڷ���ǰ��@RequestMapping
				RequestMapping rm = 
					mh.getDeclaredAnnotation(
						RequestMapping.class);
				//�������·��
				String path = rm.value();
				//����Ӧ��ϵ��ŵ�handlerMap
				handlerMap.put(path, 
						new Handler(mh,bean));
			}
		}
		System.out.println("handlerMap:" 
		+ handlerMap);
		
	}

}
