package designpattern.prototype;

/**
 * @program: designpattern
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-04-01 09:20
 **/
public class Square extends Shape{
    public Square(){
        type = "Square";
    }

    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
