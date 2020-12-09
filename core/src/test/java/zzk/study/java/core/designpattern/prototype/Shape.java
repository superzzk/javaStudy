package zzk.study.java.core.designpattern.prototype;

/**
 * @program: designpattern
 * @description:
 * @author: zhangzhongkun
 * @create: 2019-04-01 09:19
 **/
public abstract class Shape implements Cloneable {
    private String id;
    protected String type;

    abstract void draw();

    public String getType(){
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
