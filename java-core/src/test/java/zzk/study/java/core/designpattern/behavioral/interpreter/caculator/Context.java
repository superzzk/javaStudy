package zzk.study.java.core.designpattern.behavioral.interpreter.caculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Context {
    private Map<Expression, Integer> map = new HashMap<>();

    //定义变量
    public void add(Expression s, Integer value) {
        map.put(s, value);
    }

    //将变量转换成数字
    public Integer lookup(Expression s) {
        return map.get(s);
    }

    //构建语法树的主要方法
    public static Expression build(String str) {
        //主要利用栈来实现
        Stack<Expression> objects = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            //遇到运算符号+号时候
            if (c == '+') {
                //先出栈
                Expression pop = objects.pop();
                //将运算结果入栈
                objects.push(new PlusOperation(pop, new TerminalExpression2(String.valueOf(str.charAt(++i)))));
            } else if (c == '-') {
                //遇到减号类似加号
                Expression pop = objects.pop();
                objects.push(new MinusOperation(pop, new TerminalExpression2(String.valueOf(str.charAt(++i)))));
            } else {
                //遇到非终结符直接入栈（基本就是第一个数字的情况）
                objects.push(new TerminalExpression2(String.valueOf(str.charAt(i))));
            }
        }
        //把最后的栈顶元素返回
        return objects.pop();
    }
}


