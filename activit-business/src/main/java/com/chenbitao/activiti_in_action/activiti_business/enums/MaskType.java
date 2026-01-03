package com.chenbitao.activiti_in_action.activiti_business.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MaskType {
    USERNAME("username"), NAME("name"), EMAIL("email"), AGE("age");

    private final String field;

    @JsonCreator
    public static MaskType fromType(String type) {
        for (MaskType mt : values()) {
            if (mt.field.equalsIgnoreCase(type)) {
                return mt;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }

}