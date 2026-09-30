import dev.creoii.forge.Forge;
import dev.creoii.forge.parse.ForgeParser;
import dev.creoii.forge.script.Forgescript;

void main() throws IOException {
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("test.forge")) {
        if (is == null) {
            throw new IllegalArgumentException("File not found!");
        }

        Forge forge = new Forge();

        String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        Forgescript forgescript = ForgeParser.parse(content);
        forge.getExecutor().register(forgescript);

        forge.getExecutor().execute();
    }
}
