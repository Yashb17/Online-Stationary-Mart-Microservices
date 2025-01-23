package com.onlinestationarymart.domain_service.enums;

public enum OrderStatusEnum {
    ORDER_RECEIVED("RECEIVED"),
    ORDER_CANCELLED("CANCELLED"),
    ORDER_DELIVERED("DELIVERED"),
    OUT_FOR_DELIVERY("OUT_FOR_DELIVERY");

    private final String displayName;

    OrderStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
