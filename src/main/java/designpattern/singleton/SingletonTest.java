package designpattern.singleton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class SingletonTest {
    private SingletonTest() {}

    public static class Holder {
        // 这里的私有没有什么意义
        /* private */static SingletonTest instance = new SingletonTest();
    }

    public static SingletonTest getInstance() {
        // 外围类能直接访问内部类（不管是否是静态的）的私有变量
        return Holder.instance;
    }
}

class CreateThread extends Thread {
    Object singleton;
    ClassLoader cl;

    public CreateThread(ClassLoader cl) {
        this.cl = cl;
    }

    public void run() {
        Class c;
        try {
            c = cl.loadClass("SingletonTest");
            // 当两个不同命名空间内的类相互不可见时，可采用反射机制来访问对方实例的属性和方法
            Method m = c.getMethod("getInstance", new Class[] {});
            // 调用静态方法时，传递的第一个参数为class对象
            singleton = m.invoke(c, new Object[] {});
            c = null;
            cl = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyClassLoader extends ClassLoader {
    private String loadPath;
    MyClassLoader(ClassLoader cl) {
        super(cl);
    }
    public void setPath(String path) {
        this.loadPath = path;
    }
    protected Class findClass(String className) throws ClassNotFoundException {
        FileInputStream fis = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;

        try {
            fis = new FileInputStream(new File(loadPath
                    + className.replaceAll("\\.", "\\\\") + ".class"));
            baos = new ByteArrayOutputStream();
            int tmpByte = 0;
            while ((tmpByte = fis.read()) != -1) {
                baos.write(tmpByte);
            }
            data = baos.toByteArray();
        } catch (IOException e) {
            throw new ClassNotFoundException("class is not found:" + className, e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fis != null) {
                    baos.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defineClass(className, data, 0, data.length);
    }
}

class SingleTest {
    public static void main(String[] args) throws Exception {
        while (true) {
            // 不能让系统加载器直接或间接的成为父加载器
            MyClassLoader loader = new MyClassLoader(null);
            loader.setPath("D:\\HW\\XCALLC16B125SPC003_js\\uniportal\\service\\AAA\\bin\\");
            CreateThread ct1 = new CreateThread(loader);
            CreateThread ct2 = new CreateThread(loader);
            ct1.start();
            ct2.start();
            ct1.join();
            ct2.join();
            if (ct1.singleton != ct2.singleton) {
                System.out.println(ct1.singleton + " " + ct2.singleton);
            }
            // System.out.println(ct1.singleton + " " + ct2.singleton);
            ct1.singleton = null;
            ct2.singleton = null;
            Thread.yield();
        }
    }
}