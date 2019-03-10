package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class TestCase {

	public static void main(String[] args)
			throws ClassNotFoundException,
			InstantiationException, 
			IllegalAccessException,
			IllegalArgumentException, 
			InvocationTargetException {
		Scanner scanner  = 
				new Scanner(System.in);
		String className = 
				scanner.nextLine();
		System.out.println("className:" + 
				className);
		//������
		Class clazz =
				Class.forName(className);
		//ʵ����
		Object obj = clazz.newInstance();
		System.out.println("obj:" + obj);
		
		//�ҳ����з���
		Method[] methods = 
				clazz.getDeclaredMethods();
		//��������
		for(Method mh : methods) {
			//��÷�����
			String mName = 	mh.getName();
			System.out.println("mName:" 
			+ mName);
			/*
			 * ���÷���:
			 * Ҫ����Ŀ�귽���Ƿ����,
			 * ���Ե���getParameterTypes����
			 * �����Ŀ�귽���Ĳ���������Ϣ��
			 * ���types����Ϊ0����ʾĿ�귽��
			 * �����Ρ�
			 */
			Class[] types = 
					mh.getParameterTypes();
			Object returnVal = null;
			if(types.length > 0) {
				//Ŀ�귽������
				Object[] params = 
						new Object[types.length];
				for(int i = 0; i < types.length; i++) {
					if(types[i] == String.class) {
						params[i] = "Giving";
					}
					if(types[i] == int.class) {
						params[i] = 100;
					}
				}
				
				returnVal = mh.invoke(obj, params);
				
			}else {
				//Ŀ�귽��������
				returnVal = 
						mh.invoke(obj);
			}
			System.out.println("returnVal:" 
					+ returnVal);
		}
		
		
		
		
		
	}

}
