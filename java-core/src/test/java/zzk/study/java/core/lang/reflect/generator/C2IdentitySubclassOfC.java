package zzk.study.java.core.lang.reflect.generator;

import java.lang.reflect.Constructor;

/**
 * 生成子类
 * */
public class C2IdentitySubclassOfC extends C2CTransformation {

    static public void main( String[] args ) {
        new C2IdentitySubclassOfC().createClass( args );
    }

    protected String generateClassNamePrefix() {
        return "SubclassOf" + super.generateClassNamePrefix();
    }

    protected String getSuperclass() {return inputClassName;}

    protected void checkAndProcessArgs( Args args ){
        super.checkAndProcessArgs( args );
        if ( inputClassObject.isInterface() )
            throw new C2CException("input class is an interface");
    }
    protected final String generateConstructors() {
        String result = "";
        Constructor[] cArray = inputClassObject.getDeclaredConstructors();
        for (int i = 0; i < cArray.length; i++ )
            result += "public "
                    + Utils.createRenamedConstructor( cArray[i],
                    outputClassName,
                    "" );
        return super.generateConstructors() + result;
    }
}