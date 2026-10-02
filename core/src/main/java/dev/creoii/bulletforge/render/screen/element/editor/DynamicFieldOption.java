package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import dev.creoii.bulletforge.render.screen.element.editor.config.DynamicFieldConfig;

public class DynamicFieldOption extends Table {
    private final DynamicFieldConfig<?> config;
    private final Actor valueActor;

    public DynamicFieldOption(DynamicFieldConfig<?> config, Skin skin) {
        this.config = config;

        setSkin(skin);

        add(new Label(config.field().getName(), skin));
        add(valueActor = config.create());
    }

    public DynamicFieldConfig<?> config() {
        return config;
    }

    public Actor getValueActor() {
        return valueActor;
    }
}
