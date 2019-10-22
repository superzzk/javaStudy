package designpattern.builder;

/**
 * @program: designpattern
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-03-29 09:51
 **/
public class ChickenBurger extends Burger {
    @Override
    public float price() {
        return 50.5f;
    }

    public String name() {
        return "Chicken Burger";
    }
}
