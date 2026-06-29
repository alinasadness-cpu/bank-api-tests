package ru.netology.data;

public enum UserStatus {
    ACTIVE("active"),
    BLOCKED("blocked");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}