package math;


public class Demo1 {
    public static void main(String[] args){
        testFloorDiv();
        testFloorMod();
    }

    public static void testFloorDiv(){
        System.out.println("---------------------testFloorDiv---------------------");
        System.out.println(4/3);
        System.out.println( Math.floorDiv(4,3) );
        System.out.println(-4/-3);
        System.out.println( Math.floorDiv(-4,-3) );
        System.out.println(-4/3);
        System.out.println( Math.floorDiv(-4,3) );
    }

    public static void testFloorMod(){
        System.out.println("---------------------testFloorDiv---------------------");
        System.out.println(4/3);
        System.out.println( Math.floorMod(4,3) );
        System.out.println(-4/-3);
        System.out.println( Math.floorMod(-4,-3) );
        System.out.println(-4/3);
        System.out.println( Math.floorMod(-4,3) );
    }


}
