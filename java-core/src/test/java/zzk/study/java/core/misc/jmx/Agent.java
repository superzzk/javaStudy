package zzk.study.java.core.misc.jmx;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/*
* 启动后通过jconsole 连接远程：service:zzk.study.java.core.misc.jmx:rmi:///jndi/rmi://localhost:1099/MyMBean
* */
public class Agent {
    public static void main(String[] args)
            throws MalformedObjectNameException, NotCompliantMBeanException,
            InstanceAlreadyExistsException, MBeanRegistrationException, IOException {
        // 下面这种方式不能再JConsole中使用
        // MBeanServer server = MBeanServerFactory.createMBeanServer();
        // 首先建立一个MBeanServer,MBeanServer用来管理我们的MBean,通常是通过MBeanServer来获取我们MBean的信息，间接
        // 调用MBean的方法，然后生产我们的资源的一个对象。
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        String domainName = "MyMBean";
        //为MBean（下面的new Hello()）创建ObjectName实例
        ObjectName helloName = new ObjectName(domainName + ":name=HelloWorld");
        // 将new Hello()这个对象注册到MBeanServer上去
        mbs.registerMBean(new Hello(), helloName);

        int rmiPort = 1099;
        Registry registry = LocateRegistry.createRegistry(rmiPort);
        JMXServiceURL url = new JMXServiceURL("service:zzk.study.java.core.misc.jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + domainName);
        JMXConnectorServer jmxConnector = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
        jmxConnector.start();
    }

    public static interface HelloMBean {
        public String getName();

        public void setName(String name);

        public void printHello();

        public void printHello(String whoName);
    }


    public static class Hello implements HelloMBean {
        private String name;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void printHello() {
            System.out.println("Hello world, " + name);
        }

        @Override
        public void printHello(String whoName) {
            System.out.println("Hello, " + whoName);
        }
    }

}
