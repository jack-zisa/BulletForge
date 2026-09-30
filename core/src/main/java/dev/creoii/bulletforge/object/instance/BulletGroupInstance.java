package dev.creoii.bulletforge.object.instance;

import com.badlogic.gdx.graphics.g2d.Batch;
import dev.creoii.bulletforge.object.definition.BulletGroupDefinition;
import dev.creoii.bulletforge.object.definition.Offset;

import java.util.ArrayList;
import java.util.List;

public class BulletGroupInstance extends AbstractBulletInstance<BulletGroupDefinition> {
    private final List<Child> children;

    public BulletGroupInstance() {
        this.children = new ArrayList<>();
    }

    public void addChild(Child child) {
        children.add(child);
        child.node.setParent(this);
    }

    @Override
    public void tick(float dt) {
        super.tick(dt);
        children.forEach(child -> child.node.tick(dt));
    }

    @Override
    public void render(Batch batch) {
        children.forEach(child -> child.node.render(batch));
    }

    @Override
    public void reset() {
        super.reset();
        children.clear();
    }

    public static class Child {
        private final BulletNode<?> node;
        private final Offset offset;

        public Child(BulletNode<?> node, Offset offset) {
            this.node = node;
            this.offset = offset;
        }
    }
}
