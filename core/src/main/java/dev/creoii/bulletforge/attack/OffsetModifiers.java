package dev.creoii.bulletforge.attack;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.reflect.Field;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.render.screen.element.editor.ExpandableEditorPane;
import dev.creoii.bulletforge.util.editor.EditorOption;
import dev.creoii.bulletforge.util.editor.EditorSerializable;

import java.util.Objects;

public final class OffsetModifiers implements EditorOption {
    public static final OffsetModifiers DEFAULT = new OffsetModifiers(false, true);
    public static final Codec<OffsetModifiers> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.BOOL.optionalFieldOf("affect_mouse", false).forGetter(OffsetModifiers::affectMouse),
            Codec.BOOL.optionalFieldOf("rotate", true).forGetter(OffsetModifiers::rotate)
        ).apply(instance, OffsetModifiers::new);
    });
    @EditorSerializable
    private boolean affectMouse;
    @EditorSerializable
    private boolean rotate;

    public OffsetModifiers(boolean affectMouse, boolean rotate) {
        this.affectMouse = affectMouse;
        this.rotate = rotate;
    }

    public OffsetModifiers copy() {
        return new OffsetModifiers(affectMouse, rotate);
    }

    public void set(OffsetModifiers offsetModifiers) {
        affectMouse = offsetModifiers.affectMouse;
        rotate = offsetModifiers.rotate;
    }

    public boolean affectMouse() {
        return affectMouse;
    }

    public boolean rotate() {
        return rotate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        var that = (OffsetModifiers) obj;
        return affectMouse == that.affectMouse && rotate == that.rotate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(affectMouse, rotate);
    }

    @Override
    public String toString() {
        return "OffsetModifiers[" +
            "targetMouse=" + affectMouse + ", " +
            "rotate=" + rotate + ']';
    }

    @Override
    public void create(Table table, Object target, Field field, Skin skin) {
        TextButton header = new TextButton("Offset Modifiers", skin);
        ExpandableEditorPane pane = new ExpandableEditorPane(this, skin);

        header.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pane.setExpanded(!pane.isExpanded());
            }
        });

        table.row();
        table.add(header).growX().left().colspan(2).row();
        table.add(pane).growX().left().colspan(2).row();
    }
}
