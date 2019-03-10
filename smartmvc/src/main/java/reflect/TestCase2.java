package reflect;

import java.lang.reflect.Method;

public class TestCase2 {

	public static void main(String[] args)
			throws Exception {
		Class clazz = 
				Class.forName("reflect.B");
		Object obj = 
				clazz.newInstance();
		Method[] methods = 
				clazz.getDeclaredMethods();
		for(Method mh : methods) {
			String mName = mh.getName();
			if(mName.startsWith("test")) {
				mh.invoke(obj);
			}
		}
	}

}
