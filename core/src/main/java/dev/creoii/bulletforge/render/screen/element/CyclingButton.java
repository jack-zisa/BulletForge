package dev.creoii.bulletforge.render.screen.element;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.jspecify.annotations.Nullable;

public class CyclingButton<T> extends TextButton {
    private final T[] values;
    private int currentIndex;
    private @Nullable ChangeListener onChanged;

    public CyclingButton(String prefix, T[] values, Skin skin, @Nullable ChangeListener onChanged) {
        super(prefix + ": " + values[0], skin);
        this.values = values;
        this.onChanged = onChanged;
        currentIndex = 0;

        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                currentIndex = (currentIndex + 1) % values.length;
                setText(prefix + ": " + values[currentIndex]);

                if (CyclingButton.this.onChanged != null) CyclingButton.this.onChanged.changed(event, actor);
            }
        });
    }

    public CyclingButton(String prefix, T[] values, Skin skin) {
        this(prefix, values, skin, null);
    }

    public T getSelectedValue() {
        return values[currentIndex];
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setOnChanged(@Nullable ChangeListener onChanged) {
        this.onChanged = onChanged;
    }
}
