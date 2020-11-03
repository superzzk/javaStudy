package lang.reflect;

import org.junit.Test;

import java.lang.reflect.Method;

public class ReflectDemo {
	/**
	 * 通过反射调用private方法
	 */
	@Test
	public void demo1() throws Exception {
		Check c = new Check();
		Method m;

		// Using getDeclareMethod() method
		m = Check.class.getDeclaredMethod("Demo_private_Method");

		// Using setAccessible() method
		m.setAccessible(true);

		// Using invoke() method
		m.invoke(c);
	}
	// The class contains a private method
	class Check {

		// Private method
		private void Demo_private_Method() {
			System.out.println("Private Method " + "called from outside");
		}

		// Public method
		public void printData() {
			System.out.println("Public Method");
		}
	}

}

