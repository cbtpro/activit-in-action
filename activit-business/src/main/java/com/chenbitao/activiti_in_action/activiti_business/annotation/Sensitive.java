package com.chenbitao.activiti_in_action.activiti_business.annotation;

import com.chenbitao.activiti_in_action.activiti_business.enums.MaskType;
import com.chenbitao.activiti_in_action.activiti_business.serializer.SensitiveSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerializer.class) // 指向自定义序列化器
public @interface Sensitive {
    MaskType type();
}