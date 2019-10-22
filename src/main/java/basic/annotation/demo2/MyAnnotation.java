package basic.annotation.demo2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String[] value() default "unknown";
}
