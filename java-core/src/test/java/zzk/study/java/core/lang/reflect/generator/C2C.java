package zzk.study.java.core.lang.reflect.generator;

import java.io.FileWriter;
import java.io.InputStream;

public abstract class C2C {
    //These are instance variables that store the values of the general command-line arguments defined by the framework.
    protected String classNamePrefix;
    protected Class inputClassObject;
    protected String inputClassName = null;
    protected String outputClassName;
    protected Class outputClassObject;
    protected String packageName;
    protected String qualifiedInputClassName = null;
    protected String qualifiedOutputClassName;

    //There are four boolean instance variables that deal with class modifiers.
    boolean isAbstract;
    boolean isFinal;
    boolean isInterface;
    boolean isNotPublic;

    public final Class createClass( String[] args ) {
        //compute the default name of the output class, which is the prefix followed by the name of the input class.
        classNamePrefix = generateClassNamePrefix();
        Args myArgs = new Args(args);
        checkAndProcessArgs( myArgs );
        if ( !myArgs.complete() )
            throw new C2CException( "Usage: unprocessed flags: " + myArgs.toString( ) );

        UQueue itQ = generateInterfaces();
        UQueue importQ = generateImports();

        String aClassString =
                (packageName==null ? "" : "package " + packageName + ";\n")
                        // import
                        + (importQ.isEmpty() ? "" : "import "
                        + importQ.toString(";\nimport ")
                        + ";\n")
                        // javadoc
                        + getClassLevelJavadoc()
                        // class definition
                        + (isNotPublic?"":"public ")
                        + (isFinal?"final ":"")
                        + (isAbstract?"abstract ":"")
                        + (isInterface?" interface ":" class ") + outputClassName + "\n"
                        + (getSuperclass().equals("") ? "" : " extends "
                        + getSuperclass()
                        + "\n")
                        + (itQ.isEmpty() ? "" : " implements " + itQ.toString(", ") )
                        + "{\n//============= F I E L D S ======================\n"
                        + generateFields()
                        + "\n//============= C O N S T R U C T O R S ==========\n"
                        + generateConstructors()
                        + "\n//============= M E T H O D S ====================\n"
                        + generateMethods()
                        + "\n//============= N E S T E D C L A S S E S ======\n"
                        + generateNestedClasses()
                        + "}\n";

        try {
            FileWriter outputFile = new FileWriter( outputClassName + ".java" );
            outputFile.write( aClassString );
            outputFile.close();
            String cp = System.getProperty( "java.class.path" );
            Process p = Runtime.getRuntime().exec( "javac -source 1.4 -classpath \""
                            + cp
                            + "\" "
                            + outputClassName
                            + ".java");
            p.waitFor();
            if ( p.exitValue() == 0 ) {
                outputClassObject = Class.forName(qualifiedOutputClassName);
            } else {
                InputStream errStream = p.getErrorStream();
                for ( int j = errStream.available(); j > 0; j-- )
                    System.out.write( errStream.read() );
                throw new C2CException( "compile fails " + p.exitValue() );
            }
        } catch(Exception e){ throw new C2CException(e); }

        checkPostconditions();
        System.out.println( outputClassName + " compiled and loaded" );
        return outputClassObject;
    }

    // the methods that concrete subclasses of C2C override to fill out the
    //template provided by createClass
    abstract protected String generateFlags();
    abstract protected String generateClassNamePrefix();
    /**
     * 处理参数，决定输出类名
     * */
    abstract protected void checkAndProcessArgs( Args args );
    abstract protected UQueue generateImports();
    abstract protected String getClassLevelJavadoc();
    abstract protected String getSuperclass();
    abstract protected UQueue generateInterfaces();
    abstract protected String generateFields();
    abstract protected String generateConstructors();
    abstract protected String generateMethods();
    abstract protected String generateNestedClasses();
    abstract protected void checkPostconditions();

    protected final void setAbstract() { isAbstract = true; }
    protected final boolean isAbstract() { return isAbstract; }

    protected final void setFinal() { isFinal = true; }
    protected final boolean isFinal() { return isFinal; }

    protected final void setInterface() { isInterface = true; }
    protected final boolean isInterface() { return isInterface; }

    protected final void setNotPublic() { isNotPublic = true; }
    protected final boolean isNotPublic() { return isNotPublic; }
}