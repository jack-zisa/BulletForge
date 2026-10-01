package dev.creoii.bulletforge.object.instance;

import com.badlogic.gdx.graphics.g2d.Batch;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.object.definition.BulletDefinition;

public class BulletInstance extends AbstractBulletInstance {
    @Override
    public void render(Batch batch) {
        float scale = 10f;
        if (get() instanceof BulletDefinition bulletDefinition) {
            scale = bulletDefinition.display().scale();
        }
        float rotation = get().rotation() * age;
        batch.draw(GlobalAssets.DEFAULT_BULLET,
            pos.x - (scale / 2f), pos.y - (scale / 2f),
            scale / 2f, scale / 2f,
            scale, scale,
            1f, 1f,
            rotation,
            0, 0,
            GlobalAssets.DEFAULT_BULLET.getWidth(), GlobalAssets.DEFAULT_BULLET.getHeight(),
            false, false
        );
    }
}
