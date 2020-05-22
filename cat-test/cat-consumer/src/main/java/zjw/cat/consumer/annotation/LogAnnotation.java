package zjw.cat.consumer.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 系统日志注解
 * </p>
 *
 * @author clive
 * @since 2019-06-24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String value() default "";
}
