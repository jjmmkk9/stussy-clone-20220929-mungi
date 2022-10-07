package com.demo.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)             // 언제? runtime 때 쓴다.
@Target({ElementType.TYPE,ElementType.METHOD})  //클래스랑 메소드 위에 달 수 있다.
public @interface ValidAspect {

}
