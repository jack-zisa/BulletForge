package dev.creoii.bulletforge.object.definition;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.reflect.Field;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.render.screen.element.editor.ExpandableEditorPane;
import dev.creoii.bulletforge.util.Codecs;
import dev.creoii.bulletforge.util.editor.EditorOption;
import dev.creoii.bulletforge.util.editor.EditorSerializable;

import java.util.Objects;

public final class Offset implements EditorOption {
    public static final Offset DEFAULT = new Offset(new Vector2(), false, true);
    public static final Codec<Offset> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codecs.VECTOR2.optionalFieldOf("offset", new Vector2()).forGetter(Offset::offset),
            Codec.BOOL.optionalFieldOf("affect_mouse", false).forGetter(Offset::affectMouse),
            Codec.BOOL.optionalFieldOf("rotate", true).forGetter(Offset::rotate)
        ).apply(instance, Offset::new);
    });
    @EditorSerializable
    private final Vector2 offset;
    @EditorSerializable
    private boolean affectMouse;
    @EditorSerializable
    private boolean rotate;

    public Offset(Vector2 offset, boolean affectMouse, boolean rotate) {
        this.offset = offset;
        this.affectMouse = affectMouse;
        this.rotate = rotate;
    }

    public Offset copy() {
        return new Offset(offset, affectMouse, rotate);
    }

    public void set(Offset offset) {
        this.offset.set(offset.offset);
        affectMouse = offset.affectMouse;
        rotate = offset.rotate;
    }

    public Vector2 offset() {
        return offset;
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
        var that = (Offset) obj;
        return affectMouse == that.affectMouse && rotate == that.rotate &&
            offset.equals(that.offset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(affectMouse, rotate, offset);
    }

    @Override
    public String toString() {
        return "Offset[" +
            "targetMouse=" + affectMouse + ", " +
            "rotate=" + rotate + ", " +
            "offset=" + offset.toString() + ']';
    }

    @Override
    public void create(Table table, Object target, Field field, Skin skin) {
        TextButton header = new TextButton("Offset", skin);
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
