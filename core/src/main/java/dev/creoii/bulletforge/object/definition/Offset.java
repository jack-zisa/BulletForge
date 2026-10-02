package dev.creoii.bulletforge.object.definition;

import com.badlogic.gdx.math.Vector2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.util.Codecs;

import java.util.Objects;

public final class Offset {
    public static final Offset DEFAULT = new Offset(new Vector2(), false, true);
    public static final Codec<Offset> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codecs.VECTOR2.optionalFieldOf("offset", new Vector2()).forGetter(Offset::offset),
            Codec.BOOL.optionalFieldOf("affect_mouse", false).forGetter(Offset::affectMouse),
            Codec.BOOL.optionalFieldOf("rotate", true).forGetter(Offset::rotate)
        ).apply(instance, Offset::new);
    });
    private final Vector2 offset;
    private boolean affectMouse;
    private boolean rotate;

    public Offset(Vector2 offset, boolean affectMouse, boolean rotate) {
        this.offset = offset;
        this.affectMouse = affectMouse;
        this.rotate = rotate;
    }

    public Offset copy() {
        return new Offset(offset.cpy(), affectMouse, rotate);
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
}
