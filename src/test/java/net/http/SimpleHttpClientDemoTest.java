package net.http; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import java.util.HashMap;
import java.util.Map;

import static net.http.SimpleHttpClientDemo.send;

/** 
* SimpleHttpClientDemo Tester. 
* 
* @author <Authors name> 
* @since <pre>$todate</pre> 
* @version 1.0 
*/ 
public class SimpleHttpClientDemoTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: send(String url, Map<String, String> map, String encoding) 
* 
*/ 
@Test
public void testSend() throws Exception {
    String url="http://php.weather.sina.com.cn/iframe/index/w_cl.php";
    Map<String, String> map = new HashMap<String, String>();
    map.put("code", "js");
    map.put("day", "0");
    map.put("city", "上海");
    map.put("dfc", "1");
    map.put("charset", "utf-8");
    String body = send(url, map,"utf-8");
    System.out.println("交易响应结果：");
    System.out.println(body);

    System.out.println("-----------------------------------");

    map.put("city", "北京");
    body = send(url, map, "utf-8");
    System.out.println("交易响应结果：");
    System.out.println(body);


}

    @Test
    public void testSendHttps() throws Exception {

        String url = "https://www.qingyidai.com/investmanagement/invest.shtml";
        String body = send(url, null, "utf-8");
        System.out.println("交易响应结果：");
        System.out.println(body);
    }

} 
