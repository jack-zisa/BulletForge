package dev.creoii.bulletforge.util.editor;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.reflect.Field;

public interface EditorOption {
    void create(Table table, Object target, Field field, Skin skin);
}
