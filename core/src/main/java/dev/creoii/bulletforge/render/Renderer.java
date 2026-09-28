package dev.creoii.bulletforge.render;

import com.badlogic.gdx.utils.Disposable;

public interface Renderer extends Disposable {
    void create();

    void render(float delta);
}
