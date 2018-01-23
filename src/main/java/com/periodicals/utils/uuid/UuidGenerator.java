package com.periodicals.utils.uuid;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

/**
 * Class responsible for uuid objects generation
 */
public class UuidGenerator {
    public static UUID generateSequentialUuid() {
        return Generators.timeBasedGenerator().generate();
    }
}
