package com.periodicals.utils.uuid;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class UuidGenerator {
    public static UUID generateUuid() {
        return Generators.timeBasedGenerator().generate();
    }
}
