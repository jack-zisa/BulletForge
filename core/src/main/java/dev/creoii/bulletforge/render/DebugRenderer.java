package dev.creoii.bulletforge.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import dev.creoii.bulletforge.BulletForge;

public class DebugRenderer implements Renderer {
    private final BulletForge main;
    private ShapeRenderer shapeRenderer;

    public DebugRenderer(BulletForge main) {
        this.main = main;
    }

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        shapeRenderer.setProjectionMatrix(main.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        drawMouseLine();

        shapeRenderer.end();
    }

    public void drawMouseLine() {
        Vector3 mouseDir = main.getInputHandler().getDirectionToMouse();
        Vector3 end = new Vector3(mouseDir.scl(Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()) * .25f));
        shapeRenderer.line(0f, 0f, end.x, end.y);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
