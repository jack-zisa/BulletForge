package dev.creoii.forge.parse;

import dev.creoii.forge.value.datatype.DataType;

public record FunctionDefinition(String name, DataType<?> dataType, DataType<?>... args) {
}
