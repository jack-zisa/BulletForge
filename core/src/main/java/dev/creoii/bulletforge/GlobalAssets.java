package dev.creoii.bulletforge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public final class GlobalAssets {
    private static final ShaderProgram BORDER_SHADER = new ShaderProgram(Gdx.files.internal("shaders/border.vert"), Gdx.files.internal("shaders/border.frag"));
}
