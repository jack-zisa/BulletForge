package dev.creoii.forge;

public class ForgeParseException extends RuntimeException {
    private final Type type;

    private ForgeParseException(Type type, String message) {
        super(message);
        this.type = type;
    }

    public static ForgeParseException createVersionException(String message) {
        return new ForgeParseException(Type.VERSION, message);
    }

    @Override
    public String getMessage() {
        return type.message + ": " + super.getMessage();
    }

    public enum Type {
        VERSION("Error parsing version");

        private final String message;

        Type(String message) {
            this.message = message;
        }
    }
}
