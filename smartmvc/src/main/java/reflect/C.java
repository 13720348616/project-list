package reflect;

public class C {
	public void f1() {
		System.out.println("C的f1方法");
	}
	
	@Test("Giving")
	public void f2() {
		System.out.println("C的f2方法");
	}
	
	@Test("Sally")
	public void hello() {
		System.out.println("C的hello方法");
	}
}




