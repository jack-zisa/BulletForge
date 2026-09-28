package dev.creoii.bulletforge.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.creoii.bulletforge.BulletForge;

public class SpriteRenderer implements Renderer {
    private final BulletForge main;
    private SpriteBatch batch;

    public SpriteRenderer(BulletForge main) {
        this.main = main;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(main.getCamera().combined);

        batch.begin();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
