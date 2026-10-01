package dev.creoii.bulletforge.object.definition.path;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.reflect.Field;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.creoii.bulletforge.object.instance.BulletNode;
import dev.creoii.bulletforge.render.screen.element.CyclingButton;
import dev.creoii.bulletforge.util.editor.EditorOption;
import dev.creoii.bulletforge.util.editor.EditorSerializable;

import java.util.Objects;

public final class WavyBulletPathType implements BulletPathType<WavyBulletPathType.WavyBulletPathInstance> {
    public static final MapCodec<WavyBulletPathType> TYPE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        WaveType.CODEC.fieldOf("wave_type").orElse(WaveType.SIN).forGetter(WavyBulletPathType::waveType),
        Codec.FLOAT.fieldOf("amplitude").orElse(0f).forGetter(WavyBulletPathType::amplitude),
        Codec.FLOAT.fieldOf("frequency").orElse(0f).forGetter(WavyBulletPathType::frequency),
        Codec.BOOL.fieldOf("index_phase").orElse(false).forGetter(WavyBulletPathType::indexPhase)
    ).apply(instance, WavyBulletPathType::new));
    @EditorSerializable
    private WaveType waveType;
    @EditorSerializable
    private float amplitude;
    @EditorSerializable
    private float frequency;
    @EditorSerializable
    private boolean indexPhase;

    public WavyBulletPathType(WaveType waveType, float amplitude, float frequency, boolean indexPhase) {
        this.waveType = waveType;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.indexPhase = indexPhase;
    }

    @Override
    public Type type() {
        return Type.WAVY;
    }

    @Override
    public WavyBulletPathInstance create() {
        return new WavyBulletPathInstance(this);
    }

    public WaveType waveType() {
        return waveType;
    }

    public void setWaveType(WaveType waveType) {
        this.waveType = waveType;
    }

    public float amplitude() {
        return amplitude;
    }

    public float frequency() {
        return frequency;
    }

    public boolean indexPhase() {
        return indexPhase;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (WavyBulletPathType) obj;
        return Objects.equals(this.waveType, that.waveType) &&
            Float.floatToIntBits(this.amplitude) == Float.floatToIntBits(that.amplitude) &&
            Float.floatToIntBits(this.frequency) == Float.floatToIntBits(that.frequency) &&
            this.indexPhase == that.indexPhase;
    }

    @Override
    public int hashCode() {
        return Objects.hash(waveType, amplitude, frequency, indexPhase);
    }

    @Override
    public String toString() {
        return "WavyBulletPathType[" +
            "waveType=" + waveType + ", " +
            "amplitude=" + amplitude + ", " +
            "frequency=" + frequency + ", " +
            "indexPhase=" + indexPhase + ']';
    }

    public static class WavyBulletPathInstance extends Instance<WavyBulletPathType> {
        public WavyBulletPathInstance(WavyBulletPathType definition) {
            super(definition);
        }

        @Override
        public Vector2 getPathOffset(BulletNode node, float t) {
            float phase = getType().indexPhase ? 0f : ((node.index() & 1) == 0 ? 0f : .5f);
            float cycle = t * (getType().frequency / 100f) + phase;
            float wave = switch (getType().waveType) {
                case SIN -> MathUtils.sin(cycle * MathUtils.PI2) * getType().amplitude;
                case TRIANGLE -> {
                    float triangle = 2f * Math.abs(2f * (cycle - (float) Math.floor(cycle + .5f))) - 1f;
                    yield triangle * getType().amplitude;
                }
                case SQUARE -> (MathUtils.sin(cycle * MathUtils.PI2) >= 0f ? 1f : -1f) * getType().amplitude;
                case SAWTOOTH -> {
                    float sawtooth = 2f * (cycle - (float) Math.floor(cycle)) - 1f;
                    yield sawtooth * getType().amplitude;
                }
            };
            setOffset(wave, t);
            return getOffset();
        }

        @Override
        public WavyBulletPathInstance copy() {
            return new WavyBulletPathInstance(getType());
        }
    }

    public enum WaveType implements EditorOption {
        SIN,
        TRIANGLE,
        SQUARE,
        SAWTOOTH;

        public static final Codec<WaveType> CODEC = Codec.STRING.xmap(s -> WaveType.valueOf(s.toUpperCase()), type -> type.name().toLowerCase());

        @Override
        public void create(Table table, Object target, Field field, Skin skin) {
            CyclingButton<WaveType> pathTypeButton = new CyclingButton<>("Type", WaveType.values(), skin);

            System.out.println(target.getClass().getSimpleName());

            pathTypeButton.setOnChanged(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    WaveType type = pathTypeButton.getSelectedValue();
                }
            });

            table.add(pathTypeButton).colspan(2).growX().row();
        }
    }
}
