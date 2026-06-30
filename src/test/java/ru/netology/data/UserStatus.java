package ru.netology.data;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("active"),
    BLOCKED("blocked");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }
}