package com.chenbitao.activiti_in_action.activiti_business.serializer;

import com.chenbitao.activiti_in_action.activiti_business.annotation.Sensitive;
import com.chenbitao.activiti_in_action.activiti_business.enums.MaskType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;

public class SensitiveSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private MaskType type;

    // 必须保留无参构造函数
    public SensitiveSerializer() {
    }

    public SensitiveSerializer(MaskType type) {
        this.type = type;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        // 执行脱敏逻辑
        switch (type) {
            case USERNAME:
                gen.writeString(mask(value, 1, 1));
                break;
            case NAME:
                gen.writeString(mask(value, 1, 0));
                break;
            case EMAIL:
                gen.writeString(value.replaceAll("(^.{2})[^@]+(@.*$)", "$1****$2"));
                break;
            default:
                gen.writeString(value);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property != null) {
            // 判断当前处理的是否是 String 类型
            if (property.getType().getRawClass().equals(String.class)) {
                Sensitive annotation = property.getAnnotation(Sensitive.class);
                if (annotation != null) {
                    return new SensitiveSerializer(annotation.type());
                }
            }
        }
        // 如果没有注解，直接获取 Jackson 预置的标准 String 序列化器，不再寻找自定义的
        return prov.findValueSerializer(String.class, property);
    }

    private String mask(String s, int prefix, int suffix) {
        if (s == null || s.length() <= (prefix + suffix)) return "***";
        StringBuilder sb = new StringBuilder();
        sb.append(s, 0, prefix).append("****");
        if (suffix > 0) sb.append(s.substring(s.length() - suffix));
        return sb.toString();
    }
}