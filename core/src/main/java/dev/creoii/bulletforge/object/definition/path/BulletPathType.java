package dev.creoii.bulletforge.object.definition.path;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.reflect.Field;
import com.mojang.serialization.Codec;
import dev.creoii.bulletforge.object.definition.BulletNodeDefinition;
import dev.creoii.bulletforge.object.instance.BulletNode;
import dev.creoii.bulletforge.render.screen.element.CyclingButton;
import dev.creoii.bulletforge.render.screen.element.editor.ExpandableEditorPane;
import dev.creoii.bulletforge.util.editor.EditorGenerator;
import dev.creoii.bulletforge.util.editor.EditorOption;

import java.util.function.Supplier;

public interface BulletPathType<T extends BulletPathType.Instance<?>> extends EditorOption {
    Codec<BulletPathType<?>> CODEC = Type.CODEC.dispatch(BulletPathType::type, type -> switch (type) {
        case STRAIGHT -> StraightBulletPathType.TYPE_CODEC;
        case WAVY -> WavyBulletPathType.TYPE_CODEC;
        case ORBIT -> OrbitBulletPathType.TYPE_CODEC;
        case PARAMETRIC -> ParametricBulletPathType.TYPE_CODEC;
    });

    Type type();

    T create();

    @Override
    default void create(Table table, Object target, Field field, Skin skin) {
        CyclingButton<Type> pathTypeButton = new CyclingButton<>("Path Type", Type.values(), skin);
        ExpandableEditorPane pane = new ExpandableEditorPane(this, skin);

        BulletNodeDefinition bulletNodeDefinition = (BulletNodeDefinition) target;

        pathTypeButton.setOnChanged(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Type type = pathTypeButton.getSelectedValue();
                bulletNodeDefinition.setPath(type.defaultTypeInstance.get());
                pane.setContent(EditorGenerator.createEditorTable(bulletNodeDefinition.path(), skin));
                pane.setExpanded(type != Type.STRAIGHT);
            }
        });

        table.add(pathTypeButton).colspan(2).growX().row();
        table.add(pane);
    }

    abstract class Instance<T extends BulletPathType<?>> implements Pool.Poolable {
        private final T type;
        private final Vector2 offset;

        public Instance(T type) {
            this.type = type;
            offset = new Vector2();
        }

        public T getType() {
            return type;
        }

        public void setOffset(float x, float y) {
            offset.set(x, y);
        }

        public Vector2 getOffset() {
            return offset;
        }

        public Vector2 getPathOffset(BulletNode node, float t) {
            return offset;
        }

        public Vector2 getDirection(BulletNode node, Vector2 offset1, Vector2 offset2) {
            float dx = offset2.x - offset1.x;
            float dy = offset2.y - offset1.y;

            float len = (float) Math.sqrt(dx * dx + dy * dy);

            if (len == 0f)
                return Vector2.One;
            return new Vector2(dx / len, dy / len);
        }

        @Override
        public void reset() {
            offset.setZero();
        }

        public abstract Instance<T> copy();
    }

    enum Type {
        STRAIGHT(false, StraightBulletPathType::new),
        WAVY(true, () -> new WavyBulletPathType(WavyBulletPathType.WaveType.SIN, 0f, 0f, false)),
        ORBIT(true, () -> new OrbitBulletPathType(-1, 1f)),
        PARAMETRIC(true, () -> new ParametricBulletPathType(ParametricBulletPathType.ParametricType.FIGURE_EIGHT, new Vector2(1f, 1f)));

        public static final Codec<Type> CODEC = Codec.STRING.xmap(s -> Type.valueOf(s.toUpperCase()), type -> type.name().toLowerCase());
        private final boolean requiresUpdate;
        private final Supplier<BulletPathType<?>> defaultTypeInstance;

        Type(boolean requiresUpdate, Supplier<BulletPathType<?>> defaultTypeInstance) {
            this.requiresUpdate = requiresUpdate;
            this.defaultTypeInstance = defaultTypeInstance;
        }

        public boolean requiresUpdate() {
            return requiresUpdate;
        }
    }
}
