package dev.creoii.forge;

import dev.creoii.forge.script.Forgescript;

import java.util.ArrayList;
import java.util.List;

public class ForgeExecutor {
    private final List<Forgescript> forgescripts;

    public ForgeExecutor() {
        forgescripts = new ArrayList<>();
    }

    public void register(Forgescript forgescript) {
        forgescripts.add(forgescript);
    }

    public void execute() {
        forgescripts.forEach(Forgescript::execute);
    }
}
