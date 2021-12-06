package zzk.study.java.core.designpattern.strategy;

/**
 * @program: designpattern
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-04-01 10:46
 **/
public class OperationAdd implements Strategy {

    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}
