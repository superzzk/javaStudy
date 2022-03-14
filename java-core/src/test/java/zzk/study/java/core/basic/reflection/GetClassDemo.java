package zzk.study.java.core.basic.reflection;

/**
 * @author zhangzhongkun
 * @since 2019-08-07 10:12
 **/
public class GetClassDemo {
    public static void main(String[] args) {
        //方式一
        Person person = new Person();
        Class<? extends Person> personClazz01 = person.getClass();

        //方式二
        try {
            Class<?> personClazz02 = Class.forName("Person");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //方式三
        Class<? extends Person> personClazz03 = Person.class;
    }

}
class Person{

}