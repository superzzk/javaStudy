package zzk.study.java.core.basic.object;

public class CloneableClass {
    public static void main(String[] args) {
        testClone();
        System.out.println("-----------------------");
        testShallowClone();
        System.out.println("-----------------------");
        testDeepClone();
    }
    public static void testClone() {
        MyClass dh = new MyClass(100.00);
        MyClass dhClone = (MyClass) dh.clone();

        System.out.println("Original:" + dh.getValue());
        System.out.println("Clone :" + dhClone.getValue());

        dh.setValue(200.00);
        dhClone.setValue(400.00);

        System.out.println("Original:" + dh.getValue());
        System.out.println("Clone :" + dhClone.getValue());
    }
    public static void testShallowClone() {
        ShallowClone sc = new ShallowClone(100.00);
        ShallowClone scClone = (ShallowClone) sc.clone();

        System.out.println("Original:" + sc.getValue());
        System.out.println("Clone :" + scClone.getValue());

        sc.setValue(200.00);

        System.out.println("Original:" + sc.getValue());
        System.out.println("Clone :" + scClone.getValue());
    }
    public static void testDeepClone() {
        DeepClone sc = new DeepClone(100.00);
        DeepClone scClone = (DeepClone) sc.clone();

        System.out.println("Original:" + sc.getValue());
        System.out.println("Clone :" + scClone.getValue());

        sc.setValue(200.00);

        System.out.println("Original:" + sc.getValue());
        System.out.println("Clone :" + scClone.getValue());
    }
}
class MyClass implements Cloneable {
    private double value;

    public MyClass(double value) {
        this.value = value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    public Object clone() {
        MyClass copy = null;
        try {
            copy = (MyClass) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }
}

class ShallowClone implements Cloneable {
    private MyClass holder = new MyClass(0.0);

    public ShallowClone(double value) {
        this.holder.setValue(value);
    }

    public void setValue(double value) {
        this.holder.setValue(value);
    }

    public double getValue() {
        return this.holder.getValue();
    }

    public Object clone() {
        ShallowClone copy = null;
        try {
            copy = (ShallowClone) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }
}

class DeepClone implements Cloneable {
    private MyClass holder = new MyClass(0.0);

    public DeepClone(double value) {
        this.holder.setValue(value);
    }

    public void setValue(double value) {
        this.holder.setValue(value);
    }

    public double getValue() {
        return this.holder.getValue();
    }
    public Object clone() {
        DeepClone copy = null;
        try {
            copy = (DeepClone) super.clone();
            copy.holder = (MyClass) this.holder.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }
}