package org.ldd.ssm.crm.aop;
import java.lang.annotation.*;
@Target({ElementType.METHOD})   
@Retention(RetentionPolicy.RUNTIME)   
@Documented
public abstract @interface MyMethodNote {
String comment()default"无描述信息";
}
