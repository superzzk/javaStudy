package zzk.study.java.core.designpattern.singleton.destroy;

/**
 *
 * 在被序列化的类中添加readResolve方法
 * Deserializing an object via readUnshared invalidates the stream handle associated with the returned object.
 * Note that this in itself does not always guarantee that the reference returned by readUnshared is unique;
 * the deserialized object may define a readResolve method which returns an object visible to other parties,
 * or readUnshared may return a Class object or enum constant obtainable elsewhere in the stream or through external means.
 * If the deserialized object defines a readResolve method and the invocation of that method returns an array,
 * then readUnshared returns a shallow clone of that array; this guarantees that the returned
 * array object is unique and cannot be obtained a second time from an invocation of readObject
 * or readUnshared on the ObjectInputStream, even if the underlying data stream has been manipulated.
 **/

import org.jetbrains.annotations.Contract;

import java.io.*;

public class PreventSerialize implements Serializable {

    private static final long serialVersionUID = 1L;
    String name;

    private PreventSerialize() {
        System.out.println("Singleton is create");
        name = "PreventSerialize";
    }

    private static PreventSerialize instance = new PreventSerialize();

    @Contract(pure = true)
    public static PreventSerialize getInstance() {
        return instance;
    }

    public static void createString() {
        System.out.println("createString in Singleton");
    }
    private Object readResolve(){
        return instance;
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        PreventSerialize s1 = null;
        PreventSerialize s = PreventSerialize.getInstance();

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fos = new FileOutputStream("PreventSerialize.obj");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(s);
        } finally {
            oos.flush();
            oos.close();
            fos.close();
        }

        try{
            fis = new FileInputStream("PreventSerialize.obj");
            ois = new ObjectInputStream(fis);
            s1 = (PreventSerialize) ois.readObject();
        }finally{
            ois.close();
            fis.close();
        }
        System.out.println(s == s1);
    }

}