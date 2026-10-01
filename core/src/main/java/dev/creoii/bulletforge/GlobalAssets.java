package dev.creoii.bulletforge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class GlobalAssets {
    public static final Skin SKIN = new Skin(Gdx.files.internal("uiskin.json"));
    public static final BitmapFont FONT = SKIN.getFont("default-font");
    private static final ShaderProgram BORDER_SHADER = new ShaderProgram(Gdx.files.internal("shaders/border.vert"), Gdx.files.internal("shaders/border.frag"));

    public static final Texture EXIT_ICON = new Texture(Gdx.files.internal("sprites/ui/exit.png"));
    public static final Texture MINIMIZE_ICON = new Texture(Gdx.files.internal("sprites/ui/minimize.png"));
    public static final Texture MAXIMIZE_ICON = new Texture(Gdx.files.internal("sprites/ui/maximize.png"));
    public static final Texture DROPDOWN = new Texture(Gdx.files.internal("sprites/ui/dropdown.png"));
    public static final Texture DROPUP = new Texture(Gdx.files.internal("sprites/ui/dropup.png"));
    public static final Texture AUTOFIRE = new Texture(Gdx.files.internal("sprites/ui/autofire.png"));
    public static final Texture TARGET = new Texture(Gdx.files.internal("sprites/ui/target.png"));
    public static final Texture AUTOFIRE_OFF = new Texture(Gdx.files.internal("sprites/ui/autofire_off.png"));
    public static final Texture TARGET_OFF = new Texture(Gdx.files.internal("sprites/ui/target_off.png"));
    public static final Texture HOME = new Texture(Gdx.files.internal("sprites/ui/home.png"));

    public static final Texture DEFAULT_BULLET = new Texture(Gdx.files.internal("sprites/default_bullet.png"));

    public static void dispose() {
        SKIN.dispose();
        BORDER_SHADER.dispose();
    }
}
