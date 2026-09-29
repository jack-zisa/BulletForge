package dev.creoii.bulletforge.render.screen;

import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.attack.AttackManager;
import dev.creoii.bulletforge.bullet.BulletManager;

public class EditorScreen extends AbstractScreen {
    private final BulletForge main;
    private final AttackManager attackManager;
    private final BulletManager bulletManager;

    public EditorScreen(BulletForge main) {
        super(main);
        this.main = main;
        attackManager = new AttackManager(main, this);
        bulletManager = new BulletManager(main, this);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        attackManager.tick(delta);
        bulletManager.tick(delta);

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

    public AttackManager getAttackManager() {
        return attackManager;
    }

    public BulletManager getBulletManager() {
        return bulletManager;
    }
}
