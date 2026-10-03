package dev.creoii.bulletforge.object.instance;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import dev.creoii.bulletforge.object.definition.BulletGroupDefinition;
import dev.creoii.bulletforge.object.definition.BulletNodeDefinition;
import dev.creoii.bulletforge.object.definition.Offset;
import dev.creoii.bulletforge.util.manager.BulletManager;
import dev.creoii.providerlib.api.context.Context;

import java.util.*;

public class BulletGroupInstance extends AbstractBulletInstance implements BulletManager {
    private final Map<Long, Child> children;
    private final Set<Long> toRemove;

    public BulletGroupInstance() {
        children = new HashMap<>();
        toRemove = new HashSet<>();
    }

    public void initManager(BulletManager parent) {
        this.parent = parent;
    }

    @Override
    public long allocateId() {
        return parent.allocateId();
    }

    @Override
    public void freeId(long id) {
        parent.freeId(id);
    }

    @Override
    public void addBullet(BulletNode child) {
        child.init(allocateId());
        adoptBullet(child);
    }

    @Override
    public void adoptBullet(BulletNode child) {
        if (!(child instanceof Child)) {
            child = new Child(child, Offset.DEFAULT.copy());
        }

        child.setParent(this);
        children.put(child.id(), (Child) child);

        if (((Child) child).node instanceof BulletGroupInstance group) {
            group.initManager(this);
        }
    }

    @Override
    public void removeBullet(long id) {
        Child child = children.remove(id);
        if (child == null) return;

        child.setParent(null);
        freeId(id);

        if (child.node instanceof BulletGroupInstance group) {
            group.detachChildren();
        }
    }

    @Override
    public void transferBullet(BulletNode child) {
        children.remove(child.id());
        parent.adoptBullet(child);
    }

    @Override
    public void tick(float dt) {
        super.tick(dt);

        float rotation = get().rotation() * age;

        for (Child child : children.values()) {
            child.tick(dt);

            if (!child.pos().equals(pos)) { // no need to rotate if the child is at the center already
                Vector2 offset = child.offset.offset().cpy().rotateDeg(rotation);
                child.pos().set(pos.x + offset.x, pos.y + offset.y);
            }

            if (child.isDead()) {
                toRemove.add(child.id());
            }
        }

        toRemove.forEach(this::removeBullet);
        toRemove.clear();
    }

    @Override
    public void render(Batch batch) {
        children.values().forEach(child -> child.render(batch));
    }

    @Override
    public void onDead() {
        detachChildren();
    }

    @Override
    public void refresh() {
        detachChildren();
        toRemove.clear();
    }

    @Override
    public void reset() {
        super.reset();
        detachChildren();
        children.clear();
        toRemove.clear();
    }

    public void detachChildren() {
        if (parent == null) {
            children.clear();
            return;
        }

        for (Child child : new ArrayList<>(children.values())) {
            children.remove(child.id());
            parent.adoptBullet(child);
        }
    }

    @Override
    public int childrenCount() {
        return children.size();
    }

    @Override
    public void set(BulletNodeDefinition definition) {
        super.set(definition);

        if (definition instanceof BulletGroupDefinition groupDefinition) {
            for (BulletGroupDefinition.Child definitionChild : groupDefinition.children()) {
                BulletNodeDefinition childDefinition = definitionChild.bullet();

                BulletNode node = childDefinition.type() == BulletNodeDefinition.Type.SINGLE ? parent.getBulletPool().obtain() : parent.getBulletGroupPool().obtain();

                node.set(childDefinition);

                addBullet(new Child(node, definitionChild.offset().copy()));
            }
        }
    }

    @Override
    public void spawn(float x, float y, float dirX, float dirY, int index) {
        super.spawn(x, y, dirX, dirY, index);

        for (Child child : children.values()) {
            Vector2 offset = child.offset.offset();

            child.spawn(x + offset.x, y + offset.y, dirX, dirY, index);
        }
    }

    public static class Child implements BulletNode {
        private final BulletNode node;
        private final Offset offset;

        public Child(BulletNode node, Offset offset) {
            this.node = node;
            this.offset = offset;
        }

        @Override
        public long id() {
            return node.id();
        }

        @Override
        public void init(long id) {
            node.init(id);
        }

        @Override
        public BulletNodeDefinition get() {
            return node.get();
        }

        @Override
        public void spawn(float x, float y, float dirX, float dirY, int index) {
            node.spawn(x, y, dirX, dirY, index);
        }

        @Override
        public BulletManager parent() {
            return node.parent();
        }

        @Override
        public void setParent(BulletManager parent) {
            node.setParent(parent);
        }

        @Override
        public Vector2 pos() {
            return node.pos();
        }

        @Override
        public Vector2 spawnPos() {
            return node.spawnPos();
        }

        @Override
        public float incrementAge(float f) {
            return node.incrementAge(f);
        }

        @Override
        public int index() {
            return node.index();
        }

        @Override
        public boolean isDead() {
            return node.isDead();
        }

        @Override
        public void set(BulletNodeDefinition definition) {
            node.set(definition);
        }

        @Override
        public void reset() {
            node.reset();
        }

        @Override
        public void render(Batch batch) {
            node.render(batch);
        }

        @Override
        public void tick(float dt) {
            node.tick(dt);
        }

        @Override
        public Context context() {
            return node.context();
        }
    }
}
