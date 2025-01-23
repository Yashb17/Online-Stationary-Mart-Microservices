package com.onlinestationarymart.domain_service.enums;

public enum NotificationEnum {

    INVENTORY_REFILLING_REQUIRED("INVENTORY_REFILL"),
    ORDER_RECEIVED("RECEIVED"),
    ORDER_CANCELLED("CANCELLED"),
    ORDER_DELIVERED("DELIVERED"),
    OUT_FOR_DELIVERY("OUT_FOR_DELIVERY");

    private final String displayName;

    NotificationEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
