package reflect;

public class A {
	public void f1() {
		System.out.println("A的f1方法");
	}
	
	public String f2() {
		System.out.println("A的f2方法");
		return "f2";
	}
	
	public String f3(String name,int age) {
		System.out.println("A的f3方法");
		return name + age;
	}
}





