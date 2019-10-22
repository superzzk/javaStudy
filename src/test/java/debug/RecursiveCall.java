package debug;

import org.junit.Test;

/**
 * @author zhangzhongkun
 * @since  2019-07-05 10:09
 **/
public class RecursiveCall {

    @Test(expected = StackOverflowError.class)
    public void testRecursiveCall(){
        testRecursiveCall();
    }
}
