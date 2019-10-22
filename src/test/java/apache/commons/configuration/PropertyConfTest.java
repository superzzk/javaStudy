package apache.commons.configuration;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Test;

import java.io.File;

/**
 * @program: javaStudy
 * @author: zhangzhongkun
 * @create: 2019-04-26 11:05
 **/
public class PropertyConfTest {
    @Test
    public void test1() throws Exception {
        Configurations configs = new Configurations();
        try
        {
            Configuration config = configs.properties(new File("config.properties"));
            // access configuration properties
        }
        catch (ConfigurationException cex)
        {
            // Something went wrong
        }
    }
}
