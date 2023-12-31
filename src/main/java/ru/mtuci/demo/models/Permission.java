package ru.mtuci.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    READ("read"),
    DELETE("delete"),
    MODIFICATION("modification"),
    CREATE("create");

    private final String permission;
}
