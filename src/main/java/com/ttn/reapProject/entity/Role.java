package com.ttn.reapProject.entity;

public enum Role {
    USER("User"),
    ADMIN("Admin");
    String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
