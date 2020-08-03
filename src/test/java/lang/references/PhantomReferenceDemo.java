package lang.references;

import org.junit.Assert;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class PhantomReferenceDemo {

	@Test
	public void test1()
	{
		//Strong Reference
		Gfg g = new Gfg();
		g.x();

		//Creating reference queue
		ReferenceQueue<Gfg> refQueue = new ReferenceQueue<Gfg>();

		//Creating Phantom Reference to Gfg-type object to which 'g' is also pointing.
		PhantomReference<Gfg> phantomRef = new PhantomReference<Gfg>(g,refQueue);

		g = phantomRef.get();

		//The class PhantomReference overrides get() method which always returns null,
		// instead of super's class 'referent' instance.
		// That is done to ensure that the referent of a phantom reference may not be accessible.
		// (To avoid the same disadvantage of using finalize() which can resurrect the target object).
		Assert.assertNull(g);

		//Now, Gfg-type object to which 'g' was pointing
		//earlier is available for garbage collection.
		//But, this object is kept in 'refQueue' before
		//removing it from the memory.
		g = null;

		g = phantomRef.get();
		Assert.assertNull(g);
	}

	static class Gfg
	{
		//code
		public void x()
		{
			System.out.println("GeeksforGeeks");
		}
	}
}
