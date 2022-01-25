package zzk.study.java.core.lang.reflect;

import kafka.log.Log;
import org.junit.Test;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/1/24 3:55 PM
 */
public class ThrowableDemo {

    @Test
    public void demo(){
        Logger logger = new LoggerImpl();
        logger.logRecord("log test", 1);
    }

    public interface Logger{
        // Types for log records
        public static final int ERROR = 0;
        public static final int WARNING = 100;
        public static final int STATUS = 200;
        public static final int DEBUG = 300;
        public static final int TRACE = 400;
        void logRecord( String message, int logRecordType );
        void logProblem( Throwable problem );
    }

    public class LoggerImpl implements Logger {
        public void logRecord( String message, int logRecordType ) {
            Throwable ex = new Throwable();
            StackTraceElement ste = ex.getStackTrace()[1];
            String callerClassName = ste.getClassName();
            String callerMethodName = ste.getMethodName();
            int callerLineNum = ste.getLineNumber();

            System.out.printf(" caller:%s\n method:%s\n msg:%s", callerClassName, callerMethodName, message);
        }
        public void logProblem( Throwable t ) {
            // write of log record goes here
        }
    }
}
