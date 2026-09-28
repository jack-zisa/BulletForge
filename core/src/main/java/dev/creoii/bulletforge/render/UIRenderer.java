package dev.creoii.bulletforge.render;

import dev.creoii.bulletforge.BulletForge;

public class UIRenderer implements Renderer {
    private final BulletForge main;

    public UIRenderer(BulletForge main) {
        this.main = main;
    }

    @Override
    public void create() {
    }

    @Override
    public void render(float delta) {
        main.getScreen().render(delta);
    }

    @Override
    public void dispose() {
    }
}
