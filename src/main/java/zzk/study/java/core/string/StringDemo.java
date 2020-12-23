package zzk.study.java.core.string;


public class StringDemo {
    public static void main(String[] args) {
        String temp = "  50     M    ";

        System.out.println( removeWhitespace(temp) );
        System.out.println( removeNumber(temp) );
        System.out.println( removeAlphabet(temp) );

        System.out.println( removeWhitespace(temp).endsWith("M") );

        System.out.println(getBandwidth(temp));
    }

    //删除空格
    static String removeWhitespace(String s){
        return s.replaceAll(" ","");
    }

    //删除数字&空格
    static String removeNumber(String s){
        return s.replaceAll("[\\d ]","");
    }
    //删除字母&空格
    static String removeAlphabet(String s){
        return s.replaceAll("[a-zA-Z ]","");
    }

    static String getBandwidth(String bandwidth){
        if(null == bandwidth || bandwidth.isEmpty()) {
            return  null;
        }
        //删除空格
        bandwidth = bandwidth.replaceAll(" ","");
        if(!bandwidth.endsWith("M") && !bandwidth.endsWith("Mbps")){
            return  null;
        }
        //删除非数字字符
        bandwidth = bandwidth.replaceAll("[^0-9]","");

        return bandwidth;
    }

}
