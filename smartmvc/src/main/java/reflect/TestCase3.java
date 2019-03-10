package reflect;

import java.lang.reflect.Method;
import java.util.Scanner;

public class TestCase3 {

	public static void main(String[] args)
			throws Exception {
		Scanner scanner = 
				new Scanner(System.in);
		String className = 
				scanner.nextLine();
		Class clazz = 
				Class.forName(className);
		Object obj = 
				clazz.newInstance();
		Method[] methods = 
				clazz.getDeclaredMethods();
		for(Method mh : methods) {
			/*
			 * 如果方法前有@Test注解，则执行。
			 */
			Test test = mh.getDeclaredAnnotation(
					Test.class);
			System.out.println("@Test:" + test);
			if(test != null) {
				mh.invoke(obj);
				//读取注解的属性值
				String value = test.value();
				System.out.println("value:" + value);
			}
		}
		
	}

}



