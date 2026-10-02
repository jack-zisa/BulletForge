package dev.creoii.bulletforge.render.screen;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.element.AutofireButton;
import dev.creoii.bulletforge.render.screen.element.ResetPositionButton;
import dev.creoii.bulletforge.render.screen.element.TargetMouseButton;
import dev.creoii.bulletforge.util.manager.AttackManager;
import dev.creoii.bulletforge.util.manager.GlobalBulletManager;
import dev.creoii.bulletforge.editor.EditorConfig;
import dev.creoii.bulletforge.render.screen.element.editor.AttacksEditorPane;
import dev.creoii.bulletforge.render.screen.element.editor.BulletsEditorPane;

public class EditorScreen extends AbstractScreen {
    public static final float EDITOR_PANE_WIDTH = 300f;
    private final BulletForge main;
    private final EditorConfig config;
    private final AttackManager attackManager;
    private final GlobalBulletManager bulletManager;
    private final BulletsEditorPane bulletEditorPane;
    private final AttacksEditorPane attackEditorPane;

    public EditorScreen(BulletForge main) {
        super(main);
        this.main = main;
        config = new EditorConfig();

        attackManager = new AttackManager(main, this);
        bulletManager = new GlobalBulletManager(main, this);

        getRoot().setFillParent(true);
        getRoot().top().left();

        ScrollPane scrollPane = new ScrollPane(bulletEditorPane = new BulletsEditorPane(this), GlobalAssets.SKIN);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setOverscroll(false, false);
        scrollPane.setScrollingDisabled(true, false);
        getRoot().add(scrollPane).width(EDITOR_PANE_WIDTH).growY().top().left();

        Table center = new Table();
        Table toolsTable = new Table();
        toolsTable.add(new AutofireButton(main)).left();
        toolsTable.add(new TargetMouseButton(main)).left();
        toolsTable.add(new ResetPositionButton(main)).left();
        center.add(toolsTable).left().row();
        center.add().grow().fill();
        getRoot().add(center).growX().growY().top();

        ScrollPane scrollPane1 = new ScrollPane(attackEditorPane = new AttacksEditorPane(this), GlobalAssets.SKIN);
        scrollPane1.setFadeScrollBars(false);
        scrollPane1.setOverscroll(false, false);
        scrollPane1.setScrollingDisabled(true, false);
        getRoot().add(scrollPane1).width(EDITOR_PANE_WIDTH).growY().top().right();
    }

    @Override
    public void render(float delta) {
        if (config.isDirty()) {
            bulletEditorPane.refresh();
            bulletManager.refresh();
            attackEditorPane.refresh();
            attackManager.refresh();
            config.setNotDirty();
        }

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

    public BulletForge getMain() {
        return main;
    }

    public EditorConfig getConfig() {
        return config;
    }

    public AttackManager getAttackManager() {
        return attackManager;
    }

    public GlobalBulletManager getBulletManager() {
        return bulletManager;
    }
}
