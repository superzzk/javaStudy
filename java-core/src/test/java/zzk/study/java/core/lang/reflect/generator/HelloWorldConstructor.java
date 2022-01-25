package zzk.study.java.core.lang.reflect.generator;

public class HelloWorldConstructor extends C2CConstructor {
    static public void main( String[] args ) {
        new HelloWorldConstructor().createClass( args );
    }
    protected String generateMethods() {
        return super.generateMethods()
                + " public static void main( String[] args ) { \n"
                + " System.out.println( \"Hello world!\" );\n"
                + " } \n";
    }
}