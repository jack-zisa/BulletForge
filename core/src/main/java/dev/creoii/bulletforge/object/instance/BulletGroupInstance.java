package dev.creoii.bulletforge.object.instance;

import com.badlogic.gdx.graphics.g2d.Batch;
import dev.creoii.bulletforge.object.definition.BulletGroupDefinition;

import java.util.ArrayList;
import java.util.List;

public class BulletGroupInstance extends AbstractBulletInstance<BulletGroupDefinition> {
    private final List<BulletNode<?>> children;

    public BulletGroupInstance() {
        this.children = new ArrayList<>();
    }

    public void addChild(BulletNode<?> bulletNode) {
        children.add(bulletNode);
    }

    @Override
    public void tick(float dt) {
        super.tick(dt);
        children.forEach(bulletNode -> bulletNode.tick(dt));
    }

    @Override
    public void render(Batch batch) {
        children.forEach(bulletNode -> bulletNode.render(batch));
    }

    @Override
    public void reset() {
        super.reset();
        children.clear();
    }
}
