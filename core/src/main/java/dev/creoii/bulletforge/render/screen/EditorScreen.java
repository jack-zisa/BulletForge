package dev.creoii.bulletforge.render.screen;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.attack.AttackManager;
import dev.creoii.bulletforge.bullet.BulletManager;
import dev.creoii.bulletforge.editor.EditorConfig;
import dev.creoii.bulletforge.render.screen.element.editor.AttacksEditorPane;
import dev.creoii.bulletforge.render.screen.element.editor.BulletsEditorPane;

public class EditorScreen extends AbstractScreen {
    public static final float EDITOR_PANE_WIDTH = 300f;
    private final BulletForge main;
    private final EditorConfig config;
    private final AttackManager attackManager;
    private final BulletManager bulletManager;
    private final BulletsEditorPane bulletEditorPane;
    private final AttacksEditorPane attackEditorPane;

    public EditorScreen(BulletForge main) {
        super(main);
        this.main = main;
        config = new EditorConfig();

        attackManager = new AttackManager(main, this);
        bulletManager = new BulletManager(main, this);

        getRoot().setFillParent(true);
        getRoot().top().right();

        ScrollPane scrollPane = new ScrollPane(bulletEditorPane = new BulletsEditorPane(this), GlobalAssets.SKIN);
        scrollPane.setFadeScrollBars(false);
        getRoot().add(scrollPane).width(EDITOR_PANE_WIDTH).growY().top().left();

        getRoot().add().grow().fill();

        ScrollPane scrollPane1 = new ScrollPane(attackEditorPane = new AttacksEditorPane(this), GlobalAssets.SKIN);
        scrollPane1.setFadeScrollBars(false);
        getRoot().add(scrollPane1).width(EDITOR_PANE_WIDTH).growY().right();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        attackManager.tick(delta);
        bulletManager.tick(delta);

        getStage().getBatch().setProjectionMatrix(main.getCamera().combined);
        getStage().getBatch().begin();
        bulletManager.render(getStage().getBatch());
        getStage().getBatch().end();
    }

    @Override
    public void hide() {
        super.hide();
        main.getInput().removeProcessor(attackManager);
    }

    @Override
    public void show() {
        super.show();
        main.getInput().addProcessor(2, attackManager);
    }

    public EditorConfig getConfig() {
        return config;
    }

    public AttackManager getAttackManager() {
        return attackManager;
    }

    public BulletManager getBulletManager() {
        return bulletManager;
    }
}
