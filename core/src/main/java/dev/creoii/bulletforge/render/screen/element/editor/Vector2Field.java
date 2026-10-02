package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import dev.creoii.bulletforge.util.EditorUtils;

public class Vector2Field extends Table {
    private final TextField xField;
    private final TextField yField;

    public Vector2Field(Skin skin) {
        super(skin);

        xField = new TextField("", skin);
        xField.setTextFieldFilter(EditorUtils.NumberFilter.INSTANCE);
        yField = new TextField("", skin);
        yField.setTextFieldFilter(EditorUtils.NumberFilter.INSTANCE);

        add(new Label("X", skin));
        add(xField).growX();
        add(new Label("Y", skin));
        add(yField).growX();
    }

    public TextField getXField() {
        return xField;
    }

    public TextField getYField() {
        return yField;
    }

    public void addXFieldListener(EventListener listener) {
        xField.addListener(listener);
    }

    public void addYFieldListener(EventListener listener) {
        yField.addListener(listener);
    }
}
