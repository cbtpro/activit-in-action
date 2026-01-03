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

public class SensitiveSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    private MaskType type;
    private int prefix;
    private int suffix;

    // 必须保留无参构造函数
    public SensitiveSerializer() {
    }

    public SensitiveSerializer(MaskType type, int prefix, int suffix) {
        this.type = type;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        // 统一转换为字符串处理脱敏逻辑
        String originValue = String.valueOf(value);

        switch (type) {
            case USERNAME:
            case NAME:
                gen.writeString(mask(originValue, prefix, suffix));
                break;
            case EMAIL:
                gen.writeString(originValue.replaceAll("(^.{2})[^@]+(@.*$)", "$1****$2"));
                break;
            case AGE:
                // 特殊处理数字类型的年龄
                processAgeMask(value, gen);
                break;
            default:
                gen.writeObject(value);
        }
    }

    private void processAgeMask(Object value, JsonGenerator gen) throws IOException {
        try {
            int age = Integer.parseInt(String.valueOf(value));
            if (age < 10) {
                gen.writeString("10岁以下");
            } else {
                gen.writeString((age / 10 * 10) + "+");
            }
        } catch (Exception e) {
            gen.writeObject(value); // 转换失败则原样输出
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property != null) {
            Sensitive annotation = property.getAnnotation(Sensitive.class);
            if (annotation != null) {
                return new SensitiveSerializer(annotation.type(), annotation.prefix(), annotation.suffix());
            }
        }
        // 如果没有注解，根据实际类型寻找对应的默认序列化器
        return prov.findValueSerializer(property.getType(), property);
    }

    private String mask(String s, int prefix, int suffix) {
        if (s == null || s.length() <= (prefix + suffix)) return "***";
        StringBuilder sb = new StringBuilder();
        sb.append(s, 0, prefix).append("****");
        if (suffix > 0) sb.append(s.substring(s.length() - suffix));
        return sb.toString();
    }
}