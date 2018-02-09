package org.ldd.ssm.crm.web.interceptor;
import java.lang.annotation.*;
@Target({ElementType.METHOD})   
@Retention(RetentionPolicy.RUNTIME)   
@Documented
public abstract @interface MethodNote {
String comment()default"无描述信息";
}
