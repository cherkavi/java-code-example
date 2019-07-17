package jmx.utility.server.description_decorator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * skip notification - not for JMX 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
// Inherited
public @interface AMBeanNotificationSkip {
}
