package dev.creoii.bulletforge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class GlobalAssets {
    public static final Skin SKIN = new Skin(Gdx.files.internal("uiskin.json"));
    private static final ShaderProgram BORDER_SHADER = new ShaderProgram(Gdx.files.internal("shaders/border.vert"), Gdx.files.internal("shaders/border.frag"));

    public static void dispose() {
        SKIN.dispose();
        BORDER_SHADER.dispose();
    }
}
