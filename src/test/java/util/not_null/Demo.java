package util.not_null;



import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

public class Demo {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        QueryUserRequest req = new QueryUserRequest();
        //req.nullFieldValidate();
        String aaa = null;
        useRequest(aaa);
    }
    static void useRequest(@NotNull String name){
        System.out.println("ffffff");
    }

}
