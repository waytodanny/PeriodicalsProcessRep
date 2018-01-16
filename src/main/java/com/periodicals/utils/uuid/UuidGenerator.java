package com.periodicals.utils.uuid;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class UuidGenerator {
    public static String generateUuid() {
        UUID uuid = Generators.timeBasedGenerator().generate();
        return uuid.toString();
    }
}
