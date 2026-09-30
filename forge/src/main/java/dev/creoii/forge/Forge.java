package dev.creoii.forge;

public class Forge {
    public static final String FILE_EXTENSION = "forge";
    private final ForgeExecutor executor;

    public Forge() {
        executor = new ForgeExecutor();
    }

    public ForgeExecutor getExecutor() {
        return executor;
    }
}
